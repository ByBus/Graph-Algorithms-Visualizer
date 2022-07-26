package visualizer.presenter;

import visualizer.data.AxisHolder;
import visualizer.data.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class ArrowHead {
    private final Line2D.Float parenLine;

    public ArrowHead(Line2D.Float parenLine) {
        this.parenLine = parenLine;
    }

    public Shape head() {
        Vector2D vertical = new Vector2D(0, 1);
        Vector2D start = new Vector2D(parenLine.x1, parenLine.y1);
        Vector2D end = new Vector2D(parenLine.x2, parenLine.y2);
        Vector2D lineVector = start.minus(end);
        Point arrowPosition = lineVector.invert().withModulus(27).plus(start).toPoint();
        Point[] arrowPath = {new Point(arrowPosition.x-5,arrowPosition.y - 25),
                arrowPosition,
                new Point(arrowPosition.x+5, arrowPosition.y - 25)};
        int yHandle = arrowPath[0].y + 4;
        int xDeltaHandle = 5;
        Point[] curveHandles = {new Point(arrowPath[0].x + xDeltaHandle, yHandle), new Point(arrowPath[2].x - xDeltaHandle, yHandle)};

        Path2D p = new GeneralPath();
        p.moveTo(arrowPath[0].getX(), arrowPath[0].getY());
        p.lineTo(arrowPath[1].getX(), arrowPath[1].getY());
        p.lineTo(arrowPath[2].getX(), arrowPath[2].getY());
        p.curveTo(curveHandles[1].getX(), curveHandles[1].getY(),
                curveHandles[0].getX(), curveHandles[0].getY(),
                arrowPath[0].getX(), arrowPath[0].getY());
        p.closePath();
        AffineTransform transform = new AffineTransform();
        var k = start.x <= end.x ? 1 : -1;
        transform.rotate(k * lineVector.angleBetweenRad(vertical), arrowPosition.x, arrowPosition.y);
        p.transform(transform);
        return p;
    }
}
