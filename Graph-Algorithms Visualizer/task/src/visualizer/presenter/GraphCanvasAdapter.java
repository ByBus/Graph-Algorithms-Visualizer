package visualizer.presenter;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraphCanvasAdapter {
    private final List<Component> componentList = new ArrayList<>();
    private final GraphCanvas canvas;
    private final Set<VertexUI> buffer = new HashSet<>(); // To save references to same components

    public GraphCanvasAdapter(GraphCanvas canvas) {
        this.canvas = canvas;
    }

    public void submitGraph(Graph graph) {
        fulfillBuffer(graph.getGraph().keySet());
        componentList.clear();

        graph.getGraph().forEach((startVertexDM, adjacent) ->{
            var startVertex = getExistingVertexUi(startVertexDM);
            componentList.add(0, startVertex);
            adjacent.forEach((endVertexDM, weight) -> {
                var edge = new Edge(startVertex, getExistingVertexUi(endVertexDM), weight);
                componentList.add(edge);
                if (endVertexDM.isShowLabel()) {
                    componentList.add(1, edge.label());
                }
            });
        });

        canvas.syncComponents(componentList);
        buffer.clear();
    }

    private void fulfillBuffer(Set<VertexDataModel> vertices) {
        buffer.addAll(vertices.stream()
                .map(VertexDataModel::toVertexUI)
                .collect(Collectors.toSet())
        );
    }

    private VertexUI getExistingVertexUi(VertexDataModel vertex) {
        var canvasVertex = canvas.getNamedComponent(vertex.toVertexUI().getName());
        var foundVertex = canvasVertex != null ? (VertexUI) canvasVertex : vertexUiFromBuffer(vertex.getIndex());
        foundVertex.setSelected(vertex.isHighlighted());
        return foundVertex;
    }

    private VertexUI vertexUiFromBuffer(String index) {
        return buffer.stream()
                .filter(v -> v.getIndex().equals(index))
                .findFirst().orElse(null);
    }
}
