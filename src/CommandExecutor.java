import java.util.HashMap;
import java.util.Map;


class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();
    private final Map<Integer, HealthMetricType> commandMap;

    public CommandExecutor() {
        commandMap = new HashMap<>();
        commandMap.put(1, HealthMetricType.WATER_INTAKE);
        commandMap.put(2, HealthMetricType.SLEEP_DURATION);
        commandMap.put(3, HealthMetricType.EXERCISE_DURATION);
        commandMap.put(4, HealthMetricType.CALORIES_CONSUMED);
        commandMap.put(5, HealthMetricType.WEIGHT);
        commandMap.put(6, HealthMetricType.STEPS_TAKEN);
    }

    public void executeCommand(int choice, User user) {
        HealthMetricType metricType = commandMap.get(choice);
        if (metricType != null) {
            Tracker tracker = trackerFactory.createTracker(metricType);
            tracker.track(user);
        } else {
            System.out.println("Invalid command choice: " + choice);
        }
    }
}
