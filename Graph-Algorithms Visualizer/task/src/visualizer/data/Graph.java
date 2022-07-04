package visualizer.data;

import visualizer.ObservableGraph;

import java.util.ArrayList;
import java.util.List;

public class Graph implements ObservableGraph {
    private final List<Vertex> memory = new ArrayList<>();
    private final List<GraphObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(GraphObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GraphObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void addVertex(Vertex vertex) {
        memory.add(vertex);
        notifyObservers(vertex);
    }

    @Override
    public void notifyObservers(Vertex vertex) {
        observers.forEach(observer -> observer.update(vertex));
    }
}
