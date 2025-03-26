import java.util.Scanner;

public class StepTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Step Tracking ===");
        System.out.print("Enter steps taken: ");
        int steps = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(HealthMetricType.STEPS_TAKEN, steps, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nStep Tracking Statistics:");
        int totalSteps = user.getHealthHistory().stream()
                .filter(data -> data.getType() == HealthMetricType.STEPS_TAKEN)
                .mapToInt(data -> (int) data.getValue())
                .sum();
        System.out.println("Total steps today: " + totalSteps);
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetricType() == HealthMetricType.STEPS_TAKEN)
                .forEach(goal -> {
                    int totalSteps = user.getHealthHistory().stream()
                            .filter(data -> data.getType() == HealthMetricType.STEPS_TAKEN)
                            .mapToInt(data -> (int) data.getValue())
                            .sum();

                    if (totalSteps >= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Congratulations! You've achieved your step goal!");
                    }
                });
    }
}

