package visualizer.domain.usecases;

import visualizer.presenter.Edge;
import visualizer.data.Graph;

import javax.swing.*;
import java.awt.*;

final public class RemoveEdgeUseCase extends RemoveUseCase {
    public RemoveEdgeUseCase(JPanel canvas, Graph graph) {
        super(canvas, graph);
    }

    @Override
    protected void removeComponent(Component component) {
        if (component instanceof Edge) {
            Edge edge = (Edge) component;
            graph.removeEdge(edge.start.toDataModel(), edge.end.toDataModel());
        }
    }
}