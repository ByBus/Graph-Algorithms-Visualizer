package visualizer;

import visualizer.data.*;
import visualizer.data.checker.IndexChecker;
import visualizer.data.checker.MultiChecker;
import visualizer.data.checker.UniqueVertexChecker;
import visualizer.data.checker.WeightChecker;
import visualizer.domain.*;
import visualizer.domain.algorithm.*;
import visualizer.domain.usecases.*;
import visualizer.domain.usecases.Dialog;
import visualizer.presenter.MenuBar;
import visualizer.presenter.*;
import visualizer.presenter.drag.CollisionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.stream.Collectors;

public class MainFrame extends JFrame implements Observer {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int VERTEX_RADIUS = 25;
    private final Controller commandController = new Controller();
    private final CollisionManager collisionManager = new CollisionManager();
    private final ClickListenerAdder clickAdder = new ClickListenerAdder(commandController, collisionManager);
    private final GraphCanvas canvas = new GraphCanvas("Graph", clickAdder);
    private final GraphCanvasAdapter adapter = new GraphCanvasAdapter(canvas);
    private final Graph graph = new Graph();
    private final GraphHistory<SavableGraph.GraphState> graphHistory = new GraphHistory<>(graph);
    private final TestGraphData presets = new TestGraphData(graph, this);

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
        createPresetsMenu(menu);
        setJMenuBar(menu);

        JLabel progressLabel = new JLabel("333", SwingConstants.CENTER);
        progressLabel.setName("Display");
        add(progressLabel, BorderLayout.SOUTH);

        JLabel currentMode = new JLabel(Mode.ADD_VERTEX.label, SwingConstants.RIGHT);
        currentMode.setName("Mode");
        currentMode.setBorder(new EmptyBorder(0, 10, 0, 10));
        add(currentMode, BorderLayout.NORTH);
        setVisible(true); // update size of inner workspace

        Dialog inputIndexInputDialog = new InputDialog(
                "Enter the Vertex ID (Should be 1 char):",
                "Vertex",
                new MultiChecker(new IndexChecker(), new UniqueVertexChecker(graph)));
        Dialog inputWeightInputDialog = new InputCheckBoxDialog(
                "Enter Weight",
                "Directed",
                "Input",
                new WeightChecker()
        );
        LabelMaster progressLabelMaster = new LabelMaster(progressLabel);
        add(canvas);


        Command addVertexUseCase = new AddVertexUseCase(inputIndexInputDialog, graph);
        Command createEdgeUseCase = new CreateEdgeUseCase(inputWeightInputDialog, graph);
        Command noneCommand = new NoneUseCase();
        Command removeVertexUseCase = new RemoveVertexUseCase(canvas, graph);
        Command removeEdgeUseCase = new RemoveEdgeUseCase(canvas, graph);
        Command breadthFirstTraverseUseCase = new TraverseGraphUseCase(
                new AlgorithmWorkerFactory(new BreadthFirst(graph, new SpanningTree())),
                progressLabelMaster,
                canvas,
                graphHistory);
        Command depthFirstTraverseUseCase = new TraverseGraphUseCase(
                new AlgorithmWorkerFactory(new DepthFirst(graph, new SpanningTree())),
                progressLabelMaster,
                canvas,
                graphHistory);
        Command dijkstraTraverseUseCase = new TraverseGraphUseCase(
                new AlgorithmWorkerFactory(new Dijkstra(graph, new ShortestPath())),
                progressLabelMaster,
                canvas,
                graphHistory);
        Command primTraverseUseCase = new TraverseGraphUseCase(
                new AlgorithmWorkerFactory(new Prims(graph, new MinimumSpanningTree())),
                progressLabelMaster,
                canvas,
                graphHistory);

        commandController.setCommand(addVertexUseCase);

        menu.setAddVertexItemAction(e -> {
            addVertexMode(currentMode, addVertexUseCase);
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
            addVertexMode(currentMode, addVertexUseCase);
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

        menu.setDijkstraSearchMenuItemAction(e -> {
            currentMode.setText(Mode.NONE.label);
            commandController.setCommand(dijkstraTraverseUseCase);
            progressLabelMaster.setPrefix("");
            progressLabelMaster.initMessage();
        });

        menu.setPrimMinimumSpanningTreeMenuItemAction(e -> {
            currentMode.setText(Mode.NONE.label);
            commandController.setCommand(primTraverseUseCase);
            progressLabelMaster.setPrefix("");
            progressLabelMaster.initMessage();
        });

        setVisible(true);
    }

    private void addVertexMode(JLabel currentMode, Command addVertexUseCase) {
        currentMode.setText(Mode.ADD_VERTEX.label);
        commandController.setCommand(addVertexUseCase);
    }

    private void createPresetsMenu(MenuBar menu) {
        Command none = new NoneUseCase();
        Map<String, ActionListener> presetsMenuItems = presets.graphsNames().stream()
                .collect(Collectors.toMap(
                        name -> name,
                        name -> e -> {
                            commandController.setCommand(none);
                            presets.draw(name);
                        },
                        (a, b) -> b)
                );
        menu.createMenu("Presets", presetsMenuItems);
    }

    @Override
    public void update(Graph graph) {
        adapter.submitGraph(graph);
    }
}