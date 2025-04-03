package challenge;

import metric.HealthMetric;
import java.time.LocalDate;

public class MonthlyChallenge extends Challenge {
    public MonthlyChallenge(HealthMetric metric, double targetValue, LocalDate startDate, LocalDate endDate) {
        super(metric, targetValue, startDate, endDate);
    }

    @Override
    public String getChallengeType() {
        return "Monthly";
    }
}

