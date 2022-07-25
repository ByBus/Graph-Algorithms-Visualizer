package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.domain.SelectState;
import visualizer.presenter.Path;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ShortestPath extends SpanningTree {
    private VertexDataModel startVertex = null;
    protected SelectState selectionType = SelectState.HIGHLIGHTED;

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

    public Path findShortestPath(VertexDataModel end) {
        ShortestPathHighlighted pathHighlighted = new ShortestPathHighlighted();
        VertexDataModel current = end;
        System.out.println(current + " : " + spanningTree.get(current).entrySet());
        do {
            Map.Entry<VertexDataModel, Integer> min = Collections.min(spanningTree.get(current).entrySet(),
                    Map.Entry.comparingByValue());
            pathHighlighted.addEdge(current, min.getKey(), min.getValue());
            current = min.getKey();
        } while (current != startVertex);
        pathHighlighted.setDistance(Collections.min(spanningTree.get(end).values()));
        return pathHighlighted;
    }

    @Override
    public List<Component> toComponents() {
        var components = super.toComponents();
        var vertexUI = startVertex.toVertexUI();
        vertexUI.select(selectionType);
        components.add(vertexUI);
        return components;
    }

    @Override
    public void clear() {
        super.clear();
        startVertex = null;
    }
}
