package visualizer.data;

import visualizer.data.checker.PositiveNumberChecker;
import visualizer.domain.usecases.Dialog;
import visualizer.presenter.InputCircleGraphPresetDialog;

import java.util.*;

public class TestGraphData {
    private final Graph graph;
    private final Observer[] observers;
    private final Map<String, Runnable> graphs = Map.of(
            "Circle", circleGraph(),
            "Default", defaultGraph(),
            "Maze", mazeGraph(),
            "Rhombus", rhombusGraph(),
            "Candy", candyGraph(),
            "Prim test", primTest()
    );

    private final InputCircleGraphPresetDialog circleParametersDialog =
            new InputCircleGraphPresetDialog("Input parameters of the Graph", new PositiveNumberChecker());

    public TestGraphData(Graph graph, Observer... observers) {
        this.graph = graph;
        this.observers = observers;
        addDialogCallback();
    }

    private void before() {
        graph.clear();
        Arrays.stream(observers).forEach(graph::removeObserver);
    }

    private void after() {
        Arrays.stream(observers).forEach(graph::addObserver);
        graph.notifyObservers();
    }

    public void draw(String name) {
        graphs.get(name).run();
    }

    public List<String> graphsNames() {
        return new ArrayList<>(graphs.keySet());
    }

    private Runnable defaultGraph() {
        return () -> {
            before();
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
            after();
        };
    }

    private Runnable mazeGraph() {
        return () -> {
            before();
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
            after();
        };
    }

    private Runnable circleGraph() {
        return () -> {
            circleParametersDialog.show();
        };
    }

    private void circleGraph(int count, int skip, int connect, int initialAngle, char startIndex) {
        before();
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
        after();
    }

    private Runnable rhombusGraph() {
        return () -> {
            before();
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
            after();
        };
    }

    private Runnable candyGraph() {
        return () -> {
            before();
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
            after();
        };
    }

    private Runnable primTest() {
        return () -> {
            before();
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
            after();
        };
    }

    private void addDialogCallback() {
        circleParametersDialog.addCallBack(new Dialog.Callback() {
            @Override
            public void onSuccess(String index) {
                int[] params = Arrays.stream(index.split(";")).mapToInt(Integer::parseInt).toArray();
                circleGraph(params[0], params[1], params[2], params[3], 'A');
            }

            @Override
            public void onFailed() {
                circleParametersDialog.show();
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
