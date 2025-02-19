import java.util.Scanner;

public class SleepTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Sleep Tracking ===");
        System.out.print("Enter sleep duration (hours): ");
        double hours = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(HealthMetricType.SLEEP_DURATION, hours, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nSleep Statistics:");
        double totalSleep = user.getHealthHistory().stream()
                .filter(data -> data.getType() == HealthMetricType.SLEEP_DURATION)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total sleep recorded: " + totalSleep + " hours");
    }

    @Override
    public void checkGoals(User user) {
        user.getGoals().stream()
                .filter(goal -> goal.getMetricType() == HealthMetricType.SLEEP_DURATION)
                .forEach(goal -> {
                    double totalSleep = user.getHealthHistory().stream()
                            .filter(data -> data.getType() == HealthMetricType.SLEEP_DURATION)
                            .mapToDouble(HealthData::getValue)
                            .sum();

                    if (totalSleep >= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Congratulations! You've achieved your sleep goal!");
                    }
                });
    }
}
