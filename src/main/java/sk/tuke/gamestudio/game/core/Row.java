package sk.tuke.gamestudio.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Row {
    private final List<CodePeg> codePegs;
    private final List<KeyPeg> keyPegs;
    private final int colsCount;

    public Row(int colsCount) {
        this.codePegs = generateInitialCodePegs(colsCount);
        this.keyPegs = generateInitialKeyPegs(colsCount);
        this.colsCount = colsCount;
    }

    public boolean isSolved() {
        for (KeyPeg keyPeg : keyPegs) {
            if (keyPeg.getColor() != KeyPegColor.RED) {
                return false;
            }
        }
        return true;
    }

    public List<CodePeg> getCodePegs() {
        return codePegs;
    }

    public List<KeyPeg> getKeyPegs() {
        return keyPegs;
    }


    private List<CodePeg> generateInitialCodePegs(int colsCount) {
        List<CodePeg> codePegs = new ArrayList<>();

        for (int i = 0; i < colsCount; i++) {
            CodePeg codePeg = new CodePeg(null);
            codePegs.add(codePeg);
        }

        return codePegs;
    }

    private List<KeyPeg> generateInitialKeyPegs(int colsCount) {
        List<KeyPeg> keyPegs = new ArrayList<>();

        for (int i = 0; i < colsCount; i++) {
            KeyPeg keyPeg = new KeyPeg(null);
            keyPegs.add(keyPeg);
        }

        return keyPegs;
    }

    public void generateKeyPegsForSolution(List<CodePeg> solution) { // poslat list farieb do codepegov ktore su v aktualnom Row
        // count colors from solution
        Map<CodePeg, Long> colorCountInSolution = solution.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<CodePeg, List<KeyPegColor>> tempKeyPegColorsPerCodePeg = new HashMap<>();
        for (int i = 0; i < colsCount; i++) {
            CodePeg codePeg = codePegs.get(i);
            List<KeyPegColor> tempList = tempKeyPegColorsPerCodePeg.computeIfAbsent(codePeg, k -> new ArrayList<>());

            if (solution.get(i).equals(codePeg)) {
                tempList.add(KeyPegColor.RED);
            } else if (solution.contains(codePeg)) {
                tempList.add(KeyPegColor.WHITE);
            }
        }

        int keyPegIndex = 0;
        for (Map.Entry<CodePeg, List<KeyPegColor>> entry : tempKeyPegColorsPerCodePeg.entrySet()) {
            CodePeg codePeg = entry.getKey();
            List<KeyPegColor> keyPegColors = entry.getValue();
            keyPegColors.sort(new KeyPegColorPriorityComparator());

            Long limit = colorCountInSolution.get(codePeg);
            // ak solution neobsahuje farbu zadanu pouzivatelom, tak mozme preskocit
            if (limit == null) {
                continue;
            }
            for (KeyPegColor keyPegColor : keyPegColors) {
                if (limit-- == 0) {
                    break;
                }
                keyPegs.get(keyPegIndex).setColor(keyPegColor);
                keyPegIndex++;
            }
        }

        // vratit vysledok co sa sedi a co nesedi
    }
}




