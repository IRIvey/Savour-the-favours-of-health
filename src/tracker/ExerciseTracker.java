package tracker;

import user.*;
import metric.*;
import goal.*;
import challenge.ChallengeTracker;
import java.util.Scanner;
import java.time.LocalDate;

public class ExerciseTracker implements Tracker {
    private final HealthMetric metric = new ExerciseDurationMetric();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.print("Enter exercise duration (minutes): ");
        double minutes = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? (optional): ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(metric, minutes, notes);
        user.addHealthData(data);
        ChallengeTracker.getInstance().recordValue(metric, minutes);

        Goal goal = user.getGoalForMetric(metric);
        if (goal != null) {
            goal.recordProgress(LocalDate.now(), minutes);
            System.out.println(goal.getProgressSummary());
        }

        System.out.println("✅ Exercise logged: " + minutes + " " + metric.getUnit());
        checkGoals(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Exercise History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit());
        }
    }

    @Override
    public void checkGoals(User user) {
        double total = user.getTotalRecordedValue(metric);
        Goal goal = user.getGoalForMetric(metric);
        if (goal != null) {
            goal.checkIfAchieved(total);
            System.out.println("\n📊 Goal Progress:");
            System.out.println("➡ Goal: " + goal.getTargetValue() + " " + metric.getUnit());
            System.out.println("➡ Recorded: " + total + " " + metric.getUnit());
            if (goal.isAchieved()) {
                System.out.println("✅ Goal Achieved! 🎉");
            } else {
                System.out.println("❌ Goal Not Achieved. You need " + (goal.getTargetValue() - total) + " more " + metric.getUnit() + ".");
            }
        }
    }
}



