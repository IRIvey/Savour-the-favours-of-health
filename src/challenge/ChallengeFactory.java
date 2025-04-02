package challenge;

import metric.HealthMetric;
import java.time.LocalDate;

public class ChallengeFactory {
    public static Challenge createChallenge(HealthMetric metric, double targetValue, int periodChoice) {
        LocalDate startDate = LocalDate.now();
        return switch (periodChoice) {
            case 1 -> new WeeklyChallenge(metric, targetValue, startDate);
            case 2 -> new MonthlyChallenge(metric, targetValue, startDate);
            default -> throw new IllegalArgumentException("Invalid period");
        };
    }
}

