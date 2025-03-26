import java.util.Scanner;

public class CalorieTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Calorie Tracking ===");
        System.out.print("Enter calories consumed: ");
        double calories = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(HealthMetricType.CALORIES_CONSUMED, calories, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nCalorie Intake Statistics:");
        double totalCalories = user.getHealthHistory().stream()
                .filter(data -> data.getType() == HealthMetricType.CALORIES_CONSUMED)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total calories consumed today: " + totalCalories + " kcal");
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetricType() == HealthMetricType.CALORIES_CONSUMED)
                .forEach(goal -> {
                    double totalCalories = user.getHealthHistory().stream()
                            .filter(data -> data.getType() == HealthMetricType.CALORIES_CONSUMED)
                            .mapToDouble(HealthData::getValue)
                            .sum();

                    if (totalCalories <= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Congratulations! You've achieved your calorie intake goal!");
                    }
                });
    }
}
