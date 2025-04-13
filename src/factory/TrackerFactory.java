package factory;

import metric.*;
import tracker.*;
import challenge.ChallengeTracker;
import goal.GoalProcessor;

public class TrackerFactory {
    private static final TrackerFactory instance = new TrackerFactory();
    private final ChallengeTracker challengeTracker = ChallengeTracker.getInstance();
    private final GoalProcessor goalProcessor = new GoalProcessor();

    private TrackerFactory() {}

    public static TrackerFactory getInstance() {
        return instance;
    }

    public Tracker createTracker(HealthMetric metric) {
        if (metric instanceof WaterIntakeMetric) return new WaterTracker(challengeTracker, goalProcessor);
        if (metric instanceof SleepDurationMetric) return new SleepTracker(challengeTracker, goalProcessor);
        if (metric instanceof ExerciseDurationMetric) return new ExerciseTracker(challengeTracker, goalProcessor);
        if (metric instanceof CalorieMetric) return new CalorieTracker(challengeTracker, goalProcessor);
        if (metric instanceof WeightMetric) return new WeightTracker(challengeTracker, goalProcessor);
        if (metric instanceof StepMetric) return new StepTracker(challengeTracker, goalProcessor);
        throw new IllegalArgumentException("Unknown health metric: " + metric.getName());
    }
}
