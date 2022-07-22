package visualizer.presenter;

import visualizer.data.VertexDataModel;

import java.awt.*;
import java.util.List;

public interface Path {
    void addVertex(VertexDataModel vertex);

    void addEdge(VertexDataModel start, VertexDataModel end, int weight);

    void removeEdge(VertexDataModel start, VertexDataModel end);

    @Override
    String toString();

    List<Component> toComponents();
}
