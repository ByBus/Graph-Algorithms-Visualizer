package visualizer.domain.algorithm;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class BreadthFirst extends TraversalAlgorithm implements Algorithm {
    private final Queue<VertexDataModel> queue = new ArrayDeque<>();

    public BreadthFirst(Graph graph) {
        super(graph);
    }

    @Override
    public void traverse(VertexDataModel start) {
        Map<VertexDataModel, Integer> distance = new HashMap<>(Map.of(start, 0));
        queue.add(start);
        visited.add(start);

        while (queue.peek() != null) {
            var current = queue.poll();
            var adjacent = getAdjacentSortedByWeight(current);
            visited.addAll(adjacent.keySet());
            adjacent.forEach((vertex, weight) -> {
                distance.putIfAbsent(vertex, distance.get(current) + 1);
                queue.add(vertex);
                try {
                    action.run();
                } catch (InterruptedException ignored) {}
                path.addEdge(current, vertex, distance.get(vertex));
            });
        }
    }


    @Override
    public void reset() {
        super.reset();
        queue.clear();
    }

}
