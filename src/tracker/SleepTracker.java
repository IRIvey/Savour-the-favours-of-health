package tracker;

import user.*;
import metric.*;
import goal.*;
import challenge.ChallengeTracker;

public class SleepTracker implements Tracker {
    private final HealthMetric metric = new SleepDurationMetric();
    private final ChallengeTracker challengeTracker;
    private final GoalProcessor goalProcessor;

    public SleepTracker(ChallengeTracker challengeTracker, GoalProcessor goalProcessor) {
        this.challengeTracker = challengeTracker;
        this.goalProcessor = goalProcessor;
    }

    @Override
    public void track(User user, double value, String notes) {
        if (value <= 0 || value > 24) {
            System.out.println("❌ Error: Sleep duration must be between 0 and 24 hours.");
            return;
        }
        HealthData data = new HealthData(metric, value, notes);
        user.addHealthData(data);
        challengeTracker.recordValue(metric, value);
        goalProcessor.process(user, metric, value);
        System.out.println("✅ Sleep logged: " + value + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Sleep History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit());
        }
    }

    @Override
    public void checkGoals(User user) {
        goalProcessor.process(user, metric, user.getTotalRecordedValue(metric));
    }
}