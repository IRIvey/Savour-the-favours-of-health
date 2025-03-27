import java.util.Scanner;

public class WeightTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Weight Tracking ===");
        System.out.print("Enter your current weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new WeightMetric(), weight, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nWeight Statistics:");
        double latestWeight = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WeightMetric)
                .mapToDouble(HealthData::getValue)
                .reduce((first, second) -> second)
                .orElse(0);
        System.out.println("Latest recorded weight: " + latestWeight + " kg");
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

