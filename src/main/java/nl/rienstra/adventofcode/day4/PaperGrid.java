package nl.rienstra.adventofcode.day4;

import java.util.List;

public class PaperGrid {
    boolean[][] grid;

    public PaperGrid(List<String> inputLines) {
        int width = inputLines.get(0).length();
        int height = inputLines.size();
        grid = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            String line = inputLines.get(y);
            for (int x = 0; x < width; x++) {
                grid[y][x] = line.charAt(x) == '@';
            }
        }
    }

    private PaperGrid(boolean[][] grid) {
        this.grid = grid;
    }

    void printGrid() {
        for (int y = 0; y < grid.length; y++) {
            String row = "";
            for (int x = 0; x < grid[0].length; x++) {
                row += grid[y][x] ? "@" : ".";
            }
            System.out.println(row);
        }
    }

    int countRolls() {
        int count = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x]) {
                    count++;
                }
            }
        }

        return count;
    }

    int countSurroundingRolls(int x, int y) {
        int count = 0;

        //We need to check all 8 surrounding cells
        int minY = Math.max(0, y - 1);
        int maxY = Math.min(grid.length - 1, y + 1);

        int minX = Math.max(0, x - 1);
        int maxX = Math.min(grid[0].length - 1, x + 1);

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                if (i == y && j == x) {
                    continue; //Skip self
                }
                if (grid[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    void printReachableRolls() {
        for (int y = 0; y < grid.length; y++) {
            String row = "";
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] && countSurroundingRolls(x, y) < 4) {
                    row += "x";
                } else if (grid[y][x]) {
                    row += "@";
                } else {
                    row += ".";
                }
            }
            System.out.println(row);
        }
    }

    int countReachableRolls() {
        int count = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] && countSurroundingRolls(x, y) < 4) {
                    count++;
                }
            }
        }

        return count;
    }

    PaperGrid removeReachableRolls() {
        boolean[][] newGrid = new boolean[this.grid.length][this.grid[0].length];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] && countSurroundingRolls(x, y) < 4) {
                    newGrid[y][x] = false;
                } else if (grid[y][x]) {
                    newGrid[y][x] = true;
                } else  {
                    newGrid[y][x] = false;
                }
            }
        }

        return new PaperGrid(newGrid);
    }
}
