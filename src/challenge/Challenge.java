package challenge;

import metric.HealthMetric;
import java.time.LocalDate;

public abstract class Challenge {
    protected HealthMetric metric;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int currentStreak;
    protected int maxStreak;
    protected boolean active;

    public Challenge(HealthMetric metric, LocalDate startDate, LocalDate endDate) {
        this.metric = metric;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.active = true;
    }

    public HealthMetric getMetric() {
        return metric;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public boolean isActive() {
        return active;
    }

    public abstract String getChallengeType();


    public void recordDailyProgress(boolean met) {
        if (!active) {
            System.out.println("Challenge is no longer active.");
            return;
        }
        if (met) {
            currentStreak++;
            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
            }
        } else {
            currentStreak = 0;
        }
    }


    public void endChallenge() {
        active = false;
    }


    public String getSummary() {
        String status = active ? "Active" : "Ended";
        return String.format("Challenge (%s) for %s from %s to %s: Current Streak = %d, Max Streak = %d, Status = %s",
                getChallengeType(), metric.getName(), startDate, endDate, currentStreak, maxStreak, status);
    }
}
