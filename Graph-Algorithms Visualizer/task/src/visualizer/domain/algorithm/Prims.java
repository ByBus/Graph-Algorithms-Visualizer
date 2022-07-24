package visualizer.domain.algorithm;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

import java.util.*;
import java.util.stream.Collectors;

public class Prims extends TraversalAlgorithm implements Algorithm{
    private final Queue<PrimEdge> priorityQueue = new PriorityQueue<>();

    public Prims(Graph graph, Path path) {
        super(graph, path);
    }

    @Override
    public void traverse(VertexDataModel start) {
        List<PrimEdge> adjacent = getPrimEdges(start);
        priorityQueue.addAll(adjacent);
        PrimEdge edge;
        while ((edge = priorityQueue.poll()) != null) {
            if (visited.containsAll(edge.vertices())) continue;
            for (var vert : edge.vertices()) {
                if (!visited.contains(vert)) {
                    priorityQueue.addAll(getPrimEdges(vert));
                }
            }
            executeAction();
            path.addEdge(edge.end, edge.start, edge.distance);
            visited.addAll(edge.vertices());
        }
    }

    private List<PrimEdge> getPrimEdges(VertexDataModel start) {
        return graph.getAdjacent(start).entrySet()
                .stream().map(entry -> new PrimEdge(start, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private static class PrimEdge implements Comparable<PrimEdge>{
        private final VertexDataModel start;
        private final VertexDataModel end;
        private final int distance;

        public PrimEdge(VertexDataModel start, VertexDataModel end, int distance) {
            this.start = start;
            this.end = end;

            this.distance = distance;
        }

        public Set<VertexDataModel> vertices() {
            return new HashSet<>(Set.of(start, end));
        }

        @Override
        public int compareTo(PrimEdge o) {
            return Integer.compare(distance, o.distance);
        }
    }
}
