package sk.tuke.gamestudio.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.gamestudio.game.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GameController {
    //    private DecodingBoard board = new DecodingBoard(10, 1 + 2);
    private DecodingBoard board;
    private String Level;


//    @GetMapping("/skontroluj-farby")
//    public ResponseEntity<String> startGame(@RequestParam("level") int level) {
//        this.board = new DecodingBoard(10, level + 2);
//        return ResponseEntity.ok("Game started with level " + level);
//    }
//
//    @PostMapping("/skontroluj-farby")
//    public ResponseEntity<String> submitGuess(@RequestBody List<String> colors) {
//        Row currentRow = board.getRows().get(board.getCurrentRow());
//        List<CodePeg> codePegs = currentRow.getCodePegs();
//        for (int i = 0; i < colors.size(); i++) {
//            String colorName = colors.get(i).toUpperCase();
//            CodePegColor color = CodePegColor.valueOf(colorName);
//            codePegs.get(i).setColor(color);
//        }
//        currentRow.generateKeyPegsForSolution(board.getSolution());
//        if (board.isSolved() == State.WON) {
//            return ResponseEntity.ok("Congratulations! You won!");
//        } else {
//            return ResponseEntity.ok("Turn completed. Keep guessing!");
//        }
//    }

    @PostMapping("/sendLevel")
    public String createBoard(@RequestBody String level) {

        String levelWithoutQuotes = level.substring(1, level.length() - 1);
        this.Level = levelWithoutQuotes;
        this.board = new DecodingBoard(10, Integer.parseInt(Level) + 2);

        return Level;
    }

    @PostMapping("/sendInputColorRow")
    public String handlePostRequest(@RequestBody String inputColorsRow) {
        // Process the received data

//        System.out.println("Received data from client: " + inputColorsRow);
//        return consoleUI.playWEB(this.board, inputColorsRow);

//        handleInputWEB(inputColorsRow);
//        return "Input of row colors was successfully processed into consoleui's function handle input web.";
//        return Integer.toString(handleInputWEB(inputColorsRow));

//        return handleInputWEB(inputColorsRow) + Level;
        return handleInputWEB(inputColorsRow);
//        return "Input of row colors was successfully processed into consoleui's function handle input web.";
//        return processData(inputColorsRow);


        // Return a response to the client
//        return processData(inputColorsRow);
    }

    private String processData(String data) {
        // Example processing logic
        return "Processed data: " + data.toLowerCase();
    }

    public String handleInputWEB(String rowColors) {
        String colorsString = "";

        Row currentRow = board.getRows().get(board.getCurrentRow());
        List<CodePeg> codePegs = currentRow.getCodePegs();
//        List<KeyPeg> keyPegs = currentRow.getKeyPegs();

        String input = rowColors;

        String[] iColors = input.replaceAll("\\s", "").split("");
        List<String> inputColors = new ArrayList<>();

        for (String color : iColors) {
            if (!color.contains("\"")) {
                inputColors.add(color);
            }
        }


        List<CodePegColor> validatedColors = new ArrayList<>();
        CodePegColor.values();

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

//

//        StringBuilder builder2 = new StringBuilder();
//        for (CodePegColor color : validatedColors) {
//            builder2.append(color.toString()).append(" ");
//        }
//        String concatenatedColors = builder2.toString().trim();


//        StringBuilder builder2 = new StringBuilder();
//        builder2.append(color.toString()).append(" ");
//        String concatenatedColors = builder2.toString().trim();
//
        StringBuilder builder = new StringBuilder();
        for (CodePeg codePeg : codePegs) {
            if (codePeg.getColor() == null) {
                builder.append("0");
            } else {
                builder.append(codePeg.getColor().toString());
            }
        }
        String codePegsString = builder.toString();


        StringBuilder builder3 = new StringBuilder();
        for (int i = 0; i < validatedColors.size(); i++) {
            CodePegColor color = validatedColors.get(i);
            builder3.append(color.toString()).append(" ");
            codePegs.get(i).setColor(color);
//            System.out.println("codepegs.size on line " + i + "is" + codePegs.size());
//            System.out.println("in codepegs.get(i) on line " + i + "is" + codePegs.get(i));
        }
        String skuska = builder3.toString().trim();
        int skuskaSize = skuska.length();

        currentRow.generateKeyPegsForSolution(board.getSolution());
//        List<KeyPeg> keyPegs = currentRow.getKeyPegs();
        List<KeyPeg> keyPegs = currentRow.getKeyPegs();

//        String resultKeyPegs = keyPegs.stream()
//                .map(KeyPeg::toString) // Convert each enum value to its string representation
//                .collect(Collectors.joining(", ")); // Concatenate the strings with a delimiter

        String resultKeyPegs = keyPegs.stream()
                .map(KeyPeg::getColor) // Map each KeyPeg object to its value
                .map(String::valueOf) // Convert values to strings
                .collect(Collectors.joining(", "));

        return resultKeyPegs;



        /*for (KeyPeg keyPeg : keyPegs) {

        }

        return concatenatedColors;*/


//        return colorsString;
        //validatedColors.size()


//        for (int i = 0; i < validateColorSize; i++) {
//            CodePegColor color = validatedColors.get(i);
//            codePegs.get(i).setColor(color);
//        }

//        currentRow.generateKeyPegsForSolution(board.getSolution());
    }
}


// metoda/funkcia ktora spracovava request z frontendu z metody ConfimSelection
// do tejto metody odoslene do vytvorene pole v javascripte
// a skontrolujeme to s tym nasim vygenerovanym
// prijmes pole farieb a hodnotu levelu
// @GetMapping("skontroluj-farby")
//public void checkColors(@RequestParam("colors") String[] colors, @RequestParam("level") int level) {

        /*Row currentRow = board.getRows().get(board.getCurrentRow());
        List<CodePeg> codePegs = currentRow.getCodePegs();*/

// currentRow = new Row(level + 2);

//for (int i = 0; i < colors.length && i < level; i++) {
//    String color = colors[i].toUpperCase();
//   CodePegColor codePegColor = CodePegColor.valueOf(color);
//    currentRow.getCodePegs().get(i).setColor(codePegColor);
//        }
//    }

//}


// cervena, zelena, modra

// vytvor new Row(level)

// zavolat funkciu generate keypeg solution
//

// vysledok odosles na frontend
