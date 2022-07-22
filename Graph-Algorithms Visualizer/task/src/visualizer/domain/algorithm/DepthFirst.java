package visualizer.domain.algorithm;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

public class DepthFirst extends TraversalAlgorithm implements Algorithm {

    public DepthFirst(Graph graph, Path path) {
        super(graph, path);
    }

    @Override
    public void traverse(VertexDataModel current) throws InterruptedException {
        traverseHelper(current, 1);
    }

    private void traverseHelper(VertexDataModel current, int currentWeight) throws InterruptedException {
        visited.add(current);
        action.run();
        var adjacent = getAdjacentSortedByWeight(current);
        for (VertexDataModel vertex : adjacent.keySet()) {
            if (!visited.contains(vertex)) {
                try {
                    path.addEdge(current, vertex, currentWeight);
                    traverseHelper(vertex, currentWeight + 1);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}
