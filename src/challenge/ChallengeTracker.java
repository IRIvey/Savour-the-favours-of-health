package challenge;

import metric.HealthMetric;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChallengeTracker implements Serializable {
    private static ChallengeTracker instance;
    private final Map<String, Challenge> activeChallenges = new HashMap<>();

    private ChallengeTracker() {}

    public static ChallengeTracker getInstance() {
        if (instance == null) {
            instance = new ChallengeTracker();
        }
        return instance;
    }

    public void addChallenge(Challenge challenge) {
        activeChallenges.put(challenge.getMetric().getName(), challenge);
        System.out.println("Challenge started for " + challenge.getMetric().getName());
    }

    public void recordValue(HealthMetric metric, double actualValue) {
        Challenge challenge = activeChallenges.get(metric.getName());
        if (challenge != null && challenge.isActive()) {
            challenge.recordActualValue(actualValue);
        }
    }

    public void endChallenge(HealthMetric metric) {
        Challenge challenge = activeChallenges.get(metric.getName());
        if (challenge != null) {
            challenge.endChallenge();
            System.out.println(challenge.getSummary());
        }
    }

    public void listActiveChallenges() {
        if (activeChallenges.isEmpty()) {
            System.out.println("No active challenges.");
            return;
        }
        for (Challenge challenge : activeChallenges.values()) {
            System.out.println(challenge.getSummary());
        }
    }

    public Map<String, Challenge> getAllChallenges() {
        return activeChallenges;
    }
}

