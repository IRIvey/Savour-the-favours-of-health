public abstract class HealthMetric {
    private final String displayName;
    private final String unit;

    public HealthMetric(String displayName, String unit) {
        this.displayName = displayName;
        this.unit = unit;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUnit() {
        return unit;
    }
}
