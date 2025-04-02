package challenge;

import java.util.HashMap;
import java.util.Map;
import metric.HealthMetric;

public class ChallengeTracker {
    private static ChallengeTracker instance;
    private Map<String, Challenge> activeChallenges;

    private ChallengeTracker() {
        activeChallenges = new HashMap<>();
    }

    public static ChallengeTracker getInstance() {
        if (instance == null) {
            instance = new ChallengeTracker();
        }
        return instance;
    }

    public void addChallenge(Challenge challenge) {
        String key = challenge.getMetric().getName();
        activeChallenges.put(key, challenge);
        System.out.println("Challenge added for " + key);
    }

    public Challenge getChallenge(HealthMetric metric) {
        return activeChallenges.get(metric.getName());
    }

    public void recordProgress(HealthMetric metric, boolean met) {
        Challenge challenge = activeChallenges.get(metric.getName());
        if (challenge != null && challenge.isActive()){
            challenge.recordDailyProgress(met);
        } else {
            System.out.println("No active challenge for " + metric.getName());
        }
    }

    public void endChallenge(HealthMetric metric) {
        Challenge challenge = activeChallenges.get(metric.getName());
        if (challenge != null) {
            challenge.endChallenge();
            System.out.println("Challenge ended for " + metric.getName());
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
}

