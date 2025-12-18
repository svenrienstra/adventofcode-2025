package nl.rienstra.adventofcode.day7;

import java.util.ArrayList;
import java.util.List;

public class QuantumTachyonManifold {
    private State[][] grid;
    private int iteration = 1;
    private long numberOfSplits;

    public QuantumTachyonManifold(State[][] inputGrid) {
        this.grid = inputGrid;
    }

    public QuantumTachyonManifold(State[][] inputGrid, int iteration) {
        this.grid = inputGrid;
        this.iteration = iteration;
    }

    public long run() {
        return getNumberOfPaths(1);
    }

    private long getNumberOfPaths(int currentIteration) {
        if (currentIteration >= grid.length) {
            return 1;
        }

        List<QuantumTachyonManifold> possibleStates = getPossibleStates(currentIteration);
        long totalPaths = 0;
        for (QuantumTachyonManifold state : possibleStates) {
            totalPaths += state.getNumberOfPaths(currentIteration + 1);
        }

        if (!possibleStates.isEmpty()) {
            return totalPaths;
        }

        runIteration(currentIteration);
        return getNumberOfPaths(currentIteration + 1);
    }


    private void runIteration(int currentIteration) {
        for (int column = 0; column < grid[0].length; column++) {
            if (grid[currentIteration - 1][column] == State.BEAM) {
                if (grid[currentIteration][column] == State.SPLITTER) {
                    numberOfSplits++;
                    if (column - 1 >= 0) {
                        grid[currentIteration][column - 1] = State.BEAM;
                    }
                    if (column + 1 < grid[currentIteration].length) {
                        grid[currentIteration][column + 1] = State.BEAM;
                    }
                } else {
                    grid[currentIteration][column] = State.BEAM;
                }
            }
        }
    }

    private List<QuantumTachyonManifold> getPossibleStates(int currentIteration) {
        List<QuantumTachyonManifold> possibleStates = new ArrayList<>();

        for (int column = 0; column < grid[0].length; column++) {
            if (grid[currentIteration - 1][column] == State.BEAM) {
                if (grid[currentIteration][column] == State.SPLITTER) {
                    if (column - 1 >= 0) {
                        State[][] clonedGrid = deepCopy(grid);
                        clonedGrid[currentIteration][column - 1] = State.BEAM;
                        possibleStates.add(new QuantumTachyonManifold(clonedGrid, currentIteration + 1));
                    }
                    if (column + 1 < grid[currentIteration].length) {
                        State[][] clonedGrid = deepCopy(grid);
                        clonedGrid[currentIteration][column + 1] = State.BEAM;
                        possibleStates.add(new QuantumTachyonManifold(clonedGrid, currentIteration + 1));
                    }
                }
            }
        }

        return possibleStates;
    }

    public long getNumberOfSplits() {
        return numberOfSplits;
    }

    private void print() {
        for (State[] row : grid) {
            for (State state : row) {
                switch (state) {
                    case BEAM -> System.out.print("|");
                    case SPLITTER -> System.out.print("^");
                    case EMPTY -> System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("===================================================");
        System.out.println();
    }

    <T> T[][] deepCopy(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

}
