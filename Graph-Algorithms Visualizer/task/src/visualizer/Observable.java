package visualizer;

import visualizer.data.Edge;
import visualizer.data.Vertex;
import visualizer.data.Observer;

import javax.swing.*;

public interface Observable {
    void addObserver(Observer observer);

    void addVertex(Vertex vertex);

    void addEdge(Edge edge);

    void notifyObservers(JComponent component);
}
