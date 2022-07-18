package visualizer.domain.usecases;

import visualizer.domain.Command;
import visualizer.data.Graph;
import visualizer.presenter.VertexUI;
import visualizer.presenter.Dialog;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CreateEdgeUseCase implements Command {
    private final Dialog inputWeightDialog;
    private final Graph graph;
    private final Set<VertexUI> buffer = new LinkedHashSet<>();

    public CreateEdgeUseCase(Dialog inputWeightDialog, Graph graph) {
        this.inputWeightDialog = inputWeightDialog;
        this.graph = graph;
    }

    @Override
    public void execute(Component component, MouseEvent event) {
        if (!(component instanceof VertexUI)) return;
        VertexUI vertex = (VertexUI) component;
        boolean isAdded = buffer.add(vertex);
        select(vertex, isAdded);
        if (buffer.size() == 2) {
            inputWeightDialog.addCallBack(new Dialog.Callback() {
                @Override
                public void onSuccess(String weight) {
                    List<VertexUI> edge = new ArrayList<>(buffer);
                    graph.addEdge(edge.get(0).toDataModel(), edge.get(1).toDataModel(), Integer.parseInt(weight));
                    reset();
                }

                @Override
                public void onFailed() {
                    inputWeightDialog.show();
                }

                @Override
                public void onCancel() {
                    reset();
                }
            });
            inputWeightDialog.show();
        }
    }

    private void select(VertexUI vertex, boolean state) {
        vertex.select(state);
        if (!state) reset();
        refresh(vertex);
    }

    private void refresh(VertexUI vertex) {
        vertex.getParent().revalidate();
        vertex.getParent().repaint();
    }

    @Override
    public void reset() {
        buffer.forEach(v -> {
            v.select(false);
            refresh(v);
        });
        buffer.clear();
    }
}
