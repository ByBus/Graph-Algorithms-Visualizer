package visualizer.presenter;

import visualizer.data.AxisHolder;
import visualizer.domain.SelectState;
import visualizer.domain.Selectable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Edge extends JComponent implements Selectable, RefreshableComponent {
    private static final int LINE_WIDTH = 4;
    public final VertexUI start;
    public final VertexUI end;
    public final int weight;
    private EdgeLabel label = null;
    private SelectState selectState = SelectState.DEFAULT;

    public Edge(VertexUI start, VertexUI end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        init();
    }

    public Edge(VertexUI start, VertexUI end, boolean isSelected) {
        this(start, end, 0);
        selectState = isSelected ? SelectState.SELECTED : SelectState.DEFAULT;
    }

    private void init() {
        setOpaque(false);
        setName("Edge <" + start.getIndex() + " -> " + end.getIndex() + ">");
        setLayout(new GridBagLayout());
        createBounds();
    }

    private void createBounds() {
        AxisHolder xAxis = new AxisHolder(start.getXPos(true), end.getXPos(true)).sort();
        AxisHolder yAxis = new AxisHolder(start.getYPos(true), end.getYPos(true)).sort();
        setBounds(xAxis.first() - LINE_WIDTH / 2,
                yAxis.first() - LINE_WIDTH / 2,
                Math.max(LINE_WIDTH, xAxis.delta()),
                Math.max(LINE_WIDTH, yAxis.delta())
        );
    }

    public JLabel label() {
        if (label == null) {
            label = new EdgeLabel(String.valueOf(weight), start, end);
        }
        return label;
    }

    @Override
    public void refresh() {
        createBounds();
        if (label != null) label.refresh();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        refresh();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(LINE_WIDTH));
        g2.setColor((selectState == SelectState.SELECTED) ? Style.SELECTION_COLOR : Style.LINE_COLOR);
        g2.draw(createLine());
    }

    private Line2D.Float createLine() {
        AxisHolder xAxis = new AxisHolder(0, getWidth());
        AxisHolder yAxis = new AxisHolder(0, getHeight());
        if (start.getXPos(true) < end.getXPos(true)) xAxis.swap();
        if (start.getYPos(true) < end.getYPos(true)) yAxis.swap();
        return new Line2D.Float(xAxis.first(), yAxis.first(), xAxis.second(), yAxis.second());
    }

    @Override
    public String toString() {
        return getName() + ", weight =" + weight;
    }

    @Override
    public void select(SelectState state) {
        selectState = state;
    }

    @Override
    public SelectState getSelected() {
        return selectState;
    }

}
