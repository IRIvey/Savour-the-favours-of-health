import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner = new Scanner(System.in);

    public int getUserChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }
}

