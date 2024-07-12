package sk.tuke.gamestudio.game.consoleui;

import java.util.Scanner;

public class LevelSelector {

    private final Scanner scanner;

    public LevelSelector() {
        scanner = new Scanner(System.in);
    }

    public int chooseLevel() {
        int level;
        while (true) {
            System.out.println("Choose a level: (1-7). Enter the corresponding number:");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("X")) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            try {
                level = Integer.parseInt(input);
                if (level < 1 || level > 7) {
                    System.out.println("Invalid input. Please choose a level between 1 and 7.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
            }
        }
        return level;
    }
}