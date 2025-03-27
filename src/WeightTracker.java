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
                .reduce((first, second) -> second) // Get the latest weight entry
                .orElse(0);
        System.out.println("Latest recorded weight: " + latestWeight + " kg");
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetric() instanceof WeightMetric)
                .forEach(goal -> {
                    double latestWeight = user.getHealthHistory().stream()
                            .filter(data -> data.getMetric() instanceof WeightMetric)
                            .mapToDouble(HealthData::getValue)
                            .reduce((first, second) -> second)
                            .orElse(0);

                    if (latestWeight <= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Great work! You've reached your weight goal!");
                    }
                });
    }
}

