package metric;

import java.io.Serializable;

public class ExerciseDurationMetric extends HealthMetric implements Serializable {
    public ExerciseDurationMetric() {
        super("Exercise Duration", "minutes");
    }
}
