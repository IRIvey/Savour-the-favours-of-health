import java.util.Scanner;

public class StepTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Step Tracking ===");
        System.out.print("Enter step count: ");
        double steps = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new StepMetric(), steps, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nStep Statistics:");
        double totalSteps = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof StepMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total steps recorded: " + totalSteps);
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
