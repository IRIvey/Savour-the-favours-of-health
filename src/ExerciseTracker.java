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
        double totalCalories = user.getHealthHistory().stream()
                .filter(data -> data.getMetric() instanceof CalorieMetric)
                .mapToDouble(HealthData::getValue)
                .sum();

        for (Goal goal : user.getGoals()) {
            if (goal.getMetric() instanceof CalorieMetric) {
                goal.checkIfAchieved(totalCalories);

                if (goal.isAchieved()) {
                    System.out.println("🎯 You reached your calorie goal of " + goal.getTargetValue() + " kcal!");
                }
            }
        }
    }

}

