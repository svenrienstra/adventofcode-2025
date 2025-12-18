package nl.rienstra.adventofcode.day4;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.List;

public class Day4 {

    public  static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day4/input").readAllBytes()).strip();

//        PaperGrid paperGrid = new PaperGrid(List.of(input.split("\\n")));
//        paperGrid.printGrid();
//        System.out.println("");
//        System.out.println("+++++++++++++++++++++++++++++++");
//        System.out.println("");
//        paperGrid.printReachableRolls();
//        System.out.println(paperGrid.coundReachableRolls());


        PaperGrid paperGrid = new PaperGrid(List.of(input.split("\\n")));
        int initialRolls = paperGrid.countRolls();
        int rolls = initialRolls;
        do {
            PaperGrid newGrid = paperGrid.removeReachableRolls();
            int newGridsRolls = newGrid.countRolls();
            if  (newGridsRolls == rolls) {
                break;
            }
            rolls = newGridsRolls;
            paperGrid = newGrid;
        } while(true);

        System.out.println(initialRolls - paperGrid.countRolls());

    }
}
