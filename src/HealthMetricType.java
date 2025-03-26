public enum HealthMetricType {
    WATER_INTAKE("Water Intake", "ml"),
    SLEEP_DURATION("Sleep Duration", "hours"),
    EXERCISE_DURATION("Exercise Duration", "minutes"),
    CALORIES_CONSUMED("Calories", "kcal"),
    WEIGHT("Weight", "kg"),
    STEPS_TAKEN("Steps", "count");

    private final String displayName;
    private final String unit;

    HealthMetricType(String displayName, String unit) {
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