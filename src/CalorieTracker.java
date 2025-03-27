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

        String notes = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof CalorieMetric)
                .map(HealthData::getNotes)
                .findFirst()
                .orElse("No notes provided");

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof CalorieMetric) {
                double goalValue = goal.getTargetValue();
                goal.checkIfAchieved(totalCalories);
                System.out.println("\n📊 Calorie Goal Progress:");
                System.out.println("➡ Goal: " + goalValue + " kcal");
                System.out.println("➡ Recorded: " + totalCalories + " kcal");
                if (goal.isAchieved()) {
                    System.out.println("✅ Goal Achieved! 🎉 Well balanced!");
                } else {
                    double difference = goalValue - totalCalories;
                    System.out.println("❌ Goal Not Achieved. You need " + difference + " more kcal.");
                }
                System.out.println("📝 Notes: " + notes);
            }
        }
    }

}

