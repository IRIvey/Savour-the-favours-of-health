package system;

import user.User;
import metric.*;
import goal.*;
import challenge.*;
import factory.TrackerFactory;
import tracker.Tracker;
import reset.ResetManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandExecutor {
    private final TrackerFactory trackerFactory = TrackerFactory.getInstance();
    private final Map<Integer, HealthMetric> metricMap = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final ChallengeTracker tracker;
    private final MenuDisplay menuDisplay = new MenuDisplay();

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
        switch (choice) {
            case 1 -> handleTracking(user);
            case 2 -> setGoal(user);
            case 3 -> handleChallenge(user);
            case 4 -> {
                System.out.print("⚠ This will delete ALL progress. Type 'yes' to confirm: ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    ResetManager.resetAll(user);
                } else {
                    menuDisplay.showError("Reset cancelled.");
                }
            }
            case 0 -> System.out.println("👋 Exiting Health Tracking System. Goodbye!");
            default -> menuDisplay.showError("Invalid main menu choice: " + choice);
        }
    }

    private void handleTracking(User user) {
        menuDisplay.showTrackingOptions();
        int trackChoice = scanner.nextInt();
        scanner.nextLine();

        HealthMetric metric = metricMap.get(trackChoice);
        if (metric != null) {
            Tracker tracker = trackerFactory.createTracker(metric);

            menuDisplay.showSection("Tracking: " + metric.getName());
            System.out.print("Enter today's value for " + metric.getName() + ": ");
            double value = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Any notes? (optional): ");
            String notes = scanner.nextLine();
            tracker.track(user, value, notes);
        } else {
            menuDisplay.showError("Invalid tracking option.");
        }
    }

    private void setGoal(User user) {
        menuDisplay.showSection("Set a Goal");
        System.out.println("Choose a metric to set a goal:");
        for (Map.Entry<Integer, HealthMetric> entry : metricMap.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName());
        }

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        HealthMetric selectedMetric = metricMap.get(choice);
        if (selectedMetric == null) {
            menuDisplay.showError("Invalid metric choice.");
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
                menuDisplay.showError("Invalid goal period.");
                return;
            }
        }

        Goal newGoal = new Goal(selectedMetric, targetValue, period);
        user.addGoal(newGoal);
        menuDisplay.showSuccess("Goal set for " + selectedMetric.getName() + " (" + period.getPeriodName() + ")!");
    }

    private void handleChallenge(User user) {
        menuDisplay.showSection("Challenge Menu");
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
                    menuDisplay.showError("Invalid metric choice.");
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
                    menuDisplay.showError("Invalid challenge period choice.");
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
                    menuDisplay.showError("Invalid metric choice.");
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
                    menuDisplay.showError("Invalid metric choice.");
                    return;
                }
                tracker.endChallenge(endMetric);
            }
            case 4 -> tracker.listActiveChallenges();
            default -> menuDisplay.showError("Invalid challenge option.");
        }
    }
}