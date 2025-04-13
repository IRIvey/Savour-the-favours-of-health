package goal;

import java.io.Serializable;

public class DailyGoal implements GoalPeriod, Serializable {
    public String getPeriodName() {
        return "Daily";
    }
}

