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
        double totalWater = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WaterIntakeMetric)
                .mapToDouble(HealthData::getValue)
                .sum();

        String notes = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof WaterIntakeMetric)
                .map(HealthData::getNotes)
                .findFirst()
                .orElse("No notes provided");

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof WaterIntakeMetric) {
                double goalValue = goal.getTargetValue();
                goal.checkIfAchieved(totalWater);
                System.out.println("\n📊 Water Intake Goal Progress:");
                System.out.println("➡ Goal: " + goalValue + " liters");
                System.out.println("➡ Recorded: " + totalWater + " liters");
                if (goal.isAchieved()) {
                    System.out.println("✅ Goal Achieved! 🎉 Well hydrated!");
                } else {
                    double difference = goalValue - totalWater;
                    System.out.println("❌ Goal Not Achieved. You need " + difference + " more liters.");
                }

                System.out.println("📝 Notes: " + notes);
            }
        }
    }


}
