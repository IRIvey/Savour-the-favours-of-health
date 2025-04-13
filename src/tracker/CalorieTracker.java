package tracker;

import user.*;
import metric.*;
import goal.*;
import challenge.ChallengeTracker;

public class CalorieTracker implements Tracker {
    private final HealthMetric metric = new CalorieMetric();
    private final ChallengeTracker challengeTracker;
    private final GoalProcessor goalProcessor;

    public CalorieTracker(ChallengeTracker challengeTracker, GoalProcessor goalProcessor) {
        this.challengeTracker = challengeTracker;
        this.goalProcessor = goalProcessor;
    }

    @Override
    public void track(User user, double value, String notes) {
        HealthData data = new HealthData(metric, value, notes);
        user.addHealthData(data);
        challengeTracker.recordValue(metric, value);
        goalProcessor.process(user, metric, value);
        System.out.println("✅ Calories logged: " + value + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Calorie History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit());
        }
    }

    @Override
    public void checkGoals(User user) {
        goalProcessor.process(user, metric, user.getTotalRecordedValue(metric));
    }
}
