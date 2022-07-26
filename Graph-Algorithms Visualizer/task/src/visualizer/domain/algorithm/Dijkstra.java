package visualizer.domain.algorithm;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra extends TraversalAlgorithm implements Algorithm {
    private static final int INFINITY = Integer.MAX_VALUE;
    private final Queue<Distance> priorityQueue = new PriorityQueue<>();
    public Dijkstra(Graph graph, Path path) {
        super(graph, path);
    }
    private VertexDataModel startCached = null;
    private Path pathCached = null;

    @Override
    public void traverse(VertexDataModel start) {
        if (startCached == null) {
            startCached = start;
            HashMap<VertexDataModel, Distance> distances = prepareDistances();
            path.addVertex(start);

            Distance startDistance = new Distance(start, 0);
            distances.put(start, startDistance);
            priorityQueue.offer(startDistance);

            while (priorityQueue.peek() != null) {
                var current = priorityQueue.poll();
                var neighbors = getNeighbors(current);
                for (var neighbor : neighbors) {
                    int newDistance = distances.get(current.vertex).distance + neighbor.distance;
                    if (newDistance < distances.get(neighbor.vertex).distance) {
                        distances.put(neighbor.vertex, new Distance(current.vertex, newDistance));
                        executeStepAction();
                        path.addEdge(current.vertex, neighbor.vertex, newDistance);

                        priorityQueue.offer(neighbor);
                    }
                }
            }
        }
        if (!startCached.equals(start)) {
            findPath(start);
        }
    }

    private void findPath(VertexDataModel end) {
        pathCached = path;
        path = ((ShortestPath) path).findShortestPath(end);
        callback.onPathReady(path);
    }

    @Override
    public Path getPath() {
        Path result = path;
        if (pathCached != null) {
            path = pathCached;
            pathCached = null;
        }
        return result;
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
        return graph.getAdjacent(vertexDistance.vertex).entrySet().stream()
                .map(entry -> new Distance(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        super.reset();
        startCached = null;
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
