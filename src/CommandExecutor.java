public class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();

    public void executeCommand(HealthMetric metric, User user) {
        Tracker tracker = trackerFactory.createTracker(metric);
        tracker.track(user);
    }
}
