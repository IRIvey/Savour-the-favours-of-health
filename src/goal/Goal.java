package goal;

import metric.HealthMetric;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Goal implements Serializable {
    private final HealthMetric metric;
    private double targetValue;
    private final GoalPeriod period;
    private boolean achieved;
    private final Map<LocalDate, GoalProgressLog> progressLogs = new HashMap<>();

    public Goal(HealthMetric metric, double targetValue, GoalPeriod period) {
        this.metric = metric;
        this.targetValue = targetValue;
        this.period = period;
    }

    public void recordProgress(LocalDate date, double value) {
        GoalProgressLog log = progressLogs.getOrDefault(date, new GoalProgressLog(date));
        log.addToValue(value, targetValue);
        progressLogs.put(date, log);
    }

    public void checkIfAchieved(double total) {
        this.achieved = total >= targetValue;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public String getProgressSummary() {
        if (progressLogs.isEmpty()) return "No progress logged yet.";
        StringBuilder sb = new StringBuilder("\n📅 Progress Summary for " + metric.getName() + ":\n");
        progressLogs.values().stream()
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .forEach(log -> sb.append(log.toString()).append("\n"));
        return sb.toString();
    }

    public void updateTargetValue(double newTarget) {
        this.targetValue = newTarget;
        this.achieved = false;

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
