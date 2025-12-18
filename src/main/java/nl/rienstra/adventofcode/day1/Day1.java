package nl.rienstra.adventofcode.day1;

import java.io.IOException;
import java.util.List;

public class Day1 {

    public static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day1/input").readAllBytes());


        PasswordDial2 passwordDial = new PasswordDial2();
        int zeros = passwordDial.turnDials(List.of(input.split("\\n")));
        System.out.println(zeros);
    }
}
