package system;

import user.User;
import challenge.ChallengeTracker;

import java.util.Scanner;

public class HealthTrackingSystem {
    private static HealthTrackingSystem instance;
    private final Scanner scanner = new Scanner(System.in);

    private User user;
    private ChallengeTracker tracker;
    private CommandExecutor executor;
    private MenuDisplay menuDisplay;

    private HealthTrackingSystem() {}

    public static HealthTrackingSystem getInstance() {
        if (instance == null) {
            instance = new HealthTrackingSystem();
        }
        return instance;
    }

    public void start(User user, ChallengeTracker tracker) {
        this.user = user;
        this.tracker = tracker;
        this.executor = new CommandExecutor(tracker);
        this.menuDisplay = new MenuDisplay();

        while (true) {
            menuDisplay.showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("👋 Exiting Health Tracking System. Goodbye!");
                break;
            }

            executor.executeCommand(choice, user);
        }
    }
}

