package goal;

import java.io.Serializable;

public class MonthlyGoal implements GoalPeriod, Serializable {
    public String getPeriodName() {
        return "Monthly";
    }
}
