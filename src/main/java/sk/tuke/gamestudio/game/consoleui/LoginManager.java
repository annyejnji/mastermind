package sk.tuke.gamestudio.game.consoleui;

import java.util.Scanner;

public class LoginManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static void getIntoAccount() {
        System.out.println("Do you want to login (L) or register (R)?");
        String loginChoice = scanner.nextLine().trim().toUpperCase();

        switch (loginChoice) {
            case "L":
                login();
                break;
            case "R":
                RegistrationManager.registerUser();
                break;
            default:
                System.out.println("Invalid choice. Please select either 'L' for login or 'R' for registration.");
                getIntoAccount();
        }
    }

    public static void login() {
        boolean loggedIn = false;
        while (!loggedIn) {
            loggedIn = enterLoginAttributes();
            if (!loggedIn) {
                System.out.println("Invalid username or password. You cannot be logged on.");
            }
        }
    }

    private static boolean enterLoginAttributes() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();


        return CredentialsManager.login(username, password);
    }
}