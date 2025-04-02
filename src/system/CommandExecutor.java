package system;

import user.*;
import metric.*;
import goal.*;
import main.*;
import factory.*;
import tracker.*;
import challenge.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();
    private final Map<Integer, HealthMetric> metricMap = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final ChallengeTracker tracker;

    public CommandExecutor(ChallengeTracker tracker) {
        this.tracker = tracker;
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

        if (choice == 8) {
            handleChallenge(user);
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

    private void handleChallenge(User user) {
        System.out.println("=== Challenge Menu ===");
        System.out.println("1. Start a new Challenge");
        System.out.println("2. Record today's progress for a Challenge");
        System.out.println("3. End a Challenge");
        System.out.println("4. List active Challenges");
        System.out.print("Enter your challenge option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> {
                System.out.println("Choose a metric for the challenge:");
                for (Map.Entry<Integer, HealthMetric> entry : metricMap.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue().getName());
                }
                System.out.print("Enter choice: ");
                int metricChoice = scanner.nextInt();
                scanner.nextLine();
                HealthMetric selectedMetric = metricMap.get(metricChoice);
                if (selectedMetric == null) {
                    System.out.println("Invalid metric choice.");
                    return;
                }
                System.out.print("Enter target value for the challenge: ");
                double targetValue = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Choose challenge period: 1. Weekly  2. Monthly");
                int periodChoice = scanner.nextInt();
                scanner.nextLine();
                try {
                    Challenge challenge = ChallengeFactory.createChallenge(selectedMetric, targetValue, periodChoice);
                    tracker.addChallenge(challenge);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid challenge period choice.");
                }
            }
            case 2 -> {
                System.out.println("Record progress for which metric?");
                for (Map.Entry<Integer, HealthMetric> entry : metricMap.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue().getName());
                }
                System.out.print("Enter choice: ");
                int metricChoice2 = scanner.nextInt();
                scanner.nextLine();
                HealthMetric progressMetric = metricMap.get(metricChoice2);
                if (progressMetric == null) {
                    System.out.println("Invalid metric choice.");
                    return;
                }
                System.out.print("Enter today's value for the metric: ");
                double value = scanner.nextDouble();
                scanner.nextLine();
                tracker.recordValue(progressMetric, value);
            }
            case 3 -> {
                System.out.println("End challenge for which metric?");
                for (Map.Entry<Integer, HealthMetric> entry : metricMap.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue().getName());
                }
                System.out.print("Enter choice: ");
                int metricChoice3 = scanner.nextInt();
                scanner.nextLine();
                HealthMetric endMetric = metricMap.get(metricChoice3);
                if (endMetric == null) {
                    System.out.println("Invalid metric choice.");
                    return;
                }
                tracker.endChallenge(endMetric);
            }
            case 4 -> tracker.listActiveChallenges();
            default -> System.out.println("Invalid challenge option.");
        }
    }
}