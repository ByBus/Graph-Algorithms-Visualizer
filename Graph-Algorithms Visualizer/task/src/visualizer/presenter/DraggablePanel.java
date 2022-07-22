package visualizer.presenter;

import visualizer.presenter.drag.Draggable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;

abstract class DraggablePanel extends JPanel implements Draggable {
    public int x;
    public int y;
    protected Point clickCoordinates;

    @Override
    public void setClickCoordinates(Point clickCoordinates) {
        this.clickCoordinates = clickCoordinates;
    }

    @Override
    public Point getClickCoordinates() {
        return clickCoordinates;
    }

    @Override
    public Container container() {
        return getParent();
    }

    @Override
    public Rectangle boundingBox() {
        return getBounds();
    }

    @Override
    public Point position() {
        return getLocation();
    }

    @Override
    public void setPosition(Point point) {
        setLocation(point);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        x = e.getComponent().getX();
        y = e.getComponent().getY();
    }
}
