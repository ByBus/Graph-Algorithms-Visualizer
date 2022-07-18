package visualizer.presenter;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    private final JMenuItem newItem = new MenuItem("New");
    private final JMenuItem exitItem = new MenuItem("Exit");

    private final JMenuItem addVertexItem = new MenuItem("Add a Vertex");
    private final JMenuItem createEdgeItem = new MenuItem("Add an Edge");
    private final JMenuItem removeVertexItem = new MenuItem("Remove a Vertex");
    private final JMenuItem removeEdgeItem = new MenuItem("Remove an Edge");
    private final JMenuItem noneMenuItem = new MenuItem("None");

    private final JMenuItem depthFirstSearchMenuItem = new MenuItem("Depth-First Search");
    private final JMenuItem breadthFirstSearchMenuItem = new MenuItem("Breadth-First Search");

    public MenuBar() {
        setName("MenuBar");
        createSubmenus();
    }

    private void createSubmenus() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newItem);
        fileMenu.add(exitItem);
        add(fileMenu);

        JMenu modeMenu = new JMenu("Mode");
        modeMenu.add(addVertexItem);
        modeMenu.add(createEdgeItem);
        modeMenu.add(removeVertexItem);
        modeMenu.add(removeEdgeItem);
        modeMenu.add(noneMenuItem);
        add(modeMenu);

        JMenu algorithmMenu = new JMenu("Algorithms");
        algorithmMenu.add(depthFirstSearchMenuItem);
        algorithmMenu.add(breadthFirstSearchMenuItem);
        add(algorithmMenu);
    }

    public void setNewItemAction(ActionListener action) {
        newItem.addActionListener(action);
    }

    public void setExitItemAction(ActionListener action) {
        exitItem.addActionListener(action);
    }

    public void setAddVertexItemAction(ActionListener action) {
        addVertexItem.addActionListener(action);
    }

    public void setCreateEdgeItemAction(ActionListener action) {
        createEdgeItem.addActionListener(action);
    }

    public void setRemoveVertexItemAction(ActionListener action) {
        removeVertexItem.addActionListener(action);
    }

    public void setRemoveEdgeItemAction(ActionListener action) {
        removeEdgeItem.addActionListener(action);
    }

    public void setNoneItemAction(ActionListener action) {
        noneMenuItem.addActionListener(action);
    }

    public void setDepthFirstSearchItemAction(ActionListener action) {
        depthFirstSearchMenuItem.addActionListener(action);
    }

    public void setBreadthFirstSearchItemAction(ActionListener action) {
        breadthFirstSearchMenuItem.addActionListener(action);
    }
}
