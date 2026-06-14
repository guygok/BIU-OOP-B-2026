package src.geometry;

public class Point {
    private static final double EPSILON = 0.00001;
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        if (other == null) return 0;
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean equals(Point other) {
        if (other == null) return false;
        return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }
}