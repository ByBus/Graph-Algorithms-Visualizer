package visualizer.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SavableGraph implements Savable<SavableGraph.GraphState> {
    protected final Map<VertexDataModel, Map<VertexDataModel, Integer>> graph = new HashMap<>();

    @Override
    public GraphState getState() {
        var states = graph.entrySet()
                .stream()
                .map(entry -> new GraphState.VertexState(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new GraphState(states);
    }

    @Override
    public void setState(GraphState state) {
        if (graph.isEmpty()) return;
        for (var vertState : state.vertexStates) {
            var oldVertex = graph.keySet().stream()
                    .filter(v -> v.getIndex().equals(vertState.vertex.getIndex()))
                    .findFirst().orElse(vertState.vertex);
            oldVertex.setParameters(vertState.x, vertState.y, vertState.isSelected);
            var adjacent = graph.get(oldVertex);
            adjacent.forEach((key, value) -> adjacent.put(key, vertState.connections.get(key)));
        }
        notifyObservers();
    }

    protected abstract void notifyObservers();

    public static class GraphState {
        private final List<Graph.GraphState.VertexState> vertexStates = new ArrayList<>();

        @Override
        public String toString() {
            return "GraphState{" +
                    "vertexStates=" + vertexStates +
                    '}';
        }

        protected GraphState(List<Graph.GraphState.VertexState> states) {
            this.vertexStates.addAll(states);
        }

         static class VertexState {
            private final VertexDataModel vertex;
            private final Map<VertexDataModel, Integer> connections = new HashMap<>();
            public final boolean isSelected;
            private final int x;
            private final int y;

            public VertexState(VertexDataModel vertex, Map<VertexDataModel, Integer> adjacent) {
                adjacent.forEach(((vertexDataModel, weight) -> {
                    connections.put(vertexDataModel.copy(), weight);
                }));
                this.vertex = vertex;
                this.x = vertex.getX();
                this.y = vertex.getY();
                this.isSelected = vertex.isHighlighted();
            }

            @Override
            public String toString() {
                return "VertexState{" +
                        "vertex=" + vertex +
                        ", connections=" + connections +
                        ", isSelected=" + isSelected +
                        '}';
            }
        }
    }
}
