package visualizer.domain.usecases;

import visualizer.data.Graph;
import visualizer.domain.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

abstract class RemoveUseCase implements Command {
    protected final JPanel canvas;
    protected final Graph graph;

    public RemoveUseCase(JPanel canvas, Graph graph) {
        this.canvas = canvas;
        this.graph = graph;
    }

    private void refresh() {
        canvas.revalidate();
        canvas.repaint();
    }

    protected abstract void removeComponent(Component component);

    @Override
    public void execute(Component component, MouseEvent event) {
        removeComponent(component);
        refresh();
    }

    @Override
    public void reset() {

    }
}
