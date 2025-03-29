package tracker;

import java.util.Scanner;

public class ExerciseTracker implements Tracker {
    private final HealthMetric metric = new ExerciseDurationMetric();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.print("Enter exercise duration (minutes): ");
        double duration = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? (optional): ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(metric, duration, notes);
        user.addHealthData(data);

        checkGoals(user);

        System.out.println("✅ Exercise logged: " + duration + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Exercise History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit() + " (" + data.getNotes() + ")");
        }
    }

    @Override
    public void checkGoals(User user) {
        double totalExercise = user.getTotalRecordedValue(metric);
        Goal goal = user.getGoalForMetric(metric);

        if (goal != null) {
            goal.checkIfAchieved(totalExercise);
            System.out.println("\n📊 Exercise Goal Progress:");
            System.out.println("➡ Goal: " + goal.getTargetValue() + " " + metric.getUnit());
            System.out.println("➡ Recorded: " + totalExercise + " " + metric.getUnit());
            if (goal.isAchieved()) {
                System.out.println("✅ Goal Achieved! 🎉 Keep it up!");
            } else {
                System.out.println("❌ Goal Not Achieved. Exercise " + (goal.getTargetValue() - totalExercise) + " more " + metric.getUnit() + ".");
            }
        }
    }
}


