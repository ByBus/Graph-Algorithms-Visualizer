package visualizer.presenter;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    private final JMenuItem addVertexItem = new MenuItem("Add a Vertex");
    private final JMenuItem createEdgeItem = new MenuItem("Add an Edge");
    private final JMenuItem noneMenuItem = new MenuItem("None");
    public MenuBar() {
        setName("MenuBar");
        JMenu modeMenu = new JMenu("Mode");
        modeMenu.add(addVertexItem);
        modeMenu.add(createEdgeItem);
        modeMenu.add(noneMenuItem);
        add(modeMenu);
    }

    public void setAddVertexItemAction(ActionListener action) {
        addVertexItem.addActionListener(action);
    }

    public void setCreateEdgeItemAction(ActionListener action) {
        createEdgeItem.addActionListener(action);
    }

    public void setNoneItemAction(ActionListener action) {
        noneMenuItem.addActionListener(action);
    }
}
