import metric.*;
import tracker.Tracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();
    private final Map<Integer, HealthMetric> metricMap = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public CommandExecutor() {
        metricMap.put(1, new WaterIntakeMetric());
        metricMap.put(2, new SleepDurationMetric());
        metricMap.put(3, new ExerciseDurationMetric());
        metricMap.put(4, new CalorieMetric());
        metricMap.put(5, new WeightMetric());
        metricMap.put(6, new StepMetric());
    }

    public void executeCommand(int choice, User user) {
        if (choice == 7) {
            setGoal(user);
            return;
        }

        HealthMetric metric = metricMap.get(choice);

        if (metric != null) {
            Tracker tracker = trackerFactory.createTracker(metric);
            tracker.track(user);
        } else {
            System.out.println("Invalid command choice: " + choice);
        }
    }

    private void setGoal(User user) {
        System.out.println("=== Set a Goal ===");
        System.out.println("Choose a metric to set a goal:");
        for (Map.Entry<Integer, HealthMetric> entry : metricMap.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName());
        }

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        HealthMetric selectedMetric = metricMap.get(choice);
        if (selectedMetric == null) {
            System.out.println("Invalid metric choice.");
            return;
        }

        System.out.print("Enter target value for " + selectedMetric.getName() + ": ");
        double targetValue = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Choose goal period: 1. Daily  2. Weekly  3. Monthly");
        int periodChoice = scanner.nextInt();
        scanner.nextLine();

        GoalPeriod period;
        switch (periodChoice) {
            case 1 -> period = new DailyGoal();
            case 2 -> period = new WeeklyGoal();
            case 3 -> period = new MonthlyGoal();
            default -> {
                System.out.println("Invalid goal period.");
                return;
            }
        }

        Goal newGoal = new Goal(selectedMetric, targetValue, period);
        user.addGoal(newGoal);
        System.out.println("✅ Goal set for " + selectedMetric.getName() + " (" + period.getPeriodName() + ")!");
    }
}
