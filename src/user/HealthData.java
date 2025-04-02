package user;

import metric.*;
import goal.*;
import system.*;
import main.*;
import factory.*;
import java.time.LocalDateTime;

public class HealthData {
    private final HealthMetric metric;
    private final double value;
    private final LocalDateTime timestamp;
    private final String notes;

    public HealthData(HealthMetric metric, double value, String notes) {
        this.metric = metric;
        this.value = value;
        this.timestamp = LocalDateTime.now();
        this.notes = notes;
    }

    public HealthMetric getMetric() {
        return metric;
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

