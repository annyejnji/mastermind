package sk.tuke.gamestudio.game.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.core.*;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

// urobit video
// testy musia ist v intellij
// put a delete testy

public class ConsoleUI {

    private DecodingBoard board;
    private final Scanner scanner;
    private final LevelSelector levelSelector;
    private final Design design;
    private String player;
    private int score;
    private boolean gameWasExitedWhilePlaying;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
        levelSelector = new LevelSelector();
        design = new Design();
        this.score = 0;
        this.gameWasExitedWhilePlaying = false;
    }

    public RatingService getRatingService() {
        return ratingService;
    }

    public void startGame() {
        design.printTitle();
        LoginManager.getIntoAccount();
        player = CredentialsManager.getCurrentUser();
        this.score = 9;
        manageScore();
        managingDatabase();
        managingDatabase();
        chooseLevel();
    }


    public void uselessFunction() {
        String colorsString = "";

        Row currentRow = board.getRows().get(board.getCurrentRow());
        List<CodePeg> codePegs = currentRow.getCodePegs();
        List<KeyPeg> keyPegs = currentRow.getKeyPegs();

        String input = "RBBG";

        String[] iColors = input.replaceAll("\\s", "").split("");
        List<String> inputColors = new ArrayList<>();

        for (String color : iColors) {
            if (!color.contains("\"")) {
                inputColors.add(color);
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String color : inputColors) {
            builder.append(color);
        }
        String inputColorsvypis = builder.toString();


        List<CodePegColor> validatedColors = new ArrayList<>();

        for (String inputColor : inputColors) {
            CodePegColor color = null;

            for (CodePegColor cColor : CodePegColor.values()) {
                if (cColor.name().startsWith(inputColor)) {
                    color = cColor;
                    break;
                }
            }

            validatedColors.add(color);
        }


        for (int i = 0; i < validatedColors.size(); i++) {
            CodePegColor color = validatedColors.get(i);
            System.out.println("color on a line " + i + "is" + color);
            codePegs.get(i).setColor(color);
            System.out.println("codepegs.size on line " + i + "is" + codePegs.size());
            System.out.println("in codepegs.get(i) on line " + i + "is" + codePegs.get(i));
        }
    }


    private void chooseLevel() {
        int level = levelSelector.chooseLevel();

        if (level < 1 || level > 7) {
            System.out.println("Invalid level.");
            chooseLevel();
            return;
        }

        LevelSelectionStrategy strategy;
        if (level == 7) {
            strategy = new RandomLevelSelectionStrategy();
        } else {
            strategy = new ConstantLevelSelectionStrategy(level);
        }

        int levelCol = strategy.selectLevel() + 2;
        play(new DecodingBoard(10, levelCol));
    }

    private void askForRating() {
        int ratingInput;
        System.out.println("What rating will you give this game? (1-5)");
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer rating.");
            scanner.next();
        }
        ratingInput = scanner.nextInt();
        scanner.nextLine();
        if (ratingInput < 1 || ratingInput > 5) {
            System.out.println("Rating could not be added");
        } else {
            ratingService.setRating(new Rating("mastermind", player, ratingInput, new Date()));
        }


        List<Rating> ratings = ratingService.getRating("mastermind");
        System.out.println();
        design.printRatingTitle();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        IntStream.range(0, ratings.size())
                .forEach(i -> {
                    Rating rating = ratings.get(i);
                    System.out.println((i + 1) + ". " + rating.getPlayer() + "\t" + rating.getRating() + "\t" + sdf.format(rating.getRatedOn()));
                });
        System.out.println();

        int averageRating = ratingService.getAverageRating("mastermind");
        System.out.println("Average rating is " + averageRating);
    }

    private void askForComment() {
        String commentText;
        System.out.println("Okay, you can write a comment now: ");
        commentText = scanner.nextLine().trim();
        Comment inputComment = new Comment("mastermind", player, commentText, new Date());
        commentService.addComment(inputComment);
        List<Comment> comments = commentService.getComments("mastermind");
        System.out.println();
        design.printCommentTitle();

        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            System.out.println((i + 1) + ". " + comment.getComment());
        }
        System.out.println();
    }

    private void manageScore() {
        scoreService.addScore(new Score("mastermind", player, score, new Date()));

        design.printScoreTitle();
        List<Score> scores = scoreService.getTopScores("mastermind");
        if (!scores.isEmpty()) {
            System.out.println("Scores for all players:");
            scores.stream()
                    .forEach(score -> System.out.println(score.getPlayer() + ": " + score.getPoints()));
        }
    }

    private void managingDatabaseChoices() {
        if (!gameWasExitedWhilePlaying) {
            score = 9 - board.getCurrentRow();
            System.out.println("Your score: " + score);
        }

        manageScore();

        System.out.println("\nIf you want to add rating  press R. If you want to add comment press C.");

        String inputChoice = scanner.nextLine().trim().toUpperCase();
        if (inputChoice.equals("R")) {
            askForRating();
            playAgainRequest();
        } else if (inputChoice.equals("C")) {
            askForComment();
            playAgainRequest();
        } else {
            playAgainRequest();
        }
    }

    private void managingDatabase() {
        System.out.println("\nIf you want to add rating  press R. If you want to add comment press C.");

        String inputChoice = scanner.nextLine().trim().toUpperCase();
        if (inputChoice.equals("R")) {
            askForRating();
        } else if (inputChoice.equals("C")) {
            askForComment();
        }
    }

    private void play(DecodingBoard board) {
        this.board = board;
        //printSolution();
        do {
            show();
            handleInput();
        } while (board.isSolved() == State.PLAYING);

        show();

        if (board.isSolved() == State.WON) {
            design.printWinningMessage();
            printSolution();
            managingDatabaseChoices();

        } else if (board.isSolved() == State.FAILED) {
            design.printFailingMessage();
            printSolutionChoice();
            System.out.println();
            playAgainRequest();
        }
    }

//    public String playWEB(DecodingBoard board, String colorRows) {
//        this.board = board;
//        //printSolution();
//
//        if (board.isSolved() == State.PLAYING) {
//
//            handleInputWEB(colorRows);
//            show();
//        } else if (board.isSolved() == State.WON) {
//            design.printWinningMessage();
//            printSolution();
//            managingDatabaseChoices();
//
//        } else if (board.isSolved() == State.FAILED) {
//            design.printFailingMessage();
//            printSolutionChoice();
//            System.out.println();
//            playAgainRequest();
//        }
//    }

    public String playWEB(DecodingBoard board, String colorRows) {
        this.board = board;
        //printSolution();

        if (board.isSolved() == State.PLAYING) {

//            handleInputWEB(colorRows);
//            show();
            return "state: playing";
        } else if (board.isSolved() == State.WON) {
//            design.printWinningMessage();
//            printSolution();
//            managingDatabaseChoices();
            return "state: won";

        } else if (board.isSolved() == State.FAILED) {
//            design.printFailingMessage();
//            printSolutionChoice();
//            System.out.println();
//            playAgainRequest();
            return "state: lost";
        }
        return "state: null, the board doesnt have a state- it is an error, it should have it";
    }

    private void handleInput() {
        Row currentRow = board.getRows().get(board.getCurrentRow());
        List<CodePeg> codePegs = currentRow.getCodePegs();

        System.out.println("Enter colors. Use only the first letters of each color!\n" +
                "You can choose from R = " + design.getColor("RED") + "●" + design.getColor("RESET") +
                ", G = " + design.getColor("GREEN") + "●" + design.getColor("RESET") +
                ", B = " + design.getColor("SKY_BLUE") + "●" + design.getColor("RESET") +
                ", Y = " + design.getColor("YELLOW") + "●" + design.getColor("RESET") +
                ", M = " + design.getColor("MAGENTA") + "●" + design.getColor("RESET") +
                ", O = " + design.getColor("ORANGE") + "●" + design.getColor("RESET") +
                ", W = " + design.getColor("RESET") + "●" +
                ", I = " + design.getColor("INDIGO") + "●" + design.getColor("RESET") +
                ".\n(Press X to exit)");

        String input = scanner.nextLine().trim().toUpperCase();
        exitGameWhilePlaying(input);

        String[] inputColors = input.replaceAll("\\s", "").split("");
        if (!input.matches("[RGBYMOWI]+") || (inputColors.length != board.getColsCount())) {
            System.out.println("Invalid input.\n(Press X to exit)");
            handleInput();
            return;
        }

        List<CodePegColor> validatedColors = new ArrayList<>();
        // vlozit vstup do codepegs
        for (String inputColor : inputColors) {
            CodePegColor color = null;

            for (CodePegColor cColor : CodePegColor.values()) {
                if (cColor.name().startsWith(inputColor)) {
                    color = cColor;
                    break;
                }
            }

            if (color == null) {
                System.out.println("Invalid color: " + inputColor);
                return;
            }
            validatedColors.add(color);
        }

        for (int i = 0; i < validatedColors.size(); i++) {
            CodePegColor color = validatedColors.get(i);
            codePegs.get(i).setColor(color);
        }

        currentRow.generateKeyPegsForSolution(board.getSolution());
    }

    public void handleInputWEB(String rowColors) {
        Row currentRow = board.getRows().get(board.getCurrentRow());
        List<CodePeg> codePegs = currentRow.getCodePegs();
        String input = rowColors;
        String[] inputColors = input.replaceAll("\\s", "").split("");
        if (!input.matches("[RGBYMOWI]+") || (inputColors.length != board.getColsCount())) {
            handleInputWEB(rowColors);
            return;
        }

        List<CodePegColor> validatedColors = new ArrayList<>();

        for (String inputColor : inputColors) {
            CodePegColor color = null;

            for (CodePegColor cColor : CodePegColor.values()) {
                if (cColor.name().startsWith(inputColor)) {
                    color = cColor;
                    break;
                }
            }

            validatedColors.add(color);
        }

        for (int i = 0; i < validatedColors.size(); i++) {
            CodePegColor color = validatedColors.get(i);
            codePegs.get(i).setColor(color);
        }

        currentRow.generateKeyPegsForSolution(board.getSolution());
    }

    private void show() {
        List<Row> rows = board.getRows();
        int rowsCount = board.getRowsCount();

        // Prechadzam po riadkoch
        for (int i = 0; i < rowsCount; i++) {
            Row currentRow = rows.get(i);
            List<CodePeg> codePegs = currentRow.getCodePegs();
            List<KeyPeg> keyPegs = currentRow.getKeyPegs();

            // codePegs
            System.out.print(design.getColor("VIOLET") + "Code Pegs: " + design.getColor("RESET") + "| ");
            for (CodePeg codePeg : codePegs) {
                design.printCodePeg(codePeg);
                System.out.print(" ");
            }
            System.out.println("|");

            System.out.print(design.getColor("INDIGO") + " Key Pegs: " + design.getColor("RESET") + "| ");
            for (KeyPeg keyPeg : keyPegs) {
                design.printKeyPeg(keyPeg);
                System.out.print(" ");
            }
            System.out.println("|");

            int colsCount = board.getColsCount();
            design.printHyphens(colsCount);
            System.out.println();
        }
    }

    public void showWeb() {
        List<Row> rows = board.getRows();
        int rowsCount = board.getRowsCount();

        // Prechadzam po riadkoch
        for (int i = 0; i < rowsCount; i++) {
            Row currentRow = rows.get(i);
            List<CodePeg> codePegs = currentRow.getCodePegs();
            List<KeyPeg> keyPegs = currentRow.getKeyPegs();

            // codePegs
            System.out.print(design.getColor("VIOLET") + "Code Pegs: " + design.getColor("RESET") + "| ");
            for (CodePeg codePeg : codePegs) {
                design.printCodePeg(codePeg);
                System.out.print(" ");
            }
            System.out.println("|");

            System.out.print(design.getColor("INDIGO") + " Key Pegs: " + design.getColor("RESET") + "| ");
            for (KeyPeg keyPeg : keyPegs) {
                design.printKeyPeg(keyPeg);
                System.out.print(" ");
            }
            System.out.println("|");

            int colsCount = board.getColsCount();
            design.printHyphens(colsCount);
            System.out.println();
        }
    }

    private void playAgainRequest() {
        System.out.println("Do you want to stay in game?\nPress Y if yes or X if no");
        String input = scanner.nextLine().trim().toUpperCase();
        exitGameAfterPlay(input);
        playAgainIfYes(input);

        if (!input.equals("Y") || !input.equals("X")) {
            System.out.println("Invalid input.");
            playAgainRequest();
        }
    }

    private void printSolutionChoice() {
        System.out.println("Do you want to see solution? Y = yes, X = no");
        String seeSolution = scanner.nextLine().trim().toUpperCase();
        if (seeSolution.equals("Y")) {
            System.out.println("\nCorrect solution was:");
            printSolution();
        }
    }

    private void printSolution() {
        List<CodePeg> solution = board.getSolution();
        for (CodePeg codePeg : solution) {
            design.printCodePeg(codePeg);
            System.out.print(" ");
        }
        System.out.println();
    }

    private void exitGameWhilePlaying(String input) {
        if (input.equals("X")) {
            System.out.println("Exiting the game...");
            gameWasExitedWhilePlaying = true;
            System.exit(0);
        }
    }

    private void exitGameAfterPlay(String input) {
        if (input.equals("X")) {
            System.out.println("Exiting the game...");
            gameWasExitedWhilePlaying = true;
            System.exit(0);
        }
    }

    private void playAgainIfYes(String input) {
        if (input.equals("Y")) {
            chooseLevel();
        }
    }
}