package nl.rienstra.adventofcode.day9;

import java.util.Arrays;

import static nl.rienstra.adventofcode.day9.Tile.GREEN;
import static nl.rienstra.adventofcode.day9.Tile.RED;

public class TileGrid {
    private Tile[][] grid;
    
    public TileGrid(Tile[][] grid) {
        this.grid = grid;
    }
    
    public long findBiggestRectangle() {
        int rows = grid.length;
        int cols = grid[0].length;
        long maxArea = 0;

        for (int top = 0; top < rows; top++) {
            for (int left = 0; left < cols; left++) {
                if (grid[top][left] != RED) continue;
                for (int bottom = top; bottom < rows; bottom++) {
                    for (int right = left; right < cols; right++) {
                        if (grid[bottom][right] == RED) {
                            //check if the whole area is RED or GREEN
                            boolean validRectangle = true;
                            for (int r = top; r <= bottom; r++) {
                                for (int c = left; c <= right; c++) {
                                    if (grid[r][c] == null) {
                                        validRectangle = false;
                                        break;
                                    }
                                }
                                if (!validRectangle) break;
                            }
                            if (!validRectangle) continue;
                            
                            int area = (bottom - top + 1) * (right - left + 1);
                            if (area > maxArea) {
                                maxArea = area;
                            }
                        }
                    }
                }
            }
        }

        return maxArea;
    }
    
    void fillInGreen() {
        int rows = grid.length;

        for (int r = 0; r < rows; r++) {
            int startColumn = -1;
            int endColumn = -1;
            
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == RED || grid[r][c] == GREEN) {
                    if (startColumn == -1) {
                        startColumn = c;
                    } else {
                        endColumn = c;
                    }
                }
            }
            
            for (int c = startColumn + 1; c < endColumn; c++) {
                if (grid[r][c] == null) {
                    grid[r][c] = GREEN;
                }
            }
        }
    }
    
    void print() {
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile == null) {
                    System.out.print(".");
                } else if (tile == Tile.RED) {
                    System.out.print("#");
                } else if (tile == Tile.GREEN) {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }
}
