package sk.tuke.gamestudio.game.core;

import java.util.Comparator;

public class KeyPegColorPriorityComparator implements Comparator<KeyPegColor> {
    @Override
    public int compare(KeyPegColor o1, KeyPegColor o2) {
        return o1.getPriority() - o2.getPriority();
    }
}
