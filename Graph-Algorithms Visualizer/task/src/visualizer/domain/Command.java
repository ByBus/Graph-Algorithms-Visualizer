package visualizer.domain;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Command{
    void reset();
    void execute(Component component, MouseEvent event);
}
