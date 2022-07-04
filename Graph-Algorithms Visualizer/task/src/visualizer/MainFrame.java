package visualizer;

import visualizer.data.GraphObserver;
import visualizer.data.Vertex;
import visualizer.data.Graph;
import visualizer.presenter.Dialog;
import visualizer.presenter.MouseClickListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements GraphObserver {
    private static final int width = 800;
    private static final int height = 600;
    private static final int vertexRadius = 25;
    private final JPanel canvas = new JPanel();

    public MainFrame() {
        super("Graph-Algorithms Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true); // update size of inner workspace
        Dimension size = getContentPane().getSize();

        Dialog inputIndexDialog = new Dialog();
        ObservableGraph graph = new Graph();
        graph.addObserver(this);

        prepareCanvas(size);
        addCanvasListener(inputIndexDialog, graph);
        add(canvas);
    }

    private void addCanvasListener(Dialog inputIndexDialog, ObservableGraph graph) {
        canvas.addMouseListener((MouseClickListener) e -> {
            int x = e.getX() - vertexRadius;
            int y = e.getY() - vertexRadius;
            inputIndexDialog.addCallBack(new Dialog.Callback() {
                @Override
                public void onSuccess(String index) {
                    graph.addVertex(new Vertex(x, y, vertexRadius, index));
                }

                @Override
                public void onFailed() {
                    inputIndexDialog.show();
                }
            });
            inputIndexDialog.show();
        });
    }

    private void prepareCanvas(Dimension size) {
        canvas.setName("Graph");
        canvas.setLayout(null);
        canvas.setBounds(0, 0, size.width, size.height);
    }

    @Override
    public void update(Vertex vertex) {
        canvas.add(vertex);
        revalidate();
    }
}