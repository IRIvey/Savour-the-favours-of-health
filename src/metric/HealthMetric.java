package metric;

public abstract class HealthMetric {
    private final String name;
    private final String unit;

    protected HealthMetric(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}

