package sk.tuke.gamestudio.game.consoleui;

public class ConstantLevelSelectionStrategy implements LevelSelectionStrategy {
    private final int level;

    public ConstantLevelSelectionStrategy(int level) {
        this.level = level;
    }

    @Override
    public int selectLevel() {
        return level;
    }

}