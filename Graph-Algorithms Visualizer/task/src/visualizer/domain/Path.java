package visualizer.domain;

import visualizer.data.VertexDataModel;
import visualizer.presenter.Edge;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Path {
    private final Map<VertexDataModel, HashMap<VertexDataModel, Integer>> spanningTree = new LinkedHashMap<>();

    public void addVertex(VertexDataModel vertex) {
        spanningTree.putIfAbsent(vertex, new HashMap<>());
    }

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

    public List<Component> toComponents() {
        List<Component> componentList = new ArrayList<>();
        spanningTree.forEach((vert, connections) -> {
            var vertexUI = vert.toVertexUI();
            vertexUI.setSelected(true);
            componentList.add(vertexUI);
            connections.forEach((connectedVert, weight) -> {
                componentList.add(new Edge(vertexUI, connectedVert.toVertexUI(), true));
                componentList.add(new Edge(connectedVert.toVertexUI(), vertexUI, true));
            });
        });
        return componentList;
    }
}
