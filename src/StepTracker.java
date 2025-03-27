import java.util.Scanner;

public class StepTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Step Tracking ===");
        System.out.print("Enter step count: ");
        double steps = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new StepMetric(), steps, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nStep Statistics:");
        double totalSteps = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof StepMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total steps recorded: " + totalSteps);
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetric() instanceof StepMetric)
                .forEach(goal -> {
                    double totalSteps = user.getHealthHistory().stream()
                            .filter(data -> data.getMetric() instanceof StepMetric)
                            .mapToDouble(HealthData::getValue)
                            .sum();

                    if (totalSteps >= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Amazing! You've reached your step goal!");
                    }
                });
    }
}
