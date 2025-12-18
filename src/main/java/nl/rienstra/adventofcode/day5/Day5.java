package nl.rienstra.adventofcode.day5;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.List;

public class Day5 {

    public static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day5/input").readAllBytes()).strip();

        List<String> lines = List.of(input.split("\\n"));
        List<String> ranges = lines.subList(0, lines.indexOf(""));
        List<String> ingredients = lines.subList(lines.indexOf("") + 1, lines.size());

        IngredientManagement ingredientManagement = new IngredientManagement(ranges);

        Long result = ingredients.stream()
                .map(Long::parseLong)
                .filter(ingredientManagement::isFreshIngredient)
                .count();

        System.out.println(result);
        System.out.println(ingredientManagement.countFreshIngredientsIds());
    }
}
