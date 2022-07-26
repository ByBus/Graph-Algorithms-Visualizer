package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.domain.SelectState;
import visualizer.presenter.Edge;
import visualizer.presenter.Path;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SpanningTree implements Path {
    protected final Map<VertexDataModel, HashMap<VertexDataModel, Integer>> spanningTree = new LinkedHashMap<>();
    protected SelectState selectionType = SelectState.SELECTED;

    @Override
    public void addVertex(VertexDataModel vertex) {
        spanningTree.putIfAbsent(vertex, new HashMap<>());
    }

    @Override
    public void addEdge(VertexDataModel start, VertexDataModel end, int weight) {
        addVertex(start);
        addVertex(end);
        spanningTree.get(start).put(end, weight);
        spanningTree.get(end).put(start, weight);
    }

    @Override
    public String toString() {
        return spanningTree.keySet().stream()
                .map(VertexDataModel::getIndex)
                .collect(Collectors.joining(" -> "));
    }

    @Override
    public List<Component> toComponents() {
        List<Component> componentList = new ArrayList<>();
        spanningTree.forEach((vert, connections) -> {
            var vertexUI = vert.toVertexUI();
            vertexUI.setSelected(selectionType);
            componentList.add(vertexUI);
            connections.forEach((connectedVert, weight) -> {
                componentList.add(new Edge(vertexUI, connectedVert.toVertexUI(), selectionType));
            });
        });
        return componentList;
    }

    @Override
    public void clear() {
        spanningTree.clear();
    }
}
