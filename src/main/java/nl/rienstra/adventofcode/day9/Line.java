package nl.rienstra.adventofcode.day9;

import java.util.ArrayList;
import java.util.List;

public record Line(Point start, Point end) {
    boolean crosses(Line other) {
        if (horizontal() == other.horizontal()) {
            return false; // Parallel lines do not cross
        }
        
        if (horizontal()) {
            return other.start.x() > this.start.x() && other.start.x() < this.end.x() &&
                    other.start.y() < this.start.y() && other.end.y() > this.start.y();
        } else {
            return other.start.y()  > this.start.y() && other.start.y() < this.end.y() &&
                    other.start.x() < this.start.x() && other.end.x() > this.start.x();
        }
    }
    
    boolean contains(Point point) {
        if (horizontal()) {
            return point.y() == start.y() &&
                    point.x() >= Math.min(start.x(), end.x()) &&
                    point.x() <= Math.max(start.x(), end.x());
        } else {
            return point.x() == start.x() &&
                    point.y() >= Math.min(start.y(), end.y()) &&
                    point.y() <= Math.max(start.y(), end.y());
        }
    }

    boolean horizontal() {
        return start.y() == end.y();
    }
    
    List<Point> toPoints() {
        List<Point> points = new ArrayList<>();
        if (horizontal()) {
            int y = start.y();
            for (int x = Math.min(start.x(), end.x()); x <= Math.max(start.x(), end.x()); x++) {
                points.add(new Point(x, y));
            }
        } else {
            int x = start.x();
            for (int y = Math.min(start.y(), end.y()); y <= Math.max(start.y(), end.y()); y++) {
                points.add(new Point(x, y));
            }
        }
        
        return points;
    }
    
    public String toString(){
        return String.format("(%s) -> (%s)", start.toString(), end.toString());
    }
}
