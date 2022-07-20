package visualizer.presenter;

import visualizer.MainFrame;
import visualizer.data.Vector2D;
import visualizer.domain.Draggable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CollisionManager {
    private final static double VERTEX_VIOLATION_DISTANCE = MainFrame.VERTEX_RADIUS * 2.0;
    private final static double VERTEX_JUMP_BACK_DISTANCE = VERTEX_VIOLATION_DISTANCE + 1;
    private final Set<Rectangle> collisions = new HashSet<>();

    public void onDragStart(Draggable vertex, Point point) {
        vertex.setClickCoordinates(SwingUtilities.convertPoint((Component) vertex, point, vertex.container()));
        updateCollisions(vertex);
    }

    private void updateCollisions(Draggable vertex) {
        collisions.clear();
        collisions.addAll(Arrays.stream(vertex.container().getComponents())
                .filter(c -> c instanceof Draggable && !c.equals(vertex))
                .map(Component::getBounds)
                .collect(Collectors.toSet())
        );
    }

    private <T extends Comparable<? super T>> T limit(T o, T min, T max) {
        if (o.compareTo(min) < 0) return min;
        if (o.compareTo(max) > 0) return max;
        return o;
    }

    public void checkCollisions(Draggable vertex, MouseEvent e) {
        Point position = SwingUtilities.convertPoint((Component) vertex, e.getPoint(), vertex.container());
        Point newPosition = vertex.position();
        newPosition.translate(position.x - vertex.getClickCoordinates().x, position.y - vertex.getClickCoordinates().y);
        // Check collision with other components
        collisions.stream().filter(comp -> comp.intersects(vertex.boundingBox())).forEach(comp -> {
            if (isDistanceViolated(vertex.boundingBox(), comp)) {
                Point pos = calculateNewPosition(vertex.boundingBox(), comp);
                newPosition.x = pos.x - vertex.width() / 2 ;
                newPosition.y = pos.y - vertex.height() / 2;
            }
        });
        // Check window borders
        newPosition.x = limit(newPosition.x, 0, vertex.container().getWidth() - vertex.width());
        newPosition.y = limit(newPosition.y, 0, vertex.container().getHeight() - vertex.height());
        vertex.setPosition(newPosition);
        vertex.setClickCoordinates(position);
    }

    private Point calculateNewPosition(Rectangle vertex, Rectangle other) {
        Vector2D a = new Vector2D(findCenter(vertex));
        Vector2D b = new Vector2D(findCenter(other));
        var c = b.minus(a);
        return c.invert().withModulus(VERTEX_JUMP_BACK_DISTANCE - c.modulus).plus(a).toPoint();
    }

    private boolean isDistanceViolated(Rectangle vert, Rectangle other) {
        return findCenter(other).distance(findCenter(vert)) <= VERTEX_VIOLATION_DISTANCE;
    }

    private Point findCenter(Rectangle rect) {
        return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
    }
}
