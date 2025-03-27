import java.util.Scanner;

public class CalorieTracker implements Tracker {
    private final HealthMetric metric = new CalorieMetric();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.print("Enter calories consumed: ");
        double calories = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? (optional): ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(metric, calories, notes);
        user.addHealthData(data);

        checkGoals(user); // 🔥 Now checks goals inside `CalorieTracker`

        System.out.println("✅ Calories logged: " + calories + " " + metric.getUnit());
    }


    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Calorie Intake History:");
        for (HealthData data : user.getHealthHistory()) {
            if (data.getMetric() instanceof CalorieMetric) {
                System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit() + " (" + data.getNotes() + ")");
            }
        }
    }

    @Override
    public void checkGoals(User user) {
        double totalCalories = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof CalorieMetric)
                .mapToDouble(HealthData::getValue)
                .sum();

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof CalorieMetric) {
                goal.checkIfAchieved(totalCalories);

                if (goal.isAchieved()) {
                    System.out.println("🎯 You reached your calorie goal of " + goal.getTargetValue() + " kcal!");
                }
            }
        }
    }
}

