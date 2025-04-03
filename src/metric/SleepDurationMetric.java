package metric;

import java.io.Serializable;

public class SleepDurationMetric extends HealthMetric implements Serializable {
    public SleepDurationMetric() {
        super("Sleep Duration", "hours");
    }
}
