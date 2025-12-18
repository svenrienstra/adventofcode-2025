package nl.rienstra.adventofcode.day9;

public record Point(int x, int y) {
    public String toString(){
        return String.format("%d,%d", x, y);
    }
}
