package nl.rienstra.adventofcode.day3;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;

public class Day3 {
    public static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day3/input").readAllBytes());

        long result = 0;
        for (String inputRow : input.split("\\n")) {
            long output = BatterySelector.getRemainingBatteries(inputRow, 12);
            System.out.println("Input: " + inputRow + " output: " + output);
            result += output;
        }

        System.out.println("Result: " + result);
    }
}
