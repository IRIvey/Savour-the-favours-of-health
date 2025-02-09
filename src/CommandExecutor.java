public class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();

    public void executeCommand(int choice, User user) {
        try {
            HealthMetricType metricType = getMetricTypeForChoice(choice);
            if (metricType != null) {
                Tracker tracker = trackerFactory.createTracker(metricType);
                tracker.track(user);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command choice: " + choice);
        }
    }

    private HealthMetricType getMetricTypeForChoice(int choice) {
        return switch (choice) {
            case 1 -> HealthMetricType.WATER_INTAKE;
            case 2 -> HealthMetricType.SLEEP_DURATION;
            case 3 -> HealthMetricType.EXERCISE_DURATION;
            case 4 -> HealthMetricType.CALORIES;
            case 5 -> HealthMetricType.WEIGHT;
            case 6 -> HealthMetricType.STEPS;
            default -> null;
        };
    }
}
