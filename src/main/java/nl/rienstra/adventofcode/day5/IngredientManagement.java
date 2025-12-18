package nl.rienstra.adventofcode.day5;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IngredientManagement {
    private Set<Range> freshIngredients = new HashSet<>();

    public IngredientManagement(List<String> freshIngredientRanges) {
        freshIngredientRanges.forEach(range -> {
            String[] ranges = range.split("-");
            freshIngredients.add(new Range(Long.parseLong(ranges[0]), Long.parseLong(ranges[1])));
        });
    }


    boolean isFreshIngredient(Long ingredientCode) {
        return freshIngredients.parallelStream().anyMatch(range -> range.contains(ingredientCode));
    }

    Long countFreshIngredientsIds() {
        Set<Range> mergedRanges = new HashSet<>(freshIngredients);
        while(true) {
            boolean found = false;
            for(Range range : mergedRanges) {
                for (Range otherRange : mergedRanges) {
                    if(range != otherRange && range.overlaps(otherRange)) {
                        mergedRanges.remove(range);
                        mergedRanges.remove(otherRange);
                        mergedRanges.add(range.merge(otherRange));
                        found = true;
                        break;
                    }
                }
                if(found) {
                    break;
                }
            }
            if (!found) {
                break;
            }
        }

        return mergedRanges.stream().mapToLong(Range::countFreshIngredientsIds).sum();
    }

    record Range(Long lower, Long upper) {
        boolean contains(Long value) {
            return value >= lower && value <= upper;
        }

        boolean overlaps(Range other) {
            return this.lower <= other.upper && other.lower <= this.upper;
        }

        Range merge(Range other) {
            return new Range(Math.min(this.lower, other.lower), Math.max(this.upper, other.upper));
        }

        Long countFreshIngredientsIds() {
            return upper - lower + 1;
        }
    }
}
