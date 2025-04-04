package goal;

import java.io.Serializable;

public class WeeklyGoal implements GoalPeriod, Serializable {
    public String getPeriodName() {
        return "Weekly";
    }
}
