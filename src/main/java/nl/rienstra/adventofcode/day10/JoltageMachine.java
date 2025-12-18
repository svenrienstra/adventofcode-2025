package nl.rienstra.adventofcode.day10;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class JoltageMachine {
    private final int[] targetJoltages;
    private final Set<Boolean[]> buttons;

    public JoltageMachine(int[] targetJoltages, Set<Boolean[]> buttons) {
        this.targetJoltages = targetJoltages;
        this.buttons = buttons;
    }

    public int findMinimumPresses(int[] joltageState) {
        printState(joltageState);
        Map<Integer, List<Boolean[]>> buttonMatchCounts = buttons.stream()
                .filter(button -> matchCount(joltageState, button) > 0)
                .collect(Collectors.groupingBy(button -> matchCount(joltageState, button)));
        
        if (buttonMatchCounts.size() == 0) {
            return -1;
        }
        
        List<Integer> sortedMatchCounts = buttonMatchCounts.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        
        //System.out.println("Number of entries with best match count: " + buttonMatchCounts.get(sortedMatchCounts.get(0)).size());
        
        int branches = 0;
        for (int matchCount : sortedMatchCounts) {
            if (branches > 0) {
                //System.out.println("Exploring branch " + branches + " with match count: " + matchCount);
            }
            
            int lowestPresses = Integer.MAX_VALUE;
            for (Boolean[] button : buttonMatchCounts.get(matchCount)) {
                int[] newJoltages = applyButton(joltageState, button);
                if (solved(newJoltages)) {
                    return 1;
                } else {
                    int presses = findMinimumPresses(newJoltages);
                    if (presses >= 0 && presses < lowestPresses) {
                        lowestPresses = presses;
                    }
                }
            }

            if (lowestPresses != Integer.MAX_VALUE) {
                return lowestPresses + 1;
            }
            branches++;
        }
        
        return -1;
    }
    

    public int findMinimumPresses() {
        return findMinimumPresses(targetJoltages);
    }


    private int matchCount(int[] targetJoltages, Boolean[] button) {
        int count = 0;
        for (int i = 0; i < targetJoltages.length; i++) {
            if (targetJoltages[i] == 0 && button[i] != null && button[i]) {
                return -1;
            }
            
            if (targetJoltages[i] > 0 && button[i] != null && button[i]) {
                count++;
            }
        }

        return count;
    }

    private int[] applyButton(int[] targetJoltages, Boolean[] button) {
        int[] newJoltages = Arrays.copyOf(targetJoltages, targetJoltages.length);
        for (int i = 0; i < targetJoltages.length; i++) {
            if (button[i] != null && button[i]) {
                if (newJoltages[i] == 0) {
                    throw new IllegalStateException("Cannot apply button, joltage already at 0");
                }
                newJoltages[i]--;
            }
        }

        return newJoltages;
    }

    private boolean solved(int[] targetJoltages) {
        return Arrays.stream(targetJoltages).allMatch(joltage -> joltage == 0);
    }
    
    private void printState(int[] joltageState) {
        System.out.println(Arrays.toString(joltageState));
    }
}
