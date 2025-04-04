package challenge;

import metric.HealthMetric;
import java.time.LocalDate;

public class ChallengeFactory {
    public static Challenge createChallenge(HealthMetric metric, double targetValue, int periodChoice) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate;

        return switch (periodChoice) {
            case 1 -> {
                endDate = startDate.plusDays(6);
                yield new WeeklyChallenge(metric, targetValue, startDate, endDate);
            }
            case 2 -> {
                endDate = startDate.plusDays(29);
                yield new MonthlyChallenge(metric, targetValue, startDate, endDate);
            }
            default -> throw new IllegalArgumentException("Invalid challenge period");
        };
    }
}

