package tracker;

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
        double totalSteps = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof StepMetric)
                .mapToDouble(HealthData::getValue)
                .sum();

        String notes = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof StepMetric)
                .map(HealthData::getNotes)
                .findFirst()
                .orElse("No notes provided");

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof StepMetric) {
                double goalValue = goal.getTargetValue();
                goal.checkIfAchieved(totalSteps);
                System.out.println("\n📊 Steps goal.Goal Progress:");
                System.out.println("➡ goal.Goal: " + goalValue + " steps");
                System.out.println("➡ Recorded: " + totalSteps + " steps");
                if (goal.isAchieved()) {
                    System.out.println("✅ goal.Goal Achieved! 🎉 Keep stepping!");
                } else {
                    double difference = goalValue - totalSteps;
                    System.out.println("❌ goal.Goal Not Achieved. You need " + difference + " more steps.");
                }
                System.out.println("📝 Notes: " + notes);
            }
        }
    }


}
