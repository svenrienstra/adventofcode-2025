package nl.rienstra.adventofcode.day1;

import java.util.ArrayList;
import java.util.List;

public class PasswordDial {

    public List<Integer> turnDials(List<String> inputList) {
        int position = 50;
        List<Integer> output = new ArrayList<>();

        for (String input : inputList) {
            Direction direction = input.charAt(0) == 'L' ? Direction.LEFT : Direction.RIGHT;

            if (direction == Direction.LEFT) {
                position -= Integer.parseInt(input.substring(1));
                while (position < 0) {
                    position += 99;
                }
            } else {
                position += Integer.parseInt(input.substring(1));
                while (position >= 100) {
                    position -= 99;
                }
            }

            output.add(position);
        }

        return output;
    }


    private enum Direction {
        LEFT,
        RIGHT
    }
}
