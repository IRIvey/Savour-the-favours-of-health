public class TrackerFactory {
    private static final TrackerFactory instance = new TrackerFactory();

    private TrackerFactory() {}

    public static TrackerFactory getInstance() {
        return instance;
    }

    public Tracker createTracker(HealthMetricType type) {
        return switch (type) {
            case WATER_INTAKE -> new WaterTracker();
            case SLEEP_DURATION -> new SleepTracker();
            case EXERCISE_DURATION -> new ExerciseTracker();
            case CALORIES -> new CalorieTracker();
            case WEIGHT -> new WeightTracker();
            case STEPS -> new StepTracker();
        };
    }
}
