import java.time.LocalDateTime;

public class HealthData {
    private final HealthMetricType type;
    private final double value;
    private final LocalDateTime timestamp;
    private final String notes;

    public HealthData(HealthMetricType type, double value, String notes) {
        this.type = type;
        this.value = value;
        this.timestamp = LocalDateTime.now();
        this.notes = notes;
    }

    public HealthMetricType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getNotes() {
        return notes;
    }
}
