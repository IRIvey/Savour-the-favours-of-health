package challenge;

import metric.HealthMetric;
import java.time.LocalDate;

public class ChallengeFactory {

    public static Challenge createChallenge(HealthMetric metric, int periodChoice) {
        LocalDate today = LocalDate.now();
        switch (periodChoice) {
            case 1:
                return new WeeklyChallenge(metric, today);
            case 2:
                return new MonthlyChallenge(metric, today);
            default:
                throw new IllegalArgumentException("Invalid challenge period choice");
        }
    }
}

