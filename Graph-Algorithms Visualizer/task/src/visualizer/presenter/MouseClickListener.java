package visualizer.presenter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public interface MouseClickListener extends MouseListener {

    @Override
    default void mouseReleased(MouseEvent e) { }

    @Override
    default void mouseEntered(MouseEvent e) { }

    @Override
    default void mouseExited(MouseEvent e) { }
}
