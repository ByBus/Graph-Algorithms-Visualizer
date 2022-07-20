package visualizer.data;

import java.awt.*;

public class Vector2D {
    public final double x;
    public final double y;
    public final double modulus;

    public Vector2D(Point point) {
        this(point.x, point.y);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        this.modulus = Math.sqrt(x * x + y * y);
    }

    public Vector2D plus(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D minus(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D unit() {
        return new Vector2D(x / modulus, y / modulus);
    }

    public Vector2D withModulus(double modulus) {
        Vector2D unit = unit();
        return new Vector2D(unit.x * modulus, unit.y * modulus);
    }

    public Vector2D invert() {
        var kX = Math.cos(Math.PI);
        var kY = Math.sin(Math.PI);
        return new Vector2D(kX * x - kY * y, kY * x + kX * y);
    }

    public Point toPoint() {
        return new Point((int) Math.round(x), (int) Math.round(y));
    }
}
