package visualizer.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestGraphData {
    private final Graph graph;
    private final Observer[] observers;

    public TestGraphData(Graph graph, Observer... observers) {
        this.graph = graph;
        this.observers = observers;
    }

    private void before() {
        Arrays.stream(observers).forEach(graph::removeObserver);
    }

    private void after() {
        Arrays.stream(observers).forEach(graph::addObserver);
        graph.notifyObservers();
    }

    public void setup() {
        before();
        //circleGraph(16, 3, 6, 90,'A');
        //defaultGraph();
        //mazeGraph();
        //rhombusGraph();
        //candy();
        after();
    }

    private void defaultGraph() {
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

    private void mazeGraph() {
        int step = 101;
        List<VertexDataModel> vert = new ArrayList<>();
        var random = new Random();
        char i = 'A';
        for (int x = 15; x < 800 - 50; x += step) {
            for (int y = 15; y < 600 - 100; y += step) {
                var newVert = new VertexDataModel(x, y, String.valueOf(i++));
                vert.add(newVert);
                graph.addVertex(newVert);
                vert.stream().filter(v -> (v.getX() == newVert.getX() && Math.abs(v.getY() - newVert.getY()) == step)
                                || (v.getY() == newVert.getY() && Math.abs(v.getX() - newVert.getX()) == step))
                        .forEach(v -> graph.addEdge(newVert, v, random.nextInt(9) + 1));
            }
        }
    }

    private void circleGraph(int count, int skip, int connect, int initialAngle, char startIndex) {
        var random = new Random();
        List<VertexDataModel> vertices = new ArrayList<>();
        char name = startIndex;
        int centerX = 800 / 2 - 35;
        int centerY = 600 / 2 - 70;
        int radius = 600 / 2 - 75;

        for (int i = 0; i < count; i++) {
            double angle = initialAngle + (360.0 / count) * i;
            double x = (radius * Math.cos(Math.toRadians(angle)));
            double y = (radius * Math.sin(Math.toRadians(angle)));
            var vertex = new VertexDataModel((int) (centerX + x), (int) (centerY + y), String.valueOf(name++));
            graph.addVertex(vertex);
            vertices.add(vertex);
        }

        for (int j = 0; j < vertices.size(); j++) {
            var start = vertices.get(j);
            for (int k = j + skip + 1; k < j + skip + 1 + connect; k++) {
                var end = vertices.get(k % vertices.size());
                graph.addEdge(start, end, random.nextInt(9) + 1);
            }
        }
    }

    private void rhombusGraph() {
        var a = new VertexDataModel(170, 400, "A");
        var b = new VertexDataModel(250, 50, "B");
        var c = new VertexDataModel(600, 100, "C");
        var d = new VertexDataModel(575, 300, "D");
        var verts = List.of(a, b, c, d);

        verts.forEach(graph::addVertex);
        graph.addEdge(a, b, 1);
        graph.addEdge(a, d, 2);
        graph.addEdge(b, c, 4);
        graph.addEdge(c, d, 2);
    }

    private void candyGraph() {
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
