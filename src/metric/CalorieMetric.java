package metric;

import java.io.Serializable;

public class CalorieMetric extends HealthMetric implements Serializable {
    public CalorieMetric() {
        super("Calories", "kcal");
    }
}