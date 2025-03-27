import java.util.Scanner;

public class WaterTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Water Intake Tracking ===");
        System.out.print("Enter water intake (ml): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new WaterIntakeMetric(), amount, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nWater Intake Statistics:");
        double totalIntake = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WaterIntakeMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total intake today: " + totalIntake + " ml");
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetric() instanceof WaterIntakeMetric)
                .forEach(goal -> {
                    double totalIntake = user.getHealthHistory().stream()
                            .filter(data -> data.getMetric() instanceof WaterIntakeMetric)
                            .mapToDouble(HealthData::getValue)
                            .sum();

                    if (totalIntake >= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Congratulations! You've achieved your water intake goal!");
                    }
                });
    }
}
