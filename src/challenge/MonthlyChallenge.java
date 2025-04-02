package challenge;

import metric.HealthMetric;
import java.time.LocalDate;

public class MonthlyChallenge extends Challenge {
    public MonthlyChallenge(HealthMetric metric, LocalDate startDate) {
        super(metric, startDate, startDate.plusDays(30));
    }

    @Override
    public String getChallengeType() {
        return "Monthly";
    }
}
