package visualizer.presenter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

@FunctionalInterface
public interface MouseDragListener extends MouseMotionListener {
    @Override
    default void mouseMoved(MouseEvent e) {}
}
