package visualizer.data;

import visualizer.Observable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph implements Observable {
    private final Map<Vertex, Map<Vertex, Integer>> graph = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void addVertex(Vertex vertex) {
        graph.putIfAbsent(vertex, new HashMap<>());
        notifyObservers(vertex);
    }

    @Override
    public void addEdge(Edge edge) {
        Vertex start = edge.start;
        Vertex end = edge.end;
        graph.get(start).put(end, edge.weight);
        graph.get(end).put(start, edge.weight);
        notifyObservers(edge);
    }

    @Override
    public void notifyObservers(JComponent component) {
        observers.forEach(observer -> observer.update(component));
    }
}
