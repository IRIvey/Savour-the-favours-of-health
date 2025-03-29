import java.util.Scanner;
import java.util.List;

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

        System.out.println("✅ Calories logged: " + calories + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Calorie Intake History:");
        List<HealthData> calorieHistory = user.getHistoryForMetric(metric);
        for (HealthData data : calorieHistory) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit() +
                    " (" + (data.getNotes().isEmpty() ? "No notes" : data.getNotes()) + ")");
        }
    }

    @Override
    public void checkGoals(User user) {
        double totalCalories = user.getTotalRecordedValue(metric);
        Goal goal = user.getGoalForMetric(metric);

        if (goal != null) {
            goal.checkIfAchieved(totalCalories);

            System.out.println("\n📊 Calorie Goal Progress:");
            System.out.println("➡ Goal: " + goal.getTargetValue() + " kcal");
            System.out.println("➡ Recorded: " + totalCalories + " kcal");

            if (goal.isAchieved()) {
                System.out.println("✅ Goal Achieved! 🎉 Well balanced!");
            } else {
                double difference = goal.getTargetValue() - totalCalories;
                System.out.println("❌ Goal Not Achieved. You need " + difference + " more kcal.");
            }
        } else {
            System.out.println("⚠ No goal set for Calories.");
        }
    }
}


