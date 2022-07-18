package visualizer.domain;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Controller {
    private Command command;

    public void setCommand(Command command) {
        if (this.command != null) this.command.reset();
        this.command = command;
    }

    public void execute(Component component, MouseEvent event) {
        command.execute(component, event);
    }
}
