package challenge;

import metric.HealthMetric;
import java.time.LocalDate;

public class WeeklyChallenge extends Challenge {
    public WeeklyChallenge(HealthMetric metric, LocalDate startDate) {
        super(metric, startDate, startDate.plusDays(7));
    }

    @Override
    public String getChallengeType() {
        return "Weekly";
    }
}
