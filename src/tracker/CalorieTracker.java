package tracker;

import java.util.Scanner;

public class CalorieTracker implements Tracker {
    private final HealthMetric metric = new CalorieMetric();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.print("Enter calories consumed: ");
        double calories = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? (optional): ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(metric, calories, notes);
        user.addHealthData(data);

        System.out.println("✅ Calories logged: " + calories + " " + metric.getUnit());

        checkGoals(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Calorie Intake History:");
        for (HealthData data : user.getHealthHistory()) {
            if (data.getMetric() instanceof CalorieMetric) {
                System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit() +
                        (data.getNotes().isEmpty() ? "" : " (Notes: " + data.getNotes() + ")"));
            }
        }
    }

    @Override
    public void checkGoals(User user) {
        double totalCalories = user.getTotalRecordedValue(metric);
        Goal goal = user.getGoalForMetric(metric);

        if (goal == null) {
            System.out.println("⚠ No goal set for Calories.");
            return;
        }

        goal.checkIfAchieved(totalCalories);

        System.out.println("\n📊 Calorie goal.Goal Progress:");
        System.out.println("➡ goal.Goal: " + goal.getTargetValue() + " kcal");
        System.out.println("➡ Recorded: " + totalCalories + " kcal");

        if (goal.isAchieved()) {
            System.out.println("✅ goal.Goal Achieved! 🎉 Well balanced!");
        } else {
            double difference = goal.getTargetValue() - totalCalories;
            System.out.println("❌ goal.Goal Not Achieved. You need " + difference + " more kcal.");
        }
    }
}
