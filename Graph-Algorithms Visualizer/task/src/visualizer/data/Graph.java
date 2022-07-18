package visualizer.data;

import visualizer.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Graph implements Observable {
    private final Map<VertexDataModel, Map<VertexDataModel, Integer>> graph = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void addVertex(VertexDataModel vertex) {
        var result = graph.putIfAbsent(vertex, new HashMap<>());
        if (result == null) notifyObservers();
    }

    public void addEdge(VertexDataModel start, VertexDataModel end, int weight) {
        graph.get(start).put(end.withLabel(), weight);
        graph.get(end).put(start, weight);
        notifyObservers();
    }

    public void updateEdge(VertexDataModel start, VertexDataModel end, int weight) {
       getAdjacent(start).put(end, weight);
       getAdjacent(end).put(start, weight);
    }

    public void removeVertex(VertexDataModel vertex) {
        Map<VertexDataModel, Integer> edges = getAdjacent(vertex);
        for (var edge : edges.entrySet()) {
            getAdjacent(edge.getKey()).remove(vertex);
        }
        graph.remove(vertex);
        notifyObservers();
    }

    public void removeEdge(VertexDataModel start, VertexDataModel end) {
        Map<VertexDataModel, Integer> startEdges = getAdjacent(start);
        Map<VertexDataModel, Integer> endEdges = getAdjacent(end);
        startEdges.remove(end);
        endEdges.remove(start);
        notifyObservers();
    }

    public Map<VertexDataModel, Integer> getAdjacent(VertexDataModel vertex) {
        return graph.get(vertex);
    }

    public Map<VertexDataModel, Map<VertexDataModel, Integer>> getGraph() {
        return graph;
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var entry : graph.entrySet()) {
            sb.append("{").append(entry.getKey()).append(":[");
            entry.getValue().forEach((key, value) -> sb.append(key).append(" = ").append(value).append(", "));
            sb.append("]},\n");
        }
        return sb.toString();
    }

    public GraphState getState() {
        var states = graph.entrySet()
                .stream()
                .map(entry -> new GraphState.VertexState(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new GraphState(states);
    }

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

    public void clear() {
        graph.clear();
        notifyObservers();
    }

    static class GraphState {
        private final List<VertexState> vertexStates = new ArrayList<>();

        @Override
        public String toString() {
            return "GraphState{" +
                    "vertexStates=" + vertexStates +
                    '}';
        }

        private GraphState(List<VertexState> states) {
            this.vertexStates.addAll(states);
        }

        private static class VertexState {
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
