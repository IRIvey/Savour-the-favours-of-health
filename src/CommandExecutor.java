public class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();

    public void executeCommand(int choice, User user) {
        HealthMetric metric = getMetricForChoice(choice);
        if (metric != null) {
            Tracker tracker = trackerFactory.createTracker(metric);
            tracker.track(user);
        } else {
            System.out.println("Invalid choice: " + choice);
        }
    }

    private HealthMetric getMetricForChoice(int choice) {
        return switch (choice) {
            case 1 -> new WaterIntakeMetric();
            case 2 -> new SleepDurationMetric();
            case 3 -> new ExerciseDurationMetric();
            case 4 -> new CalorieMetric();
            case 5 -> new WeightMetric();
            case 6 -> new StepMetric();
            default -> null;
        };
    }
}
