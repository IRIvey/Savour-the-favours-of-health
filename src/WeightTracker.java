import java.util.Scanner;

public class WeightTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Weight Tracking ===");
        System.out.print("Enter your current weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new WeightMetric(), weight, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nWeight Statistics:");
        double latestWeight = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WeightMetric)
                .mapToDouble(HealthData::getValue)
                .reduce((first, second) -> second)
                .orElse(0);
        System.out.println("Latest recorded weight: " + latestWeight + " kg");
    }

    @Override
    public void checkGoals(User user) {
        double totalWeight = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WeightMetric)
                .mapToDouble(HealthData::getValue)
                .sum();

        String notes = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WeightMetric)
                .map(HealthData::getNotes)
                .findFirst()
                .orElse("No notes provided");

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof WeightMetric) {
                double goalValue = goal.getTargetValue();
                goal.checkIfAchieved(totalWeight);
                System.out.println("\n📊 Weight Goal Progress:");
                System.out.println("➡ Goal: " + goalValue + " kg");
                System.out.println("➡ Recorded: " + totalWeight + " kg");
                if (goal.isAchieved()) {
                    System.out.println("✅ Goal Achieved! 🎉 Keep up the great work!");
                } else {
                    double difference = goalValue - totalWeight;
                    System.out.println("❌ Goal Not Achieved. You need to lose/gain " + difference + " kg.");
                }
                System.out.println("📝 Notes: " + notes);
            }
        }
    }
}



