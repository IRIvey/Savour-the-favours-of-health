import java.util.Scanner;

public class ExerciseTracker implements Tracker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.println("\n=== Exercise Tracking ===");
        System.out.print("Enter exercise duration (minutes): ");
        double minutes = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(new ExerciseDurationMetric(), minutes, notes);
        user.addHealthData(data);

        checkGoals(user);
        displayStats(user);
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\nExercise Statistics:");
        double totalExercise = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof ExerciseDurationMetric)
                .mapToDouble(HealthData::getValue)
                .sum();
        System.out.println("Total exercise time: " + totalExercise + " minutes");
    }

    @Override
    public void checkGoals(User user) {
        double totalExercise = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof ExerciseDurationMetric)
                .mapToDouble(HealthData::getValue)
                .sum();

        String notes = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof ExerciseDurationMetric)
                .map(HealthData::getNotes)
                .findFirst()
                .orElse("No notes provided");

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof ExerciseDurationMetric) {
                double goalValue = goal.getTargetValue();
                goal.checkIfAchieved(totalExercise);
                System.out.println("\n📊 Exercise Goal Progress:");
                System.out.println("➡ Goal: " + goalValue + " minutes");
                System.out.println("➡ Recorded: " + totalExercise + " minutes");
                if (goal.isAchieved()) {
                    System.out.println("✅ Goal Achieved! 🎉 Keep moving!");
                } else {
                    double difference = goalValue - totalExercise;
                    System.out.println("❌ Goal Not Achieved. You need " + difference + " more minutes.");
                }

                System.out.println("📝 Notes: " + notes);
            }
        }
    }


}

