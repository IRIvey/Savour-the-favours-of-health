import java.util.Scanner;

public class WeightTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Weight Tracking ===");
        System.out.print("Enter your weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(HealthMetricType.WEIGHT, weight, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nWeight Tracking Statistics:");
        user.getHealthHistory().stream()
                .filter(data -> data.getType() == HealthMetricType.WEIGHT)
                .reduce((first, second) -> second)
                .ifPresentOrElse(
                        latestData -> System.out.println("Latest recorded weight: " + latestData.getValue() + " kg"),
                        () -> System.out.println("No weight data recorded yet.")
                );
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetricType() == HealthMetricType.WEIGHT)
                .forEach(goal -> {
                    double latestWeight = user.getHealthHistory().stream()
                            .filter(data -> data.getType() == HealthMetricType.WEIGHT)
                            .mapToDouble(HealthData::getValue)
                            .reduce((first, second) -> second)
                            .orElse(0);

                    if (latestWeight == goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Congratulations! You've achieved your weight goal!");
                    }
                });
    }
}
