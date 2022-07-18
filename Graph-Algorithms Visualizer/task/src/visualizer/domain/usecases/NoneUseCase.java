package visualizer.domain.usecases;

import visualizer.domain.Command;

import java.awt.*;
import java.awt.event.MouseEvent;

public class NoneUseCase implements Command {
    @Override
    public void execute(Component component, MouseEvent event) {

    }

    @Override
    public void reset() {

    }
}
