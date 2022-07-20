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
        circleGraph(6, 0, 3, 90,'A');
        //defaultGraph();
        //mazeGraph();
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
//        int length = (int) (radius * 2 * Math.PI);
//        int count = length / (100 + 26);

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
}
