package nl.rienstra.adventofcode.day8;

public record Point(int x, int y, int z) {
    public double distanceTo(Point p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2) + Math.pow(z - p.z, 2));
    }
    
    public String toString(){
        return String.format("%d,%d,%d", x, y, z);
    }
}
