package nl.rienstra.adventofcode.day10;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Machine {
    private boolean[] targetState;
    private Set<Boolean[]> buttons;

    public Machine(boolean[] targetState, Set<Boolean[]> buttons) {
        this.targetState = targetState;
        this.buttons = buttons;
    }

    public int findMinimumPresses() {
        for (int i = 1; i <= buttons.size(); i++) {
            for (Set<Boolean[]> buttonsToTry : Sets.combinations(buttons, i)) {
                if (tryCombination(List.copyOf(buttonsToTry))) {
                    return buttonsToTry.size();
                }
            }
        }

        return -1;
    }

    private boolean tryCombination(List<Boolean[]> buttons) {
        boolean[] initialState = new boolean[targetState.length];
        for (Boolean[] button : buttons) {
            for (int i = 0; i < button.length; i++) {
                if (button[i] != null && button[i]) {
                    initialState[i] = !initialState[i];
                }
            }
        }

        return Arrays.equals(initialState, targetState);
    }

}
