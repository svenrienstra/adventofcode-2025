package nl.rienstra.adventofcode.day6;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {

    public static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day6/input").readAllBytes());

        List<String> lines = Arrays.stream(input.split("\\n")).toList();

        List<Integer> columnWidths = new ArrayList<>();
        int counter = 0;
        for (Character c : lines.get(lines.size() - 1).toCharArray()) {
            if (!c.equals(' ')) {
                columnWidths.add(counter - 1);
                counter = 0;
            }
            counter++;
        }
        columnWidths.add(counter);
        columnWidths.remove(0);

        System.out.println(columnWidths);

        List<String[]> parsedLines = new ArrayList<>();
        for (String line : lines) {
            List<String> parts = new ArrayList<>();
            for (Integer width : columnWidths) {
                String part = line.substring(0, width);
                if (part.length() < width) {
                    part = part + " ".repeat(width - part.length());
                }
                parts.add(part);
                if (width + 1 <= line.length()) {
                    line = line.substring(width + 1);
                }
            }
            parsedLines.add(parts.toArray(new String[0]));
        }


        int numberOfProblems = parsedLines.get(0).length;

        List<CephalopodProblem> problems = new ArrayList<>();
        for (int problem = 0; problem < numberOfProblems; problem++) {
            List<Long> numbers = new ArrayList<>();
            CephalopodProblem.Operation operation = parsedLines.get(lines.size() - 1)[problem].strip().equals("+") ? CephalopodProblem.Operation.ADD : CephalopodProblem.Operation.MULTIPLY;

            int problemIndex = problem;

            for (int i = columnWidths.get(problem) - 1; i >= 0 ; i--) {
                String number = "";
                for (int lineIndex = 0; lineIndex < parsedLines.size() - 1; lineIndex++) {
                    number += parsedLines.get(lineIndex)[problemIndex].toCharArray()[i];
                }
                numbers.add(Long.parseLong(number.strip()));
            }

            problems.add(new CephalopodProblem(numbers, operation));
        }

        System.out.println(problems.stream().mapToLong(CephalopodProblem::solve).sum());
    }
}
