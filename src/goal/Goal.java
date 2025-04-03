package goal;

import metric.HealthMetric;

import java.io.Serializable;

public class Goal implements Serializable {
    private final HealthMetric metric;
    private double targetValue;
    private final GoalPeriod period;
    private boolean achieved;

    public Goal(HealthMetric metric, double targetValue, GoalPeriod period) {
        this.metric = metric;
        this.targetValue = targetValue;
        this.period = period;
        this.achieved = false;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void checkIfAchieved(double totalValue) {
        if (totalValue >= targetValue) {
            achieved = true;
        }
    }

    public void updateTargetValue(double newTargetValue) {
        this.targetValue = newTargetValue;
    }

    public HealthMetric getMetric() {
        return metric;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public GoalPeriod getPeriod() {
        return period;
    }
}
