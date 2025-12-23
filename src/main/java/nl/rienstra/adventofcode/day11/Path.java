package nl.rienstra.adventofcode.day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path {
    private final String name;
    private boolean visited = false;
    private List<List<Path>> pathsToTarget = new ArrayList<>();
    private Map<String, Long> pathCountCache = new HashMap<>();

    private final List<Path> connections;

    public Path(String name, List<Path> connections) {
        this.name = name;
        this.connections = connections;
    }
    
    public void visit(String target) {
        if (visited) {
            return;
        }
        
        if (this.getName().equals(target)) {
            pathsToTarget.add(new ArrayList<>());
            pathsToTarget.get(0).add(this);
        }
        
        for (Path path : connections) {
            path.visit(target);
            for (List<Path> p : path.getPathsToTarget()) {
                List<Path> newPath = new ArrayList<>(p);
                newPath.add(this);
                pathsToTarget.add(newPath);
            }
        }
        
        visited = true;
    }
    
    public List<List<Path>> getPathsToTarget() {
        return pathsToTarget;
    }

    public List<List<Path>> getPathsTo(String target) {
        List<List<Path>> result = new java.util.ArrayList<>();
        if (this.getName().equalsIgnoreCase(target)) {
            result.add(new ArrayList<>());
            result.get(0).add(this);
            return result;
        }

        for (Path connection : connections) {
            List<List<Path>> paths = connection.getPathsTo(target);
            if (!paths.isEmpty()) {
                for (List<Path> path : paths) {
                    if (!path.contains(this)) {
                        path.add(this);
                        result.add(path);
                    }
                }
            }

        }

        return result;
    }
    
    public long countPathsTo(String target) {
        if (pathCountCache.containsKey(target)) {
            return pathCountCache.get(target);
        }
        
        if (this.getName().equalsIgnoreCase(target)) {
            return 1;
        }
        
        long result = 0;
        for (Path connection : connections) {
            result += connection.countPathsTo(target);
        }

        pathCountCache.put(target, result);
        return result;
    }
    
    public void resetVisited() {
        this.visited = false;
        this.pathsToTarget = new ArrayList<>();
        for (Path path : connections) {
            path.resetVisited();
        }
    }

    public void addConnection(Path path) {
        connections.add(path);
    }

    public String getName() {
        return name;
    }
}
