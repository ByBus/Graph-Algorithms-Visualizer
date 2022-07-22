package visualizer.domain;

import visualizer.presenter.drag.CollisionManager;
import visualizer.presenter.MouseClickListener;
import visualizer.presenter.MouseDragListener;
import visualizer.presenter.drag.Draggable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ClickListenerAdder {
    private final Controller clickController;
    private final CollisionManager collisionManager;

    public ClickListenerAdder(Controller clickController, CollisionManager collisionManager) {
        this.clickController = clickController;
        this.collisionManager = collisionManager;
    }

    public void add(Component component) {
        component.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickController.execute(component, e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (component instanceof Draggable) {
                    collisionManager.onDragStart(((Draggable) component), e.getPoint());
                }
            }
        });

        if (component instanceof Draggable) {
            component.addMouseMotionListener((MouseDragListener) e ->
                    collisionManager.checkCollisions((Draggable) component, e));
        }
    }
}
