package factory;

import tracker.*;

public class TrackerFactory {
    private static final TrackerFactory instance = new TrackerFactory();

    private TrackerFactory() {}

    public static TrackerFactory getInstance() {
        return instance;
    }

    public Tracker createTracker(HealthMetric metric) {
        if (metric instanceof WaterIntakeMetric) return new WaterTracker();
        if (metric instanceof SleepDurationMetric) return new SleepTracker();
        if (metric instanceof ExerciseDurationMetric) return new ExerciseTracker();
        if (metric instanceof CalorieMetric) return new CalorieTracker();
        if (metric instanceof WeightMetric) return new WeightTracker();
        if (metric instanceof StepMetric) return new StepTracker();
        throw new IllegalArgumentException("Unknown health metric: " + metric.getName());
    }
}

