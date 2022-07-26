package visualizer.data.preset;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;

import java.util.List;

public class PrimTest extends Preset{
    public PrimTest(Graph graph) {
        super(graph, "Prim test");
    }

    @Override
    protected void creationOfGraph() {
        int x0 = 65;
        int y0 = 130;
        int i = 200;
        var v0 = new VertexDataModel(x0, y0, "0");
        var v1 = new VertexDataModel(x0, y0 + i, "1");
        var v2 = new VertexDataModel(x0 + i, y0, "2");
        var v3 = new VertexDataModel(x0 + i, y0 + i, "3");
        var v4 = new VertexDataModel(x0 + i * 2, y0, "4");
        var v5 = new VertexDataModel(x0 + i * 2, y0 + i, "5");
        var v6 = new VertexDataModel(x0 + i * 3, y0, "6");
        var v7 = new VertexDataModel(x0 + i * 3, y0 + i, "7");
        var verts = List.of(v0, v1, v2, v3, v4, v5, v6, v7);
        verts.forEach(graph::addVertex);

        graph.addEdge(v0, v1, 1);
        graph.addEdge(v0, v2, 1);
        graph.addEdge(v0, v3, 2);
        graph.addEdge(v1, v3, 2);
        graph.addEdge(v3, v2, 4);
        graph.addEdge(v3, v5, 2);
        graph.addEdge(v3, v4, 3);
        graph.addEdge(v2, v4, 4);
        graph.addEdge(v5, v4, 3);
        graph.addEdge(v5, v7, 3);
        graph.addEdge(v4, v7, 1);
        graph.addEdge(v4, v6, 5);
        graph.addEdge(v6, v7, 2);
    }
}
