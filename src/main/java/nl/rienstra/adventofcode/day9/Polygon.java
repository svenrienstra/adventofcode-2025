package nl.rienstra.adventofcode.day9;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Polygon {
    private final List<Line> lines = new ArrayList<>();
    private final int maxY;
    private final int maxX;
    private Cache<Integer, Set<Point>> coveredAreaCache = Caffeine.newBuilder()
            .maximumSize(2000)
            .build();
    private Cache<Point, Boolean> notinsideCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .build();


    public Polygon(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String[] startCoords = lines.get(i).split(",");
            String[] endCoords = null;
            if (i + 1 >= lines.size()) {
                endCoords = lines.get(0).split(",");
            } else {
                endCoords = lines.get((i + 1)).split(",");
            }

            this.lines.add(new Line(new Point(Integer.parseInt(startCoords[0]), Integer.parseInt(startCoords[1])), new Point(Integer.parseInt(endCoords[0]), Integer.parseInt(endCoords[1]))));
        }
        maxY = this.lines.stream().mapToInt(line -> Math.max(line.start().y(), line.end().y())).max().getAsInt() + 1;
        maxX = this.lines.stream().mapToInt(line -> Math.max(line.start().x(), line.end().x())).max().getAsInt() + 1;


    }

    Set<Point> findCoveredPointsForRow(int row) {
        Set<Point> points = new HashSet<>();

        List<Line> verticalLines = new ArrayList<>();
        for (Line line : lines) {
            if (line.horizontal() && line.start().y() == row) {
                points.addAll(line.toPoints());
            } else if (Math.min(line.start().y(), line.end().y()) <= row && Math.max(line.start().y(), line.end().y()) >= row) {
                verticalLines.add(line);
            }
        }

        verticalLines.sort(Comparator.comparingInt(line -> line.start().x()));

        if (verticalLines.size() % 2 != 0) {
            List<Line> toRemove = new ArrayList<>();
            for (int i = 0; i < verticalLines.size(); i++) {
                if (i + 1 < verticalLines.size()) {
                    Line line = verticalLines.get(i);
                    Line nextLine = verticalLines.get(i + 1);
                    if (lines.contains(new Line(new Point(line.start().x(), row), new Point(nextLine.start().x(), row)))
                            || lines.contains(new Line(new Point(nextLine.start().x(), row), new Point(line.start().x(), row)))) {
                        toRemove.add(line);
                    }
                }
            }

            verticalLines.removeAll(toRemove);
        }

        assert verticalLines.size() % 2 == 0;

        if (verticalLines.size() == 1) {
            return points;
        }

        for (int i = 0; i < verticalLines.size(); i += 2) {
            Line leftLine = verticalLines.get(i);
            Line rightLine = verticalLines.get(i + 1);
            for (int x = leftLine.start().x(); x <= rightLine.start().x(); x++) {
                points.add(new Point(x, row));
            }
        }


        return points;
    }

    boolean inNotInsideCache(Point point) {
        return notinsideCache.getIfPresent(point) != null;
    }

    boolean inside(Point point) {
        boolean result = lines.stream().anyMatch(line -> line.contains(point));
        if (!result) {
            result = coveredAreaCache.get(point.y(), row -> findCoveredPointsForRow(row)).contains(point);
        }
        if (!result) {
            notinsideCache.put(point, true);
        }
        return result;
    }

    void print() {
        for (int row = 0; row < maxY; row++) {
            for (int col = 0; col < maxX; col++) {
                int column = col;
                int finalRow = row;
                if (lines.stream().anyMatch(line -> line.contains(new Point(column, finalRow)))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    void printToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/svenrienstra/Documents/workspaces/personal/advent-of-code-2025/src/main/resources/day9/output"));

        for (int row = 0; row < maxY; row++) {
            StringBuilder sb = new StringBuilder();
            boolean containsRow = false;
            for (int col = 0; col < maxX; col++) {
                int column = col;
                int finalRow = row;
                if (lines.stream().anyMatch(line -> line.contains(new Point(column, finalRow)))) {
                    sb.append("#");
                    containsRow = true;
                } else {
                    sb.append(".");
                }
            }
            if (containsRow) {
                writer.write(row + ": " + sb.toString());
                writer.newLine();
            }
        }
        writer.close();

    }
}
