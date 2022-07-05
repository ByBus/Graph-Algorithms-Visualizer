package visualizer;

import visualizer.data.*;
import visualizer.domain.AddVertexUseCase;
import visualizer.domain.CreateEdgeUseCase;
import visualizer.domain.Mode;
import visualizer.presenter.Dialog;
import visualizer.presenter.MenuBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame implements Observer {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int VERTEX_RADIUS = 25;
    private final JPanel canvas = new GraphCanvas("Graph");
    private Mode mode = Mode.ADD_VERTEX;
    private AddVertexUseCase addVertexUseCase;
    private CreateEdgeUseCase createEdgeUseCase;

    public MainFrame() {
        super("Graph-Algorithms Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        Graph graph = new Graph();
        graph.addObserver(this);

        MenuBar menu = new MenuBar();
        setJMenuBar(menu);

        JLabel currentMode = new JLabel(mode.label, SwingConstants.RIGHT);
        currentMode.setName("Mode");
        currentMode.setBorder(new EmptyBorder(0,10,0,10));
        add(currentMode, BorderLayout.NORTH);
        setVisible(true); // update size of inner workspace

        Dialog inputIndexDialog = new Dialog(
                "Enter the Vertex ID (Should be 1 char):",
                "Vertex",
                new IndexChecker());
        Dialog inputWeightDialog = new Dialog(
            "Enter Weight",
                "Input",
                new WeightChecker()
        );

        add(canvas);

        addVertexUseCase = new AddVertexUseCase(canvas, inputIndexDialog, graph);
        createEdgeUseCase = new CreateEdgeUseCase(inputWeightDialog, graph);

        menu.setAddVertexItemAction(e -> {
            mode = Mode.ADD_VERTEX;
            currentMode.setText(mode.label);
            updateUseCasesMode();
        });

        menu.setCreateEdgeItemAction(e -> {
            mode = Mode.ADD_EDGE;
            currentMode.setText(mode.label);
            updateUseCasesMode();
        });

        menu.setNoneItemAction(e -> {
            mode = Mode.NONE;
            currentMode.setText(mode.label);
            updateUseCasesMode();
        });
        setVisible(true);
    }

    private void updateUseCasesMode() {
        addVertexUseCase.setMode(mode);
        createEdgeUseCase.setMode(mode);
    }

    @Override
    public void update(JComponent component) {
        if (component instanceof Vertex) {
            createEdgeUseCase.addVertexListener((Vertex) component);
        }
        if (component instanceof Edge) {
            canvas.add(((Edge) component).label());
            canvas.add(((Edge) component).reverse());
        }
        canvas.add(component);

        revalidate();
        repaint();
    }
}