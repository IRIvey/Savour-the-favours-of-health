import java.util.Scanner;

public class HealthTrackingSystem {
    private static HealthTrackingSystem instance;
    private final CommandExecutor commandExecutor;
    private final MenuDisplay menuDisplay;
    private final Scanner scanner;
    private final User currentUser;

    private HealthTrackingSystem() {
        this.currentUser = new User("DefaultUser");
        this.commandExecutor = new CommandExecutor();
        this.menuDisplay = new MenuDisplay();
        this.scanner = new Scanner(System.in);
    }

    public static HealthTrackingSystem getInstance() {
        if (instance == null) {
            instance = new HealthTrackingSystem();
        }
        return instance;
    }

    public void start() {
        while (true) {
            menuDisplay.showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Thank you for using Health Tracking System!");
                break;
            }

            commandExecutor.executeCommand(choice, currentUser);
        }
    }
}