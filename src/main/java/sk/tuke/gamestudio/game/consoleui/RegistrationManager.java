package sk.tuke.gamestudio.game.consoleui;

import java.util.Scanner;

public class RegistrationManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        String password;

        do {
            System.out.print("Enter password. It must be at least 8 characters long: ");
            password = scanner.nextLine().trim();
        } while (password.length() < 8);

        if (CredentialsManager.register(username, password)) {
            System.out.println("Registration successful.");
            LoginManager.getIntoAccount();
        } else {
            System.out.println("Username already exists. Please choose another one.");
            registerUser();
        }
    }
}