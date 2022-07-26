package visualizer.data.preset;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;

import java.util.List;

public class Rhombus extends Preset{
    public Rhombus(Graph graph) {
        super(graph, "Rhombus");
    }

    @Override
    protected void creationOfGraph() {
        var a = new VertexDataModel(270, 370, "A");
        var b = new VertexDataModel(150, 50, "B");
        var c = new VertexDataModel(480, 80, "C");
        var d = new VertexDataModel(600, 400, "D");
        var verts = List.of(a, b, c, d);

        verts.forEach(graph::addVertex);
        graph.addEdge(a, b, 1);
        graph.addEdge(a, d, 2);
        graph.addEdge(b, c, 4);
        graph.addEdge(c, d, 2);
    }
}
