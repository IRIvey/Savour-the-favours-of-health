package tracker;

import user.*;
import metric.*;
import goal.*;
import challenge.ChallengeTracker;

public class ExerciseTracker implements Tracker {
    private final HealthMetric metric = new ExerciseDurationMetric();
    private final ChallengeTracker challengeTracker;
    private final GoalProcessor goalProcessor;

    public ExerciseTracker(ChallengeTracker challengeTracker, GoalProcessor goalProcessor) {
        this.challengeTracker = challengeTracker;
        this.goalProcessor = goalProcessor;
    }

    @Override
    public void track(User user, double value, String notes) {
            if (value <= 0 || value > 8) {
                System.out.println("❌ Error: Exercise duration must be between 0 and 8 hours.");
                return;
            }
        HealthData data = new HealthData(metric, value, notes);
        user.addHealthData(data);
        challengeTracker.recordValue(metric, value);
        goalProcessor.process(user, metric, value);
        System.out.println("✅ Exercise logged: " + value + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Exercise History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit());
        }
    }

    @Override
    public void checkGoals(User user) {
        goalProcessor.process(user, metric, user.getTotalRecordedValue(metric));
    }
}


