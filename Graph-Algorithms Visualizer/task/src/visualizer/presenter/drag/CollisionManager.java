package visualizer.presenter.drag;

import visualizer.MainFrame;
import visualizer.data.Vector2D;
import visualizer.presenter.drag.Draggable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CollisionManager {
    private final static double VERTEX_VIOLATION_DISTANCE = MainFrame.VERTEX_RADIUS * 2.0;
    private final Set<Rectangle> boundingBoxes = new HashSet<>();

    public void onDragStart(Draggable vertex, Point point) {
        vertex.setClickCoordinates(SwingUtilities.convertPoint((Component) vertex, point, vertex.container()));
        updateCollisions(vertex);
    }

    private void updateCollisions(Draggable vertex) {
        boundingBoxes.clear();
        boundingBoxes.addAll(Arrays.stream(vertex.container().getComponents())
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
        Rectangle vertexBounds = vertex.boundingBox();
        Point position = SwingUtilities.convertPoint((Component) vertex, e.getPoint(), vertex.container());
        Point newPosition = vertex.position();
        newPosition.translate(position.x - vertex.getClickCoordinates().x, position.y - vertex.getClickCoordinates().y);
        // Check collision with other components
        boundingBoxes.forEach(rect -> {
            var vertexCenter = new Vector2D(newPosition.x + vertexBounds.width / 2.0, newPosition.y + vertexBounds.height / 2.0);
            var rectCenter = new Vector2D(rect.x + rect.width / 2.0, rect.y + rect.height / 2.0);
            if (isDistanceViolated(vertexCenter, rectCenter)) {
                Point pos = calculatePosition(vertexCenter, rectCenter);
                newPosition.x = pos.x - vertexBounds.width / 2;
                newPosition.y = pos.y - vertexBounds.height / 2;
            }
        });
        // Check window borders
        newPosition.x = limit(newPosition.x, 0, vertex.container().getWidth() - vertexBounds.width);
        newPosition.y = limit(newPosition.y, 0, vertex.container().getHeight() - vertexBounds.height);
        vertex.setPosition(newPosition);
        vertex.setClickCoordinates(position);
    }

    private Point calculatePosition(Vector2D a, Vector2D b) {
        Vector2D c = b.minus(a);
        return c.invert().withModulus(VERTEX_VIOLATION_DISTANCE - c.modulus).plus(a).toPoint();
    }

    private boolean isDistanceViolated(Vector2D a, Vector2D b) {
        return a.minus(b).modulus <= VERTEX_VIOLATION_DISTANCE;
    }
}
