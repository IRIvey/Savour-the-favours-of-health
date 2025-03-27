public class Goal {
    private final HealthMetric metric;
    private final double targetValue;
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
