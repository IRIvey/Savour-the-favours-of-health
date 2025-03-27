import java.util.Scanner;

public class SleepTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Sleep Tracking ===");
        System.out.print("Enter sleep duration (hours): ");
        double hours = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new SleepDurationMetric(), hours, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nSleep Statistics:");
        double totalSleep = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof SleepDurationMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total sleep recorded: " + totalSleep + " hours");
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
