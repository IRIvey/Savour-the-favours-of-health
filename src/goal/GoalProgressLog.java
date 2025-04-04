package goal;

import java.io.Serializable;
import java.time.LocalDate;

public class GoalProgressLog implements Serializable {
    private final LocalDate date;
    private double totalValue;
    private boolean met;

    public GoalProgressLog(LocalDate date) {
        this.date = date;
        this.totalValue = 0;
        this.met = false;
    }

    public void addToValue(double value, double target) {
        this.totalValue += value;
        this.met = totalValue >= target;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public boolean isMet() {
        return met;
    }

    public String toString() {
        return date + " - " + (met ? "✅ Met (" : "❌ Missed (") + totalValue + ")";
    }
}
