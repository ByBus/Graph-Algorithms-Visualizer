package visualizer.domain.algorithm;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.domain.Path;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

abstract class TraversalAlgorithm implements Algorithm{
    protected final Set<VertexDataModel> visited = new HashSet<>();
    protected final Graph graph;
    protected Path path = new Path();
    protected SubStepAction action = () -> { };

    public TraversalAlgorithm(Graph graph) {
        this.graph = graph;
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
    public void setAction(SubStepAction action) {
        this.action = action;
    }

    @Override
    public void reset() {
        visited.clear();
        path = new Path();
    }
}
