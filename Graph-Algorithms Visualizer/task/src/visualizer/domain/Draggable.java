package visualizer.domain;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public interface Draggable extends ComponentListener {
    void setClickCoordinates(Point clickCoordinates);

    Point getClickCoordinates();

    Container container();

    int width();

    int height();

    Rectangle boundingBox();

    Point position();

    void setPosition(Point point);

    @Override
    default void componentResized(ComponentEvent e) {
    }

    @Override
    default void componentShown(ComponentEvent e) {
    }

    @Override
    default void componentHidden(ComponentEvent e) {
    }
}
