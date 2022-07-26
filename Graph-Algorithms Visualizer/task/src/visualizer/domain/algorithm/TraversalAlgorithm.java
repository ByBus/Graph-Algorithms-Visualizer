package visualizer.domain.algorithm;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

abstract class TraversalAlgorithm implements Algorithm{
    protected final Set<VertexDataModel> visited = new HashSet<>();
    protected final Graph graph;
    protected Path path;
    protected Callback callback = null;

    public TraversalAlgorithm(Graph graph, Path path) {
        this.graph = graph;
        this.path = path;
    }

    protected LinkedHashMap<VertexDataModel, Integer> getAdjacentSortedByWeight(VertexDataModel current) {
        return graph.getAdjacent(current)
                .entrySet().stream()
                .filter(entry -> !visited.contains(entry.getKey()))
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)
                );
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void executeStepAction() {
        try {
            callback.onEveryStep();
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void reset() {
        visited.clear();
        path.clear();
    }
}
