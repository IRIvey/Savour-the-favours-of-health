package tracker;

import java.util.Scanner;

public class SleepTracker implements Tracker {
    private final HealthMetric metric = new SleepDurationMetric();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void track(User user) {
        System.out.print("Enter sleep duration (hours): ");
        double duration = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Any notes? (optional): ");
        String notes = scanner.nextLine();

        HealthData data = new HealthData(metric, duration, notes);
        user.addHealthData(data);

        checkGoals(user);

        System.out.println("✅ Sleep logged: " + duration + " " + metric.getUnit());
    }

    @Override
    public void displayStats(User user) {
        System.out.println("\n📊 Sleep History:");
        for (HealthData data : user.getHistoryForMetric(metric)) {
            System.out.println(data.getTimestamp() + " - " + data.getValue() + " " + metric.getUnit() + " (" + data.getNotes() + ")");
        }
    }

    @Override
    public void checkGoals(User user) {
        double totalSleep = user.getTotalRecordedValue(metric);
        Goal goal = user.getGoalForMetric(metric);

        if (goal != null) {
            goal.checkIfAchieved(totalSleep);
            System.out.println("\n📊 Sleep Goal Progress:");
            System.out.println("➡ Goal: " + goal.getTargetValue() + " " + metric.getUnit());
            System.out.println("➡ Recorded: " + totalSleep + " " + metric.getUnit());
            if (goal.isAchieved()) {
                System.out.println("✅ Goal Achieved! 🎉 Well Rested!");
            } else {
                System.out.println("❌ Goal Not Achieved. Sleep " + (goal.getTargetValue() - totalSleep) + " more " + metric.getUnit() + ".");
            }
        }
    }
}
