package visualizer.domain;

import visualizer.data.Edge;
import visualizer.data.Graph;
import visualizer.data.Vertex;
import visualizer.presenter.Dialog;
import visualizer.presenter.MouseClickListener;

import java.util.*;

public class CreateEdgeUseCase {
    private final Dialog inputWeightDialog;
    private final Graph graph;
    private Mode mode = Mode.ADD_VERTEX;
    private final Set<Vertex> buffer = new LinkedHashSet<>();

    public CreateEdgeUseCase(Dialog inputWeightDialog, Graph graph) {
        this.inputWeightDialog = inputWeightDialog;
        this.graph = graph;
    }

    public void addVertexListener(Vertex vertex) {
        vertex.addMouseListener((MouseClickListener) e -> {
            if (mode != Mode.ADD_EDGE) return;
            buffer.add(vertex);
            if (buffer.size() == 2) {
                inputWeightDialog.addCallBack(new Dialog.Callback() {
                    @Override
                    public void onSuccess(String weight) {
                        List<Vertex> arr = new ArrayList<>(buffer);
                        Edge edge = new Edge(arr.get(0), arr.get(1), Integer.parseInt(weight));
                        graph.addEdge(edge);
                        buffer.clear();
                    }

                    @Override
                    public void onFailed() {
                        inputWeightDialog.show();
                    }

                    @Override
                    public void onCancel() {
                        buffer.clear();
                    }
                });
                inputWeightDialog.show();
            }
        });
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode != Mode.ADD_EDGE) buffer.clear();
    }
}
