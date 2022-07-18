package visualizer.data.checker;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.presenter.Checker;

public class UniqueVertexChecker implements Checker {
    private final Graph graph;

    public UniqueVertexChecker(Graph graph) {
        this.graph = graph;
    }
    @Override
    public Boolean check(String input) {
        return graph.getGraph()
                .keySet()
                .stream()
                .map(VertexDataModel::getIndex)
                .noneMatch(index -> index.equals(input));
    }
}
