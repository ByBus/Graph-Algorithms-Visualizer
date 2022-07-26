package visualizer.data.preset;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.data.checker.IndexChecker;
import visualizer.data.checker.PositiveNumberChecker;
import visualizer.domain.usecases.Dialog;
import visualizer.presenter.InputCircleGraphPresetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Circle extends Preset {
    private final Dialog parametersDialog;

    public Circle(Graph graph) {
        super(graph, "Circle");
        this.parametersDialog = new InputCircleGraphPresetDialog(
                "Input parameters of the Graph",
                new PositiveNumberChecker(),
                new IndexChecker()
        );
        init();
    }

    private void init() {
        parametersDialog.addCallBack(new Dialog.Callback() {
            @Override
            public void onSuccess(String parameters) {
                int[] params = Arrays.stream(parameters.split(";")).mapToInt(Integer::parseInt).toArray();
                configurationCallback.onBefore();
                circleGraph(params[0], params[1], params[2], params[3], (char) params[4]);
                configurationCallback.onAfter();
            }

            @Override
            public void onFailed() {
                parametersDialog.show();
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void circleGraph(int count, int skip, int connect, int initialAngle, char startIndex) {
        var random = new Random();
        List<VertexDataModel> vertices = new ArrayList<>();
        char index = startIndex;
        int centerX = 800 / 2 - 35;
        int centerY = 600 / 2 - 70;
        int radius = 600 / 2 - 75;

        for (int i = 0; i < count; i++) {
            double angle = initialAngle + (360.0 / count) * i;
            double x = (radius * Math.cos(Math.toRadians(angle)));
            double y = (radius * Math.sin(Math.toRadians(angle)));
            var vertex = new VertexDataModel((int) (centerX + x), (int) (centerY + y), String.valueOf(index++));
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

    @Override
    protected void creationOfGraph() {
        // do nothing because of callback of dialog
    }

    @Override
    public void create() {
        parametersDialog.show();
    }
}
