import java.util.Scanner;



public class HealthTrackingSystem {
    private static HealthTrackingSystem instance;
    private final CommandExecutor commandExecutor;
    private final MenuDisplay menuDisplay;
    private final Scanner scanner;
    private final User currentUser;
    private final UserInputHandler userInputHandler;

    private HealthTrackingSystem(UserInputHandler userInputHandler, CommandExecutor commandExecutor, MenuDisplay menuDisplay) {
        this.currentUser = new User("DefaultUser");
        this.commandExecutor = commandExecutor;
        this.menuDisplay = menuDisplay;
        this.scanner = new Scanner(System.in);
        this.userInputHandler = userInputHandler;
    }

    public static HealthTrackingSystem getInstance() {
        if (instance == null) {
            instance = new HealthTrackingSystem(new UserInputHandler(), new CommandExecutor(), new MenuDisplay());
        }
        return instance;
    }

    public void start() {
        while (true) {
            menuDisplay.showMenu();
            int choice = userInputHandler.getUserChoice();
            if (choice == 0) {
                System.out.println("Thank you for using Health Tracking System!");
                break;
            }
            commandExecutor.executeCommand(choice, currentUser);
        }
    }
}