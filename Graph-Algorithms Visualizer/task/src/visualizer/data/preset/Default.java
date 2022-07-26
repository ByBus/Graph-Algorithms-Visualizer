package visualizer.data.preset;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;

import java.util.List;

public class Default extends Preset{
    public Default(Graph graph) {
        super(graph, "Default");
    }

    @Override
    public void creationOfGraph() {
        var a = new VertexDataModel(60, 420, "A");
        var b = new VertexDataModel(90, 200, "B");
        var c = new VertexDataModel(300, 400, "C");
        var d = new VertexDataModel(358, 50, "D");
        var e = new VertexDataModel(575, 280, "E");
        var f = new VertexDataModel(600, 70, "F");
        var verts = List.of(a, b, c, d, e, f);

        verts.forEach(graph::addVertex);
        graph.addEdge(a, b, 1);
        graph.addEdge(a, c, 6);
        graph.addEdge(b, c, 9);
        graph.addEdge(b, d, 2);
        graph.addEdge(b, e, 7);
        graph.addEdge(c, e, 5);
        graph.addEdge(c, d, 8);
        graph.addEdge(d, e, 10);
        graph.addEdge(d, f, 3);
        graph.addEdge(e, f, 4);
    }
}
