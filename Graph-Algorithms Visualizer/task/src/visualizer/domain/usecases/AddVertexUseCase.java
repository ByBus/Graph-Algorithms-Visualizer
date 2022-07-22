package visualizer.domain.usecases;

import visualizer.MainFrame;
import visualizer.data.Graph;
import visualizer.data.VertexDataModel;
import visualizer.domain.Command;
import visualizer.presenter.GraphCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AddVertexUseCase implements Command {
    private final Dialog inputIndexInputDialog;
    private final Graph graph;

    public AddVertexUseCase(Dialog inputIndexInputDialog, Graph graph) {
        this.inputIndexInputDialog = inputIndexInputDialog;
        this.graph = graph;
    }

    @Override
    public void execute(Component component, MouseEvent event) {
        if (!(component instanceof GraphCanvas)) return;
        int x = event.getX() - MainFrame.VERTEX_RADIUS;
        int y = event.getY() - MainFrame.VERTEX_RADIUS;
        inputIndexInputDialog.addCallBack(new Dialog.Callback() {
            @Override
            public void onSuccess(String index) {
                graph.addVertex(new VertexDataModel(x, y, index));
            }

            @Override
            public void onFailed() {
                inputIndexInputDialog.show();
            }

            @Override
            public void onCancel() { }
        });
        inputIndexInputDialog.show();
    }

    @Override
    public void reset() {

    }
}
