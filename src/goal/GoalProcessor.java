package goal;

import user.User;
import metric.HealthMetric;
import goal.Goal;

import java.time.LocalDate;

public class GoalProcessor {
    public void process(User user, HealthMetric metric, double value) {
        Goal goal = user.getGoalForMetric(metric);
        if (goal != null) {
            goal.recordProgress(LocalDate.now(), value);
            System.out.println(goal.getProgressSummary());

            double total = user.getTotalRecordedValue(metric);
            goal.checkIfAchieved(total);
            System.out.println("\n📊 Goal Progress:");
            System.out.println("➡ Goal: " + goal.getTargetValue() + " " + metric.getUnit());
            System.out.println("➡ Recorded: " + total + " " + metric.getUnit());
            if (goal.isAchieved()) {
                System.out.println("✅ Goal Achieved! 🎉");
            } else {
                System.out.println("❌ Goal Not Achieved. You need " + (goal.getTargetValue() - total) + " more " + metric.getUnit() + ".");
            }
        }
    }
}
