package sk.tuke.gamestudio.game.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DecodingBoard {
    private final List<Row> rows;
    private final List<CodePeg> solution;
    private final int rowsCount;
    private final int colsCount;
    private int currentRow;

    public DecodingBoard(int rowsCount, int colsCount) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.rows = generateRows(rowsCount, colsCount);
        this.solution = generateSolution(colsCount);
    }

    public State isSolved() {
        boolean rowSolved = rows.get(currentRow).isSolved();

        if (rowSolved) {
            return State.WON;
        }
        if (currentRow < (rowsCount - 1)) {
            currentRow++;
            return State.PLAYING;
        }
        return State.FAILED;
    }

    private List<Row> generateRows(int rowsCount, int colsCount) {
        List<Row> rows = new ArrayList<>();

        for (int i = 0; i < rowsCount; i++) {
            Row row = new Row(colsCount);
            rows.add(row);
        }
        return rows;
    }

    private List<CodePeg> generateSolution(int colsCount) {
        List<CodePeg> solution = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < colsCount; i++) {
            int randomIndex = random.nextInt(CodePegColor.values().length);
            CodePegColor randomColor = CodePegColor.values()[randomIndex];
            CodePeg codePeg = new CodePeg(randomColor);
            solution.add(codePeg);
        }
        return solution;
    }

    public List<Row> getRows() {
        return rows;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColsCount() {
        return colsCount;
    }

    public List<CodePeg> getSolution() {
        return solution;
    }
}