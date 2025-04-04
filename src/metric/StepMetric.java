package metric;

import java.io.Serializable;

public class StepMetric extends HealthMetric implements Serializable {
    public StepMetric() {
        super("Steps", "count");
    }
}