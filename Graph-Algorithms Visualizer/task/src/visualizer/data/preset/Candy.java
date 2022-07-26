package visualizer.data.preset;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;

import java.util.List;

public class Candy extends Preset{
    public Candy(Graph graph) {
        super(graph, "Candy");
    }

    @Override
    protected void creationOfGraph() {
        int xCenter = 360;
        int yCenter = 220;
        int delta = 140;
        int x1 = xCenter - delta * 2;
        int x2 = xCenter - delta;
        int x3 = xCenter;
        int x4 = xCenter + delta;
        int x5 = xCenter + delta * 2;
        int y1 = yCenter - delta;
        int y2 = yCenter;
        int y3 = yCenter + delta;

        var a = new VertexDataModel(x1, y2, "A");
        var b = new VertexDataModel(x2, y1, "B");
        var c = new VertexDataModel(x2, y3, "C");
        var d = new VertexDataModel(x3, y2, "D");
        var e = new VertexDataModel(x4, y1, "E");
        var f = new VertexDataModel(x4, y3, "F");
        var g = new VertexDataModel(x5, y2, "G");
        var verts = List.of(a, b, c, d, e, f, g);

        verts.forEach(graph::addVertex);
        graph.addEdge(a, b, 4);
        graph.addEdge(a, c, 7);
        graph.addEdge(b, c, 1);
        graph.addEdge(b, d, 3);
        graph.addEdge(d, c, 8);
        graph.addEdge(c, f, 5);
        graph.addEdge(b, e, 10);
        graph.addEdge(d, e, 5);
        graph.addEdge(d, f, 1);
        graph.addEdge(e, f, 3);
        graph.addEdge(e, g, 4);
        graph.addEdge(f, g, 9);
    }
}
