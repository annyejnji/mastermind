package sk.tuke.gamestudio.game.core;

import java.util.Objects;

public class KeyPeg {
    KeyPegColor color;

    public KeyPeg(KeyPegColor color) {
        this.color = color;
    }

    public void setColor(KeyPegColor color) {
        this.color = color;
    }

    public KeyPegColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyPeg keyPeg = (KeyPeg) o;
        return color == keyPeg.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
