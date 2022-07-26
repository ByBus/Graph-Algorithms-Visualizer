package visualizer.data;

import java.awt.*;

public class Vector2D implements Comparable<Vector2D>{
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

    public double scalar(Vector2D other) {
        return x * other.x + y * other.y;
    }

    public double angleBetweenRad(Vector2D other) {
        return Math.acos(scalar(other) / (modulus * other.modulus));
    }

    public double dotProduct(Vector2D other) {
        return modulus * other.modulus * Math.cos(angleBetweenRad(other));
    }

    public Vector2D multiplyByNumber(double number) {
        return new Vector2D(x * number, y * number);
    }

    public Point toPoint() {
        return new Point((int) Math.round(x), (int) Math.round(y));
    }

    @Override
    public int compareTo(Vector2D o) {
        return Double.compare(modulus, o.modulus);
    }
}
