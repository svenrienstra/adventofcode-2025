package nl.rienstra.adventofcode.day11;

import nl.rienstra.adventofcode.day1.Day1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String input = new String(Day1.class.getResourceAsStream("/day11/input").readAllBytes());
        String[] lines = input.split("\\n");

        List<Path> paths = new ArrayList<>();
        paths.add(new Path("out", List.of()));
        for (String line : lines) {
            String[] splitted = line.split(" ");
            String name = splitted[0].substring(0, splitted[0].length() - 1);
            
            if (!paths.stream().anyMatch(p -> p.getName().equals(name))) {
                paths.add(new Path(name, new ArrayList<>()));
            }
        }
        
        System.out.println("Paths created: " + paths.size());

        for (String line : lines) {
            String[] splitted = line.split(" ");
            String name = splitted[0].substring(0, splitted[0].length() - 1);

            Path thisPath = paths.stream().filter(p -> p.getName().equals(name)).findFirst().orElseThrow();

            for (int j = 1; j < splitted.length; j++) {
                String connection = splitted[j];
                thisPath.addConnection(paths.stream().filter(path -> path.getName().equals(connection)).findFirst().orElseThrow());
            }
        }

        Path startPath = paths.stream().filter(path -> path.getName().equals("svr")).findFirst().orElseThrow();
        Path fft = paths.stream().filter(path -> path.getName().equals("fft")).findFirst().orElseThrow();
        Path dac = paths.stream().filter(path -> path.getName().equals("dac")).findFirst().orElseThrow();


        System.out.println("Paths svr to fft: " + startPath.countPathsTo("fft"));
        System.out.println("Paths svr to dac: " + startPath.countPathsTo("dac"));
        System.out.println("Paths fft to dac: " + fft.countPathsTo("dac"));
        System.out.println("Paths dac to fft " + dac.countPathsTo("fft"));
        System.out.println("Paths fft to out: " + fft.countPathsTo("out"));
        System.out.println("Paths dac to out " + dac.countPathsTo("out"));
        
    }
}
