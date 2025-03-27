import java.util.Scanner;

public class WaterTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Water Intake Tracking ===");
        System.out.print("Enter water intake (ml): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new WaterIntakeMetric(), amount, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nWater Intake Statistics:");
        double totalIntake = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WaterIntakeMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total intake today: " + totalIntake + " ml");
    }

    @Override
    public void checkGoals(User user) {
        double totalCalories = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof CalorieMetric)
                .mapToDouble(HealthData::getValue)
                .sum();

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof CalorieMetric) {
                goal.checkIfAchieved(totalCalories);

                if (goal.isAchieved()) {
                    System.out.println("🎯 You reached your calorie goal of " + goal.getTargetValue() + " kcal!");
                }
            }
        }
    }

}
