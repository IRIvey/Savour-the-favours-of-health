package metric;

import java.io.Serializable;

public class WeightMetric extends HealthMetric implements Serializable {
    public WeightMetric() {
        super("Weight", "kg");
    }
}