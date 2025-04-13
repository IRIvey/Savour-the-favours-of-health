package tracker;

import user.HealthData;
import user.User;
import metric.*;
import goal.GoalProcessor;
import challenge.ChallengeTracker;

public class WaterTracker implements Tracker {
    private final HealthMetric metric = new WaterIntakeMetric();
    private final ChallengeTracker challengeTracker;
    private final GoalProcessor goalProcessor;

    public WaterTracker(ChallengeTracker challengeTracker, GoalProcessor goalProcessor) {
        this.challengeTracker = challengeTracker;
        this.goalProcessor = goalProcessor;
    }

    @Override
    public void track(User user, double value, String notes) {
        HealthData data = new HealthData(metric, value, notes);
        user.addHealthData(data);
        challengeTracker.recordValue(metric, value);
        goalProcessor.process(user, metric, value);
        System.out.println("✅ Water logged: " + value + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Water History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit());
        }
    }

    @Override
    public void checkGoals(User user) {
        goalProcessor.process(user, metric, user.getTotalRecordedValue(metric));
    }
}

