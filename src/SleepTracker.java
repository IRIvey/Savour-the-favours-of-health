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

        HealthData data = new HealthData(new SleepDurationMetric(), hours, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nSleep Statistics:");
        double totalSleep = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof SleepDurationMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total sleep recorded: " + totalSleep + " hours");
    }

    @Override
    public void checkGoals(User user) {
        double totalSleep = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof SleepDurationMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        String notes = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof SleepDurationMetric)
                .map(HealthData::getNotes)
                .findFirst()
                .orElse("No notes provided");

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof SleepDurationMetric) {
                double goalValue = goal.getTargetValue();
                goal.checkIfAchieved(totalSleep);

                System.out.println("\n📊 Sleep Goal Progress:");
                System.out.println("➡ Goal: " + goalValue + " hours (" + goal.getPeriod().getPeriodName() + ")");
                System.out.println("➡ Recorded: " + totalSleep + " hours");

                if (goal.isAchieved()) {
                    System.out.println("✅ Goal Achieved! 🎉 Great job!");
                } else {
                    double difference = goalValue - totalSleep;
                    System.out.println("❌ Goal Not Achieved. You need " + difference + " more hours.");
                }


                System.out.println("📝 Notes: " + notes);
            }
        }
    }

}


