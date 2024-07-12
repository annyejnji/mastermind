package sk.tuke.gamestudio.game.consoleui;

public class RandomLevelSelectionStrategy implements LevelSelectionStrategy {
    @Override
    public int selectLevel() {
        return 3 + (int) (Math.random() * 6);
    }
}
