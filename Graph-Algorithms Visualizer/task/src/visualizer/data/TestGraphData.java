package visualizer.data;

import java.util.List;

public class TestGraphData {
    Graph graph;

    public TestGraphData(Graph graph) {
        this.graph = graph;
    }

    public void setup() {
        var a = new VertexDataModel(77, 402, "A");
        var b = new VertexDataModel(83, 175, "B");
        var c = new VertexDataModel(358, 423, "C");
        var d = new VertexDataModel(324, 20, "D");
        var e = new VertexDataModel(575, 311, "E");
        var f = new VertexDataModel(591, 36, "F");
        var verts = List.of(a, b, c, d, e, f);

        verts.forEach(v -> graph.addVertex(v));
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
