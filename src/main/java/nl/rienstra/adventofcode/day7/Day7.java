package nl.rienstra.adventofcode.day7;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Day7 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String input = new String(Day1.class.getResourceAsStream("/day7/input").readAllBytes());
        String[] lines = input.split("\\n");

        State[][] grid = new State[lines.length][lines[0].length()];
        for (int row = 0; row < lines.length; row++) {
            for (int column = 0; column < lines[row].length(); column++) {
                char c = lines[row].toCharArray()[column];
                switch (c) {
                    case '.' -> grid[row][column] = State.EMPTY;
                    case '^' -> grid[row][column] = State.SPLITTER;
                    case 'S' -> grid[row][column] = State.BEAM;
                }
            }
        }

        TachyonManifold tachyonManifold = new TachyonManifold(grid);
        tachyonManifold.run();

        System.out.println(tachyonManifold.countTotalPathsUp());
        
    }
}
