package challenge;

import metric.HealthMetric;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class Challenge implements Serializable {
    protected HealthMetric metric;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected double targetValue;
    protected int currentStreak;
    protected int maxStreak;
    protected boolean active;

    public Challenge(HealthMetric metric, double targetValue, LocalDate startDate, LocalDate endDate) {
        this.metric = metric;
        this.targetValue = targetValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.active = true;
    }

    public void recordActualValue(double actualValue) {
        if (!active) return;
        boolean met = actualValue >= targetValue;
        if (met) {
            currentStreak++;
            if (currentStreak > maxStreak) maxStreak = currentStreak;
        } else {
            currentStreak = 0;
        }
    }

    public void endChallenge() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public String getSummary() {
        return String.format("[%s Challenge] Metric: %s | Target: %.2f %s | Max Streak: %d | Status: %s",
                getChallengeType(),
                metric.getName(),
                targetValue,
                metric.getUnit(),
                maxStreak,
                active ? "Active" : "Ended");
    }

    public abstract String getChallengeType();

    public HealthMetric getMetric() {
        return metric;
    }

    public double getTargetValue() {
        return targetValue;
    }
}