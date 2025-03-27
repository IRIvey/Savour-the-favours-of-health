import java.util.Scanner;

public class CalorieTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Calorie Tracking ===");
        System.out.print("Enter calories consumed (kcal): ");
        double calories = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new CalorieMetric(), calories, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nCalorie Statistics:");
        double totalCalories = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof CalorieMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total calories consumed: " + totalCalories + " kcal");
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetric() instanceof CalorieMetric)
                .forEach(goal -> {
                    double totalCalories = user.getHealthHistory().stream()
                            .filter(data -> data.getMetric() instanceof CalorieMetric)
                            .mapToDouble(HealthData::getValue)
                            .sum();

                    if (totalCalories >= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 You've reached your calorie goal!");
                    }
                });
    }
}
