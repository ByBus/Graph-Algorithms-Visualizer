package visualizer.domain.usecases;

import visualizer.data.Graph;
import visualizer.domain.Command;
import visualizer.domain.SelectState;
import visualizer.presenter.VertexUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class CreateEdgeUseCase implements Command {
    private final Dialog inputWeightInputDialog;
    private final Graph graph;
    private final Set<VertexUI> buffer = new LinkedHashSet<>();

    public CreateEdgeUseCase(Dialog inputWeightInputDialog, Graph graph) {
        this.inputWeightInputDialog = inputWeightInputDialog;
        this.graph = graph;
    }

    @Override
    public void execute(Component component, MouseEvent event) {
        if (!(component instanceof VertexUI)) return;
        VertexUI vertex = (VertexUI) component;
        boolean isAdded = buffer.add(vertex);
        select(vertex, isAdded ? SelectState.SELECTED : SelectState.DEFAULT);
        if (buffer.size() == 2) {
            inputWeightInputDialog.addCallBack(new Dialog.Callback() {
                @Override
                public void onSuccess(String parameters) {
                    String[] params = parameters.split(";");
                    List<VertexUI> edge = new ArrayList<>(buffer);
                    if (params[1].equals("true")) {
                        graph.addDirectedEdge(edge.get(0).toDataModel(), edge.get(1).toDataModel(), Integer.parseInt(params[0]));
                    } else {
                        graph.addEdge(edge.get(0).toDataModel(), edge.get(1).toDataModel(), Integer.parseInt(params[0]));
                    }
                    buffer.clear();
                }

                @Override
                public void onFailed() {
                    inputWeightInputDialog.show();
                }

                @Override
                public void onCancel() {
                    reset();
                }
            });
            inputWeightInputDialog.show();
        }
    }

    private void select(VertexUI vertex, SelectState state) {
        vertex.select(state);
        if (state != SelectState.SELECTED) reset();
        refresh(vertex);
    }

    private void refresh(VertexUI vertex) {
        vertex.getParent().revalidate();
        vertex.getParent().repaint();
    }

    @Override
    public void reset() {
        buffer.forEach(v -> {
            v.select(SelectState.DEFAULT);
            refresh(v);
        });
        buffer.clear();
    }
}
