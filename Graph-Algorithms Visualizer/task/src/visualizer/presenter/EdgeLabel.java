package visualizer.presenter;

import visualizer.data.AxisHolder;
import visualizer.domain.SelectState;
import visualizer.domain.Selectable;

import javax.swing.*;
import java.awt.*;

public class EdgeLabel extends JLabel implements RefreshableComponent, Selectable {
    public final VertexUI start;
    public final VertexUI end;
    private final Font font = new Font("Courier", Font.BOLD, 14);
    private SelectState selectState = SelectState.DEFAULT;

    public EdgeLabel(String text, VertexUI start, VertexUI end) {
        super(text);
        this.start = start;
        this.end = end;
        init();
        refresh();
    }

    private void init() {
        setName("EdgeLabel <" + start.getIndex() + " -> " + end.getIndex() + ">");
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(font);
        setOpaque(true);
        setForeground(Style.LINE_COLOR);
        setBackground(UIManager.getColor("Panel.background"));
        setBorder(BorderFactory.createLineBorder(Style.LINE_COLOR, 1));
    }

    @Override
    public void refresh() {
        int centerX = (start.getXPos(true) + end.getXPos(true)) / 2;
        int centerY = (start.getYPos(true) + end.getYPos(true)) / 2;
        int labelWidth = getFontMetrics(font).stringWidth(getText()) + 8;
        int labelHeight = getFontMetrics(font).getHeight();
        setBounds(centerX - labelWidth / 2, centerY - labelHeight / 2, labelWidth, labelHeight);
    }

    private double slope() {
        AxisHolder xAxis = new AxisHolder(start.getXPos(true), end.getXPos(true));
        AxisHolder yAxis = new AxisHolder(start.getYPos(true), end.getYPos(true));
        if (xAxis.direction() < 0) {
            xAxis.swap();
            yAxis.swap();
        }
        double slope = (double) (yAxis.second() - yAxis.first()) / (xAxis.second() - xAxis.first());
        return Math.atan(slope);
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

