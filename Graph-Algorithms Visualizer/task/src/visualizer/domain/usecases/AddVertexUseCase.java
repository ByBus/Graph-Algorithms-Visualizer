package visualizer.domain.usecases;

import visualizer.MainFrame;
import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.domain.Command;
import visualizer.presenter.Dialog;
import visualizer.presenter.GraphCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AddVertexUseCase implements Command {
    private final Dialog inputIndexDialog;
    private final Graph graph;

    public AddVertexUseCase(Dialog inputIndexDialog, Graph graph) {
        this.inputIndexDialog = inputIndexDialog;
        this.graph = graph;
    }

    @Override
    public void execute(Component component, MouseEvent event) {
        if (!(component instanceof GraphCanvas)) return;
        int x = event.getX() - MainFrame.VERTEX_RADIUS;
        int y = event.getY() - MainFrame.VERTEX_RADIUS;
        inputIndexDialog.addCallBack(new Dialog.Callback() {
            @Override
            public void onSuccess(String index) {
                graph.addVertex(new VertexDataModel(x, y, index));
            }

            @Override
            public void onFailed() {
                inputIndexDialog.show();
            }

            @Override
            public void onCancel() { }
        });
        inputIndexDialog.show();
    }

    @Override
    public void reset() {

    }
}
