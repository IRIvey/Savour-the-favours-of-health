package challenge;

import metric.HealthMetric;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class Challenge implements Serializable {
    protected HealthMetric metric;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected double targetValue;
    protected int currentStreak;
    protected int maxStreak;
    protected boolean active;


    protected Set<LocalDate> successfulDays = new HashSet<>();
    protected LocalDate lastSuccessDate = null;

    public Challenge(HealthMetric metric, double targetValue, LocalDate startDate, LocalDate endDate) {
        this.metric = metric;
        this.targetValue = targetValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.active = true;
    }

    public abstract String getChallengeType();

    public void recordActualValue(double actualValue) {
        if (!isActive()) return;

        LocalDate today = LocalDate.now();


        if (successfulDays.contains(today)) return;

        if (actualValue >= targetValue) {
            successfulDays.add(today);

            if (lastSuccessDate != null && lastSuccessDate.plusDays(1).equals(today)) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }

            lastSuccessDate = today;
            maxStreak = Math.max(maxStreak, currentStreak);
        } else {
            currentStreak = 0;
        }
    }

    public void endChallenge() {
        this.active = false;
    }

    public String getSummary() {
        return getDailyBreakdown();
    }

    public String getDailyBreakdown() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n📆 Challenge Breakdown for ").append(metric.getName()).append(":\n");
        sb.append("Target: ").append(targetValue).append(" ").append(metric.getUnit()).append("\n");
        sb.append("Start: ").append(startDate).append(" → End: ").append(endDate).append("\n");
        sb.append("Max Streak: ").append(maxStreak).append("\n");
        sb.append("Status: ").append(active ? "In Progress" : "Completed").append("\n");
        return sb.toString();
    }


    public HealthMetric getMetric() { return metric; }
    public boolean isActive() { return active; }
    public double getTargetValue() { return targetValue; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getCurrentStreak() { return currentStreak; }
    public int getMaxStreak() { return maxStreak; }
}
