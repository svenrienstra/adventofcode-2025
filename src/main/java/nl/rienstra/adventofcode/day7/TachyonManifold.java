package nl.rienstra.adventofcode.day7;

public class TachyonManifold {
    private State[][] grid;
    private Long[][] totalPathsCache;
    private int iteration = 0;
    private long numberOfSplits;

    public TachyonManifold(State[][] inputGrid) {
        this.grid = inputGrid;
        this.totalPathsCache = new Long[grid.length][grid[0].length];
    }

    public void run() {
        print();
        for (iteration = 1; iteration < grid.length; iteration++) {
            runIteration();
            print();
        }
    }

    private void runIteration() {
        for (int column = 0; column < grid[0].length; column++) {
            if (grid[iteration - 1][column] == State.BEAM) {
                if (grid[iteration][column] == State.SPLITTER) {
                    numberOfSplits++;
                    if (column - 1 >= 0) {
                        grid[iteration][column - 1] = State.BEAM;
                    }
                    if (column + 1 < grid[iteration].length) {
                        grid[iteration][column + 1] = State.BEAM;
                    }
                } else {
                    grid[iteration][column] = State.BEAM;
                }
            }
        }
    }

    public long countTotalPathsUp(){
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                totalPathsCache[row][column] = findPathsUp(row, column);
            }
        }

        long totalPaths = 0;
        for (int column = 0; column < grid[0].length; column++) {
            totalPaths += totalPathsCache[grid.length - 1][column];
        }

        return totalPaths;
    }

    public long findPathsUp(int row, int column) {
        if (grid[row][column] == State.EMPTY) {
            return 0l;
        }

        if (row == 0 && grid[row][column] != State.EMPTY) {
            return 1l;
        }

        long sum = 0;
        //left
        if (column - 1 >= 0 && grid[row - 1][column - 1] == State.SPLITTER) {
            sum += totalPathsCache[row - 1][column - 1];
        }

        //middle
        if (column - 1 >= 0 && grid[row - 1][column] == State.BEAM) {
            sum += totalPathsCache[row - 1][column];
        }

        //right
        if (column + 1 < grid[0].length && grid[row - 1][column + 1] == State.SPLITTER) {
            sum += totalPathsCache[row - 1][column + 1];
        }

        return sum;
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
}
