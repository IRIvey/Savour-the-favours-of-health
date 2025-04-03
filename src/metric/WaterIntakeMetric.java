package metric;

import java.io.Serializable;

public class WaterIntakeMetric extends HealthMetric implements Serializable {
    public WaterIntakeMetric() {
        super("Water Intake", "ml");
    }
}
