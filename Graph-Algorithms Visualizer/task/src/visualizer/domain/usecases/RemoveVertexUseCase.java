package visualizer.domain.usecases;

import visualizer.data.Graph;
import visualizer.presenter.VertexUI;

import javax.swing.*;
import java.awt.*;

final public class RemoveVertexUseCase extends RemoveUseCase {
    public RemoveVertexUseCase(JPanel canvas, Graph graph) {
        super(canvas, graph);
    }

    @Override
    protected void removeComponent(Component component) {
        if (component instanceof VertexUI) {
            VertexUI vertex = (VertexUI) component;
            graph.removeVertex(vertex.toDataModel());
        }
    }
}
