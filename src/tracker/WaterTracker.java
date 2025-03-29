package tracker;

import java.util.Scanner;

public class WaterTracker implements Tracker {
    private final HealthMetric metric = new WaterIntakeMetric();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.print("Enter water intake (liters): ");
        double intake = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? (optional): ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(metric, intake, notes);
        user.addHealthData(data);

        checkGoals(user);

        System.out.println("✅ Water logged: " + intake + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Water Intake History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit() + " (" + data.getNotes() + ")");
        }
    }

    @Override
    public void checkGoals(User user) {
        double totalIntake = user.getTotalRecordedValue(metric);
        Goal goal = user.getGoalForMetric(metric);

        if (goal != null) {
            goal.checkIfAchieved(totalIntake);
            System.out.println("\n📊 Water Goal Progress:");
            System.out.println("➡ Goal: " + goal.getTargetValue() + " " + metric.getUnit());
            System.out.println("➡ Recorded: " + totalIntake + " " + metric.getUnit());
            if (goal.isAchieved()) {
                System.out.println("✅ Goal Achieved! 🎉 Keep Hydrated!");
            } else {
                System.out.println("❌ Goal Not Achieved. Drink " + (goal.getTargetValue() - totalIntake) + " more " + metric.getUnit() + ".");
            }
        }
    }
}

