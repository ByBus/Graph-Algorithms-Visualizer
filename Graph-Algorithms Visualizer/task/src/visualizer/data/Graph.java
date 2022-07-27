package visualizer.data;

import visualizer.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph extends SavableGraph implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
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

    public void addDirectedEdge(VertexDataModel start, VertexDataModel end, int weight) {
        graph.get(start).put(end.withLabel(), weight);
        notifyObservers();
    }

    public boolean isDirected(VertexDataModel start, VertexDataModel end) {
        try {
            return !(graph.get(start).containsKey(end) && graph.get(end).containsKey(start));
        } catch (Exception e) {
            return false;
        }
    }

    public void removeVertex(VertexDataModel vertex) {
        Map<VertexDataModel, Integer> edges = getAdjacent(vertex);
        graph.keySet().forEach(k -> graph.get(k).remove(vertex));
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var entry : graph.entrySet()) {
            sb.append("{").append(entry.getKey()).append(":[");
            entry.getValue().forEach((key, value) -> sb.append(key).append(" = ").append(value).append(", "));
            sb.append("]},\n");
        }
        return sb.toString();
    }

    public void clear() {
        graph.clear();
        notifyObservers();
    }
}
