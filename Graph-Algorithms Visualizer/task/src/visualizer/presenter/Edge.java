package visualizer.presenter;

import visualizer.data.AxisHolder;
import visualizer.domain.SelectState;
import visualizer.domain.Selectable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Edge extends JComponent implements Selectable, RefreshableComponent {
    private static final int LINE_WIDTH = 4;
    private static final int BOUNDS_WIDTH = 16;
    public final VertexUI start;
    public final VertexUI end;
    public final int weight;
    private EdgeLabel label = null;
    private SelectState selectState = SelectState.DEFAULT;
    private boolean isDirected = false;

    public Edge(VertexUI start, VertexUI end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        init();
    }

    public Edge(VertexUI start, VertexUI end, int weight, boolean directed) {
        this(start, end, weight);
        this.isDirected = directed;
    }

    public Edge(VertexUI start, VertexUI end, SelectState state) {
        this(start, end, 0);
        this.selectState = state;
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
        setBounds(xAxis.first() - BOUNDS_WIDTH / 2,
                yAxis.first() - BOUNDS_WIDTH / 2,
                xAxis.delta() + BOUNDS_WIDTH,
                yAxis.delta() + BOUNDS_WIDTH
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
        BasicStroke stroke = new BasicStroke(
                LINE_WIDTH,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        g2.setColor((selectState == SelectState.SELECTED) ? Style.SELECTION_COLOR :
                (selectState == SelectState.DEFAULT) ? Style.LINE_COLOR : Style.LINE_COLOR_HIGHLIGHTED);
        var line = createLine();
        g2.draw(line);
        if (isDirected) {
            var arrowHead = new ArrowHead(line).head();
            g2.draw(arrowHead);
            g2.fill(arrowHead);
        }
    }

    private Line2D.Float createLine() {
        AxisHolder xAxis = new AxisHolder(BOUNDS_WIDTH / 2, getWidth() -  BOUNDS_WIDTH / 2);
        AxisHolder yAxis = new AxisHolder(BOUNDS_WIDTH / 2, getHeight() -  BOUNDS_WIDTH / 2);
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
