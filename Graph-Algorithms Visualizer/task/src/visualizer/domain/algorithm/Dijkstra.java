package visualizer.domain.algorithm;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.domain.ShortestPath;
import visualizer.presenter.Path;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra extends TraversalAlgorithm implements Algorithm {
    private Path path = new ShortestPath();
    private static final int INFINITY = Integer.MAX_VALUE;
    private final Queue<Distance> priorityQueue = new PriorityQueue<>();

    public Dijkstra(Graph graph) {
        super(graph);
    }

    @Override
    public void traverse(VertexDataModel start) {
        HashMap<VertexDataModel, Distance> distances = prepareDistances();
        path.addVertex(start);

        Distance startVertex = new Distance(start, 0);
        distances.put(start, startVertex);
        priorityQueue.offer(startVertex);

        while (priorityQueue.peek() != null) {
            var current = priorityQueue.poll();
            var neighbors = getNeighbors(current);
            for (var neighbor : neighbors) {
                if (!visited.contains(neighbor.vertex)) {
                    int newDistance = distances.get(current.vertex).distance + neighbor.distance;
                    if (newDistance < distances.get(neighbor.vertex).distance) {
                        var previousConnectedVertex = distances.get(neighbor.vertex).vertex;
                        distances.put(neighbor.vertex, new Distance(current.vertex, newDistance));
                        try {
                            action.run();
                        } catch (InterruptedException ignored) {
                        }
                        path.addEdge(current.vertex, neighbor.vertex, newDistance);
                        path.removeEdge(neighbor.vertex, previousConnectedVertex);
                    }
                    priorityQueue.offer(neighbor);
                }
            }
            visited.add(current.vertex);

        }

//        for (var k : distances.keySet()) {
//            if (k.equals(start)) continue;
//            path.addEdge(start, k, distances.get(k));
//        }
    }

    @Override
    public Path getPath() {
        return path;
    }

    private HashMap<VertexDataModel, Distance> prepareDistances() {
        var allVertices = graph.getGraph().keySet();
        HashMap<VertexDataModel, Distance> map = new LinkedHashMap<>();
        for (VertexDataModel vert : allVertices) {
            map.put(vert, new Distance(vert, INFINITY));
        }
        return map;
    }

    private List<Distance> getNeighbors(Distance vertexDistance) {
        return getAdjacentSortedByWeight(vertexDistance.vertex).entrySet().stream()
                .map(entry -> new Distance(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        visited.clear();
        path = new ShortestPath();
        priorityQueue.clear();
    }

    private static class Distance implements Comparable<Distance> {
        private final VertexDataModel vertex;
        private final int distance;

        public Distance(VertexDataModel vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Distance o) {
            //return with the lowest distance
            return Integer.compare(distance, o.distance);
        }
    }
}
