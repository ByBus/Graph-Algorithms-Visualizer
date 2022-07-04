package visualizer;

import visualizer.data.Vertex;
import visualizer.data.GraphObserver;

public interface ObservableGraph {
    void addObserver(GraphObserver observer);

    void removeObserver(GraphObserver observer);

    void addVertex(Vertex vertex);

    void notifyObservers(Vertex vertex);
}
