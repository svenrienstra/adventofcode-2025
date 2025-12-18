package nl.rienstra.adventofcode.day9;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day9 {

    public static void main(String[] args) throws IOException {
        String input = new String(Day1.class.getResourceAsStream("/day9/input").readAllBytes());
        String[] lines = input.split("\\n");

        System.out.println("Biggest rectangle area: " + findBiggestRectangle(lines));
    }

    private static long findBiggestRectangle(String[] lines) throws IOException {
        Polygon polygon = new Polygon(Arrays.asList(lines));
        long maxArea = 0;
        long count = 0;

        List<Pair<Point>> points = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                int[] point1 = Arrays.stream(lines[i].split(",")).mapToInt(Integer::parseInt).toArray();
                int[] point2 = Arrays.stream(lines[j].split(",")).mapToInt(Integer::parseInt).toArray();

                points.add(new Pair<>(new Point(point1[0], point1[1]), new Point(point2[0], point2[1])));
            }
        }
        
        System.out.println("Number of pairs: " + points.size());

        points.sort(Comparator.<Pair<Point>>comparingLong(pair -> {
            int top = Math.min(pair.first().y(), pair.second().y());
            int bottom = Math.max(pair.first().y(), pair.second().y());
            int left = Math.min(pair.first().x(), pair.second().x());
            int right = Math.max(pair.first().x(), pair.second().x());

            return (bottom - top + 1L) * (right - left + 1L);
        }).reversed());

        System.out.println("Done sorting pairs.");

        for (Pair<Point> pair : points) {
            count++;
            
            if (count % 100 == 0 ) {
                System.out.println("Checked " + count + " pairs...");
            }
            int top = Math.min(pair.first().y(), pair.second().y());
            int bottom = Math.max(pair.first().y(), pair.second().y());
            int left = Math.min(pair.first().x(), pair.second().x());
            int right = Math.max(pair.first().x(), pair.second().x());

            boolean allInside = polygon.inside(new Point(left, top)) &&
                    polygon.inside(new Point(left, bottom)) &&
                    polygon.inside(new Point(right, top)) &&
                    polygon.inside(new Point(right, bottom));
            if (allInside) {
                for (int row = top; row <= bottom; row++) {
                    if (polygon.inNotInsideCache(new Point(left, row)) || polygon.inNotInsideCache(new Point(right, row))) {
                        allInside = false;
                        break;
                    }
                }
            }

            if (allInside) {
                for (int col = left; col <= right; col++) {
                    if (polygon.inNotInsideCache(new Point(col, top)) || polygon.inNotInsideCache(new Point(col, bottom))) {
                        allInside = false;
                        break;
                    }
                }
            }

            if (allInside) {
                //check the outlines of the rectangle
                for (int row = top; row <= bottom; row++) {
                    if (!polygon.inside(new Point(left, row)) || !polygon.inside(new Point(right, row))) {
                        allInside = false;
                        break;
                    }
                }
            }

            if (allInside) {
                for (int col = left; col <= right; col++) {
                    if (!polygon.inside(new Point(col, top)) || !polygon.inside(new Point(col, bottom))) {
                        allInside = false;
                        break;
                    }
                }
            }

            if (allInside) {
                System.out.println("Biggest rectangle: top=" + top + ", left=" + left + ", bottom=" + bottom + ", right=" + right + " => area=" + ((bottom - top + 1L) * (right - left + 1L)));
                maxArea = (bottom - top + 1L) * (right - left + 1L);
                break;
            }
        }
        
        return maxArea;
    }
    
}
