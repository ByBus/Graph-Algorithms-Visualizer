package visualizer;

import visualizer.data.Graph;
import visualizer.data.GraphHistory;
import visualizer.data.Observer;
import visualizer.data.TestGraphData;
import visualizer.data.checker.IndexChecker;
import visualizer.data.checker.MultiChecker;
import visualizer.data.checker.UniqueVertexChecker;
import visualizer.data.checker.WeightChecker;
import visualizer.domain.ClickListenerAdder;
import visualizer.domain.Command;
import visualizer.domain.Controller;
import visualizer.domain.Mode;
import visualizer.domain.algorithm.AlgorithmWorker;
import visualizer.domain.algorithm.AlgorithmWorkerFactory;
import visualizer.domain.algorithm.BreadthFirst;
import visualizer.domain.algorithm.DepthFirst;
import visualizer.domain.usecases.*;
import visualizer.presenter.*;
import visualizer.presenter.Dialog;
import visualizer.presenter.MenuBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame implements Observer {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int VERTEX_RADIUS = 25;
    private final Controller commandController = new Controller();
    private final ClickListenerAdder clickAdder = new ClickListenerAdder(commandController);
    private final GraphCanvas canvas = new GraphCanvas("Graph", clickAdder);
    private final GraphCanvasAdapter adapter = new GraphCanvasAdapter(canvas);
    private final Graph graph = new Graph();
    private final GraphHistory graphHistory = new GraphHistory(graph);

    public MainFrame() {
        super("Graph-Algorithms Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        graph.addObserver(this);

        MenuBar menu = new MenuBar();
        setJMenuBar(menu);

        JLabel progressLabel = new JLabel("333", SwingConstants.CENTER);
        progressLabel.setName("Display");
        add(progressLabel, BorderLayout.SOUTH);

        JLabel currentMode = new JLabel(Mode.ADD_VERTEX.label, SwingConstants.RIGHT);
        currentMode.setName("Mode");
        currentMode.setBorder(new EmptyBorder(0,10,0,10));
        add(currentMode, BorderLayout.NORTH);
        setVisible(true); // update size of inner workspace

        Dialog inputIndexDialog = new Dialog(
                "Enter the Vertex ID (Should be 1 char):",
                "Vertex",
                new MultiChecker(new IndexChecker(), new UniqueVertexChecker(graph)));
        Dialog inputWeightDialog = new Dialog(
            "Enter Weight",
                "Input",
                new WeightChecker()
        );
        LabelMaster progressLabelMaster = new LabelMaster(progressLabel);
        add(canvas);


        Command addVertexUseCase = new AddVertexUseCase(inputIndexDialog, graph);
        Command createEdgeUseCase = new CreateEdgeUseCase(inputWeightDialog, graph);
        Command noneCommand = new NoneUseCase();
        Command removeVertexUseCase = new RemoveVertexUseCase(canvas, graph);
        Command removeEdgeUseCase = new RemoveEdgeUseCase(canvas, graph);
        Command breadthFirstTraverseUseCase = new TraverseGraphUseCase(new AlgorithmWorkerFactory(new BreadthFirst(graph)),
                progressLabelMaster,
                canvas,
                graphHistory);
        Command depthFirstTraverseUseCase = new TraverseGraphUseCase(new AlgorithmWorkerFactory(new DepthFirst(graph)),
                progressLabelMaster,
                canvas,
                graphHistory);

        commandController.setCommand(addVertexUseCase);

        menu.setAddVertexItemAction(e -> {
            currentMode.setText(Mode.ADD_VERTEX.label);
            commandController.setCommand(addVertexUseCase);
        });

        menu.setCreateEdgeItemAction(e -> {
            currentMode.setText(Mode.ADD_EDGE.label);
            commandController.setCommand(createEdgeUseCase);
        });

        menu.setRemoveEdgeItemAction(e -> {
            currentMode.setText(Mode.REMOVE_EDGE.label);
            commandController.setCommand(removeEdgeUseCase);
        });

        menu.setRemoveVertexItemAction(e -> {
            currentMode.setText(Mode.REMOVE_VERTEX.label);
            commandController.setCommand(removeVertexUseCase);
        });

        menu.setNoneItemAction(e -> {
            currentMode.setText(Mode.NONE.label);
            commandController.setCommand(noneCommand);
        });

        menu.setNewItemAction(e -> {
            graphHistory.clear();
            graph.clear();
        });

        menu.setExitItemAction(e -> {
            dispose();
            System.exit(0);
        });

        menu.setBreadthFirstSearchItemAction(e -> {
            currentMode.setText(Mode.NONE.label);
            commandController.setCommand(breadthFirstTraverseUseCase);
            progressLabelMaster.setPrefix("BFS");
            progressLabelMaster.initMessage();
        });

        menu.setDepthFirstSearchItemAction(e -> {
            currentMode.setText(Mode.NONE.label);
            commandController.setCommand(depthFirstTraverseUseCase);
            progressLabelMaster.setPrefix("DFS");
            progressLabelMaster.initMessage();
        });

        setVisible(true);

//        var test = new TestGraphData(graph);
//        test.setup();
    }

    @Override
    public void update(Graph graph) {
        adapter.submitGraph(graph);
    }
}