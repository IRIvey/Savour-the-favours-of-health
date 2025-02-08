public class Goal {
    private final HealthMetricType metricType;
    private final double targetValue;
    private final GoalPeriod period;
    private boolean achieved;

    public Goal(HealthMetricType metricType, double targetValue, GoalPeriod period) {
        this.metricType = metricType;
        this.targetValue = targetValue;
        this.period = period;
        this.achieved = false;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public HealthMetricType getMetricType() {
        return metricType;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public GoalPeriod getPeriod() {
        return period;
    }
}
