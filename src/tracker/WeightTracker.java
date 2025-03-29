package tracker;

import metric.HealthMetric;
import metric.WeightMetric;

import java.util.Scanner;

public class WeightTracker implements Tracker {
    private final HealthMetric metric = new WeightMetric();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.print("Enter your weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? (optional): ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(metric, weight, notes);
        user.addHealthData(data);

        checkGoals(user);

        System.out.println("✅ Weight logged: " + weight + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Weight History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit() + " (" + data.getNotes() + ")");
        }
    }

    @Override
    public void checkGoals(User user) {
        double latestWeight = user.getTotalRecordedValue(metric);
        Goal goal = user.getGoalForMetric(metric);

        if (goal != null) {
            goal.checkIfAchieved(latestWeight);
            System.out.println("\n📊 Weight goal.Goal Progress:");
            System.out.println("➡ goal.Goal: " + goal.getTargetValue() + " " + metric.getUnit());
            System.out.println("➡ Recorded: " + latestWeight + " " + metric.getUnit());
            if (goal.isAchieved()) {
                System.out.println("✅ goal.Goal Achieved! 🎉 Keep maintaining!");
            } else {
                System.out.println("❌ goal.Goal Not Achieved. Adjust by " + (goal.getTargetValue() - latestWeight) + " kg.");
            }
        }
    }
}

