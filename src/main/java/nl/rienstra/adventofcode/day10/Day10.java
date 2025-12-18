package nl.rienstra.adventofcode.day10;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {

    public static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day10/input-example").readAllBytes());
        String[] lines = input.split("\\n");

        List<JoltageMachine> machines = new ArrayList<>();
        for (String line : lines) {
            String[] splittedLine = line.split(" ");
            String targetStateString = splittedLine[splittedLine.length - 1];
            List<String> buttonStrings = new ArrayList<>();
            for (String buttonString : splittedLine) {
                if (buttonString.startsWith("(")) {
                    buttonStrings.add(buttonString);
                }
            }

            machines.add(createMachine(targetStateString, buttonStrings));
        }

        int total = 0;
        for (JoltageMachine machine : machines) {
            int presses = machine.findMinimumPresses();
            total += presses;
            System.out.println("Minimum presses: " + presses);
        }

        System.out.println("Total: " + total);

    }

    private static JoltageMachine createMachine(String targetStateString, List<String> buttonStrings) {
        String[] splittedTarget = targetStateString.substring(1, targetStateString.length() - 1).split(",");
        int[] targetState = new int[splittedTarget.length];
        for (int i = 0; i < splittedTarget.length; i++) {
            targetState[i] = Integer.parseInt(splittedTarget[i]);
        }

        Set<Boolean[]> buttons = new HashSet<>();
        for (String buttonString : buttonStrings) {
            Boolean[] button = new Boolean[targetState.length];
            String buttonContent = buttonString.substring(1, buttonString.length() - 1);
            String[] buttonParts = buttonContent.split(",");
            for (int i = 0; i < buttonParts.length; i++) {
                int index = Integer.parseInt(buttonParts[i]);
                button[index] = true;
            }
            buttons.add(button);
        }

        return new JoltageMachine(targetState, buttons);
    }
}
