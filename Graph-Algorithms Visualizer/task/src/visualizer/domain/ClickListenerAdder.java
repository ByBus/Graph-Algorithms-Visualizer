package visualizer.domain;

import visualizer.presenter.MouseClickListener;

import java.awt.*;

public class ClickListenerAdder {
    private final Controller clickController;

    public ClickListenerAdder(Controller clickController) {
        this.clickController = clickController;
    }

    public void add(Component component) {
        component.addMouseListener((MouseClickListener) e -> {
            clickController.execute(component, e);
        });
    }
}
