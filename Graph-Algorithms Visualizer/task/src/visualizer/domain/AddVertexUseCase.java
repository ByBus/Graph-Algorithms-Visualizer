package visualizer.domain;

import visualizer.MainFrame;
import visualizer.data.Graph;
import visualizer.data.Vertex;
import visualizer.presenter.Dialog;
import visualizer.presenter.MouseClickListener;

import javax.swing.*;

public class AddVertexUseCase {
    private final JPanel canvas;
    private final Dialog inputIndexDialog;
    private final Graph graph;
    private Mode mode = Mode.ADD_VERTEX;

    public AddVertexUseCase(JPanel canvas, Dialog inputIndexDialog, Graph graph) {
        this.canvas = canvas;
        this.inputIndexDialog = inputIndexDialog;
        this.graph = graph;
        addCanvasListener();
    }

    private void addCanvasListener() {
        canvas.addMouseListener((MouseClickListener) e -> {
            if (mode != Mode.ADD_VERTEX) return;
            int x = e.getX() - MainFrame.VERTEX_RADIUS;
            int y = e.getY() - MainFrame.VERTEX_RADIUS;
            inputIndexDialog.addCallBack(new Dialog.Callback() {
                @Override
                public void onSuccess(String index) {
                    graph.addVertex(new Vertex(x, y, MainFrame.VERTEX_RADIUS, index));
                }

                @Override
                public void onFailed() {
                    inputIndexDialog.show();
                }

                @Override
                public void onCancel() { }
            });
            inputIndexDialog.show();
        });
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
