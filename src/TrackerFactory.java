import java.util.Map;
import java.util.HashMap;

public class TrackerFactory {
    private static final TrackerFactory instance = new TrackerFactory();
    private final Map<HealthMetricType, Tracker> trackerMap = new HashMap<>();

    private TrackerFactory() {
        trackerMap.put(HealthMetricType.WATER_INTAKE, new WaterTracker());
        trackerMap.put(HealthMetricType.SLEEP_DURATION, new SleepTracker());
        trackerMap.put(HealthMetricType.EXERCISE_DURATION, new ExerciseTracker());
        trackerMap.put(HealthMetricType.CALORIES_CONSUMED, new CalorieTracker());
        trackerMap.put(HealthMetricType.WEIGHT, new WeightTracker());
        trackerMap.put(HealthMetricType.STEPS_TAKEN, new StepTracker());
    }

    public static TrackerFactory getInstance() {
        return instance;
    }

    public Tracker createTracker(HealthMetricType type) {
        return trackerMap.get(type);
    }
}
