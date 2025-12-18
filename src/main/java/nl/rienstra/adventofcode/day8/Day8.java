package nl.rienstra.adventofcode.day8;

import com.google.common.collect.Sets;
import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String input = new String(Day1.class.getResourceAsStream("/day8/input").readAllBytes());
        String[] lines = input.split("\\n");

        List<Point> points = Arrays.stream(lines)
                .map(line -> {
                    String[] coordinates = line.split(",");
                    return new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2]));
                })
                .toList();
        
        List<Pair<Point>> pairs = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                pairs.add(new Pair(points.get(i), points.get(j)));
            }
        }

        pairs.sort(Comparator.comparing(pair -> pair.first().distanceTo(pair.second())));

//        pairs.stream()
//                .forEach(pair -> System.out.println("Point 1: " + pair.first + " Point 2: " + pair.second));

        List<Set<Point>> circuits = new ArrayList<>();
        Pair<Point> finalPair = null;
        for (Pair<Point> pair : pairs) {
            List<Set<Point>> existingCiruits = circuits.stream()
                    .filter(set -> set.contains(pair.first()) || set.contains(pair.second()))
                    .toList();
            
            Set<Point> circuit;
            if (!existingCiruits.isEmpty()) {
                if (existingCiruits.size() > 1) {
                    
                    circuit = existingCiruits.get(0);
                    circuit.addAll(existingCiruits.get(1));
                    circuits.remove(existingCiruits.get(1));
                } else {
                    circuit = existingCiruits.get(0);
                    if (circuit.contains(pair.first()) && circuit.contains(pair.second())) {
                        continue;
                    }
                }
            } else {
                circuit = new HashSet<>();
                circuits.add(circuit);
            }
            circuit.add(pair.first());
            circuit.add(pair.second());
            
            if (circuits.size() == 1 && circuit.size() == points.size()) {
                finalPair = pair;
                break;
            }
        }
        
        System.out.println("Circuits: " + circuits.size());
        System.out.println("result: " + (Long.valueOf(finalPair.first.x()) * (Long.valueOf(finalPair.second.x()))));
    }
    
    record Pair<T>(T first, T second) {
    }
}
