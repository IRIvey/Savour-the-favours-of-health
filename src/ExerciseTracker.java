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
        user.getGoals().stream()
                .filter(goal -> goal.getMetric() instanceof ExerciseDurationMetric)
                .forEach(goal -> {
                    double totalExercise = user.getHealthHistory().stream()
                            .filter(data -> data.getMetric() instanceof ExerciseDurationMetric)
                            .mapToDouble(HealthData::getValue)
                            .sum();

                    if (totalExercise >= goal.getTargetValue() && !goal.isAchieved()) {
                        goal.setAchieved(true);
                        System.out.println("🎉 Keep it up! You've achieved your exercise goal!");
                    }
                });
    }
}

