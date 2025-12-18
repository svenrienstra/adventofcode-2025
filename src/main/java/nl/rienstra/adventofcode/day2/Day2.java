package nl.rienstra.adventofcode.day2;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.function.Predicate.not;

public class Day2 {

    public static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day2/input").readAllBytes()).trim();


        List<String> ranges = List.of(input.split(","));
        List<Long> invalidProductIds = new ArrayList<>();

        for (String range : ranges) {
            String start = range.split("-")[0];
            String end = range.split("-")[1];

            invalidProductIds.addAll(LongStream.rangeClosed(Long.parseLong(start), Long.parseLong(end))
                    .mapToObj(Long::toString)
                    .filter(not(ProductIdValidator::isValidProductId))
                    .mapToLong(Long::parseLong)
                    .boxed().toList());
        }

        System.out.println("Invalid product ids: " + invalidProductIds);
        System.out.println("Output: " + invalidProductIds.stream().mapToLong(Long::longValue).sum());
    }
}
