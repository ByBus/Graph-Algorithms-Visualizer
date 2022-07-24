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
    private VertexDataModel startVertex = null;
    private Path savedPath = null;

    @Override
    public void traverse(VertexDataModel clickedVertex) {
        if (startVertex == null) {
            startVertex = clickedVertex;
            HashMap<VertexDataModel, Distance> distances = prepareDistances();
            path.addVertex(clickedVertex);

            Distance startVertex = new Distance(clickedVertex, 0);
            distances.put(clickedVertex, startVertex);
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
                            executeStepAction();
                            path.addEdge(current.vertex, neighbor.vertex, newDistance);
                            path.removeEdge(neighbor.vertex, previousConnectedVertex);
                        }
                        priorityQueue.offer(neighbor);
                    }
                }
                visited.add(current.vertex);
            }
        }
        if (!startVertex.equals(clickedVertex)) {
            findPath(clickedVertex);
        }
    }

    private void findPath(VertexDataModel end) {
        savedPath = path;
        path = ((ShortestPath) path).findShortestPath(end);
        action.onEnd(path);
    }

    @Override
    public Path getPath() {
        Path result = path;
        if (savedPath != null) {
            path = savedPath;
            savedPath = null;
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
        return getAdjacentSortedByWeight(vertexDistance.vertex).entrySet().stream()
                .map(entry -> new Distance(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        super.reset();
        startVertex = null;
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
