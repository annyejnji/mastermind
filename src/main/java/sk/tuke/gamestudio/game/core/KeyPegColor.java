package sk.tuke.gamestudio.game.core;

public enum KeyPegColor implements Comparable<KeyPegColor> {
    RED(1),
    WHITE(2);

    private final int priority;

    KeyPegColor(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
