package nl.rienstra.adventofcode.day1;

import java.util.ArrayList;
import java.util.List;

public class PasswordDial2 {

    public int turnDials(List<String> inputList) {
        int position = 50;
        int zeroCount = 0;

        for (String input : inputList) {
            Direction direction = input.charAt(0) == 'L' ? Direction.LEFT : Direction.RIGHT;
            int clicks = Integer.parseInt(input.substring(1));

            for (int i = 0; i < clicks; i++) {
                if (direction == Direction.LEFT) {
                    position--;
                } else {
                    position++;
                }

                if (position == 0) {
                    zeroCount++;
                }

                if (position < 0) {
                    position = 99;
                }

                if (position > 99) {
                    position = 0;
                    zeroCount++;
                }
            }

            System.out.println("Input: " + input + " position: " + position + " zeroCount: " + zeroCount);
        }

        return zeroCount;
    }

    private enum Direction {
        LEFT,
        RIGHT
    }
}
