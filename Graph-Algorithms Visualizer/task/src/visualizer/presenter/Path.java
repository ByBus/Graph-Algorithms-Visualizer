package visualizer.presenter;

import visualizer.data.VertexDataModel;

import java.awt.*;
import java.util.List;

public interface Path {
    void addVertex(VertexDataModel vertex);

    void addEdge(VertexDataModel start, VertexDataModel end, int weight);

    @Override
    String toString();

    List<Component> toComponents();

    void clear();
}
