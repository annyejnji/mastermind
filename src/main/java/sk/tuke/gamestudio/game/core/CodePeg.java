package sk.tuke.gamestudio.game.core;

import java.util.Objects;

public class CodePeg {
    CodePegColor color;

    public CodePeg(CodePegColor color) {
        this.color = color;
    }

    public void setColor(CodePegColor color) {
        this.color = color;
    }

    public CodePegColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodePeg codePeg = (CodePeg) o;
        return color == codePeg.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}

