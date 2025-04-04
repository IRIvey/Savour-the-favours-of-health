package system;

import user.User;
import challenge.ChallengeTracker;
import storage.StorageManager;
import storage.UserDataWrapper;

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

    public void start() {

        UserDataWrapper data = StorageManager.load();

        if (data == null) {
            System.out.print("Enter your name to begin: ");
            String name = scanner.nextLine();
            user = new User(name);
            tracker = ChallengeTracker.getInstance();
            System.out.println("✅ New profile created for " + name);
        } else {
            user = data.getUser();
            ChallengeTracker.setInstance(data.getChallengeTracker());
            tracker = ChallengeTracker.getInstance();
        }

        executor = new CommandExecutor(tracker);
        menuDisplay = new MenuDisplay();

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
