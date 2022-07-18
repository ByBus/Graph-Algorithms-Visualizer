package visualizer;

import visualizer.data.Observer;
import visualizer.data.VertexDataModel;

public interface Observable {
    void addObserver(Observer observer);

    void addVertex(VertexDataModel vertex);

    void addEdge(VertexDataModel start, VertexDataModel end, int weight);

    void notifyObservers();
}
