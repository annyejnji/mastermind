package sk.tuke.gamestudio.game.consoleui;

import sk.tuke.gamestudio.game.core.CodePeg;
import sk.tuke.gamestudio.game.core.CodePegColor;
import sk.tuke.gamestudio.game.core.KeyPeg;
import sk.tuke.gamestudio.game.core.KeyPegColor;

import java.util.HashMap;
import java.util.Map;

public class Design {
    private final Map<String, String> COLORS;

    public Design() {
        COLORS = new HashMap<>();
        initializeColors();
    }

    private void initializeColors() {
        COLORS.put("SKY_BLUE", "\u001B[38;2;173;216;230m");
        COLORS.put("RED", "\u001B[0;31m");
        COLORS.put("GREEN", "\u001B[32m");
        COLORS.put("YELLOW", "\u001B[33m");
        COLORS.put("MAGENTA", "\u001B[35m");
        COLORS.put("ORANGE", "\u001B[38;5;208m");
        COLORS.put("INDIGO", "\u001B[94m");
        COLORS.put("VIOLET", "\u001B[0;35m");
        COLORS.put("RESET", "\u001B[0m");
    }

    public void printTitle() {
        System.out.println(getColor("SKY_BLUE") + " _______               __                        __           __ ");
        System.out.println("|   |   |.---.-.-----.|  |_.-----.----.--------.|__|.-----.--|  |");
        System.out.println("|       ||  _  |__ --||   _|  -__|   _|        ||  ||     |  _  |");
        System.out.println("|__|_|__||___._|_____||____|_____|__| |__|__|__||__||__|__|_____|" + getColor("RESET"));
        System.out.println();
    }

    public void printWinningMessage() {
        System.out.println(getColor("GREEN") + "__   __                     _         _ _ _ ");
        System.out.println("\\ \\ / /__  _   _  __      _(_)_ __   | | | |");
        System.out.println(" \\ V / _ \\| | | | \\ \\ /\\ / / | '_ \\  | | | |");
        System.out.println("  | | (_) | |_| |  \\ V  V /| | | | | |_|_|_|");
        System.out.println("  |_|\\___/ \\__,_|   \\_/\\_/ |_|_| |_| (_|_|_)" + getColor("RESET"));
        System.out.println("\n**Congratulation**\nSolution:");
    }

    public void printFailingMessage() {
        System.out.println(getColor("RED") + "__   __            _                         ");
        System.out.println("\\ \\ / /__  _   _  | | ___  ___  ___          ");
        System.out.println(" \\ V / _ \\| | | | | |/ _ \\/ __|/ _ \\          ");
        System.out.println("  | | (_) | |_| | | | (_) \\__ \\  __/         ");
        System.out.println("  |_|\\___/ \\__,_| |_|\\___/|___/\\___|         " + getColor("RESET"));
    }

    public void printHyphens(int colsCount) {
        for (int j = 0; j < (colsCount * 2) + 14; j++) {
            System.out.print("-");
        }
    }

    public void printCodePeg(CodePeg codePeg) {
        CodePegColor color = codePeg.getColor();
        if (color == null) {
            System.out.print("*");
            return;
        }
        String formatted = switch (color) {
            case RED -> getColor("RED") + "●" + getColor("RESET");
            case BLUE -> getColor("SKY_BLUE") + "●" + getColor("RESET");
            case GREEN -> getColor("GREEN") + "●" + getColor("RESET");
            case YELLOW -> getColor("YELLOW") + "●" + getColor("RESET");
            case MAGENTA -> getColor("MAGENTA") + "●" + getColor("RESET");
            case ORANGE -> getColor("ORANGE") + "●" + getColor("RESET");
            case INDIGO -> getColor("INDIGO") + "●" + getColor("RESET");
            case WHITE -> "●";
        };
        System.out.print(formatted);
    }

    public void printKeyPeg(KeyPeg keyPeg) {
        KeyPegColor color = keyPeg.getColor();
        if (color == null) {
            System.out.print("*");
            return;
        }
        String formatted = switch (color) {
            case RED -> getColor("RED") + "●" + getColor("RESET");
            case WHITE -> "●";
        };
        System.out.print(formatted);
    }

    public String getColor(String colorName) {
        return COLORS.getOrDefault(colorName, "");
    }

    public void printRatingTitle() {
        System.out.println(getColor("SKY_BLUE") + " _____     _   _             ");
        System.out.println("| __  |___| |_|_|___ ___ ___ ");
        System.out.println("|    -| .'|  _| |   | . |_ -|");
        System.out.println("|__|__|__,|_| |_|_|_|_  |___|");
        System.out.println("                    |___|    " + getColor("RESET"));
        System.out.println();
    }

    public void printCommentTitle() {
        System.out.println(getColor("SKY_BLUE") + "_____                         _      _       _   _");
        System.out.println("|     |___ _____ _____ ___ ___| |_   | |_ ___| |_| |___");
        System.out.println("|   --| . |     |     | -_|   |  _|  |  _| .'| . | | -_|");
        System.out.println("|_____|___|_|_|_|_|_|_|___|_|_|_|    |_| |__,|___|_|___|" + getColor("RESET"));
        System.out.println();
    }

    public void printScoreTitle() {
        System.out.println(getColor("SKY_BLUE") + " _____                     ");
        System.out.println("|   __|___ ___ ___ ___ ___ ");
        System.out.println("|__   |  _| . |  _| -_|_ -|");
        System.out.println("|_____|___|___|_| |___|___|" + getColor("RESET"));
        System.out.println();

    }
}
