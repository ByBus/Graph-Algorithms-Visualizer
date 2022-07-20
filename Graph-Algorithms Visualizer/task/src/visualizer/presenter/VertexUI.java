package visualizer.presenter;

import visualizer.MainFrame;
import visualizer.data.VertexDataModel;
import visualizer.domain.Selectable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VertexUI extends DraggablePanel implements Selectable, RefreshableComponent {
    private final int diameter = MainFrame.VERTEX_RADIUS * 2;
    private final String index;
    private boolean highLight = false;
    private JLabel label;

    public VertexUI(int x, int y, String index) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.setName("Vertex " + index);
        init();
        refresh();
        addComponentListener(this);
    }

    private void init() {
        setOpaque(false);
        this.label = new JLabel(String.valueOf(index));
        label.setName("VertexLabel " + index);
        setLayout(new BorderLayout());
        Font font = new Font("Courier", Font.BOLD, MainFrame.VERTEX_RADIUS);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(Style.TEXT_COLOR);
        add(label);
    }

    @Override
    public void refresh() {
        setBounds(x, y, diameter, diameter);
    }

    public int getXPos(boolean centered) {
        return centered ? x + diameter / 2 : x;
    }

    public int getYPos(boolean centered) {
        return centered ? y + diameter / 2 : y;
    }

    public String getIndex() {
        return index;
    }

    public void setSelected(boolean value) {
        this.highLight = value;
    }

    @Override //Paints before ui-elements
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor((highLight) ? Style.SELECTION_COLOR : Style.VERTEX_COLOR);
        g.fillOval(1, 1, diameter - 3, diameter - 3);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Style.LINE_COLOR);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(1, 1, diameter - 3, diameter - 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexUI vertex = (VertexUI) o;
        return Objects.equals(index, vertex.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "Vertex " + index;
    }

    @Override
    public void select(boolean value) {
        highLight = value;
    }

    @Override
    public boolean isSelected() {
        return highLight;
    }

    public VertexDataModel toDataModel() {
        return new VertexDataModel(x, y, index);
    }
}