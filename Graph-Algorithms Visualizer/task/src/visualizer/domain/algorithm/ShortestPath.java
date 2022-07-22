package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.domain.SelectState;

import java.awt.*;
import java.util.List;
import java.util.StringJoiner;

public class ShortestPath extends SpanningTree {
    private VertexDataModel startVertex = null;

    @Override
    public void addVertex(VertexDataModel vertex) {
        super.addVertex(vertex);
        if (startVertex == null) {
            startVertex = vertex;
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        for (var vert : spanningTree.keySet()) {
            if (vert.equals(startVertex)) continue;
            var minDistance = spanningTree.get(vert).values()
                    .stream()
                    .mapToInt(i -> i)
                    .min().orElse(0);
            joiner.add(vert + "=" + minDistance);
        }
        return joiner.toString();
    }

    @Override
    public List<Component> toComponents() {
        var components = super.toComponents();
        var vertexUI = startVertex.toVertexUI();
        vertexUI.select(SelectState.HIGHLIGHTED);
        components.add(vertexUI);
        return components;
    }

    @Override
    public void clear() {
        super.clear();
        startVertex = null;
    }
}
