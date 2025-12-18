package nl.rienstra.adventofcode.day3;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class BatterySelector {

    public static int selectBatteries(String input) {
        //Find highest value excluding last char
        Integer first = input.substring(0, input.length() - 1).chars().map(c -> c - '0').max().orElse(0);
        Integer second = input.substring(input.indexOf(first.toString()) + 1, input.length()).chars().map(c -> c - '0').max().orElse(0);

        return first * 10 + second;
    }

    public static long selectTwelveBatteries(String input) {
        List<Integer> selectedBatteries = new ArrayList<>();
        String remainingInput = input;
        for (int i = 11; i >= 0; i--) {
            String partialInput = remainingInput.substring(0, remainingInput.length() - i);
            Integer output = partialInput.chars().map(c -> c - '0').max().orElse(0);
            remainingInput = remainingInput.substring(remainingInput.indexOf(output.toString()) + 1, remainingInput.length());
            selectedBatteries.add(output);
        }

        String output = selectedBatteries.stream().map(Object::toString).collect(joining());
        return Long.parseLong(output);
    }

    public static Long getRemainingBatteries(String input, int count) {
        String partialInput = input.substring(0, input.length() - (count - 1));
        Integer output = partialInput.chars().map(c -> c - '0').max().orElse(0);
        if (count > 1) {
            return Long.parseLong(output + getRemainingBatteries(input.substring(input.indexOf(output.toString()) + 1), count - 1).toString());
        }

        return output.longValue();
    }
}
