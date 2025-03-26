public class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();

    public void executeCommand(int choice, User user) {
        HealthMetricType type = getHealthMetricType(choice);
        if (type != null) {
            Tracker tracker = trackerFactory.createTracker(type);
            tracker.track(user);
        } else {
            System.out.println("Invalid choice! Please try again.");
        }
    }

    private HealthMetricType getHealthMetricType(int choice) {
        return switch (choice) {
            case 1 -> HealthMetricType.WATER_INTAKE;
            case 2 -> HealthMetricType.SLEEP_DURATION;
            case 3 -> HealthMetricType.EXERCISE_DURATION;
            case 4 -> HealthMetricType.CALORIES_CONSUMED;
            case 5 -> HealthMetricType.WEIGHT;
            case 6 -> HealthMetricType.STEPS_TAKEN;
            default -> null;
        };
    }
}
