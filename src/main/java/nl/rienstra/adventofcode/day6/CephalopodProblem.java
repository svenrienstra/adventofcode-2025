package nl.rienstra.adventofcode.day6;

import java.util.List;

public class CephalopodProblem {
    private List<Long> numbers;
    private CephalopodProblem.Operation operation;

    public CephalopodProblem(List<Long> numbers, CephalopodProblem.Operation operation) {
        this.numbers = numbers;
        this.operation = operation;
    }

    public Long solve() {
        return numbers.stream().reduce(0L, (acc, number) -> {
            if (operation == Operation.ADD) {
                return acc + number;
            } else if (operation == Operation.MULTIPLY) {
                if (acc == 0) {
                    acc = 1L;
                }
                return acc * number;
            } else {
                throw new IllegalArgumentException("Unknown operation: " + operation);
            }
        }, (a, b) -> a);
    }

    static enum Operation {
        ADD,
        MULTIPLY;
    }
}
