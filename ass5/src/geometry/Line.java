package src.geometry;
import java.util.List;

public class Line {
    private static final double EPSILON = 0.00001;
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public double length() { return this.start.distance(this.end); }
    public Point middle() { return new Point((this.start.getX() + this.end.getX()) / 2.0, (this.start.getY() + this.end.getY()) / 2.0); }
    public Point start() { return this.start; }
    public Point end() { return this.end; }

    public boolean isIntersecting(Line other) { return this.intersectionWith(other) != null; }

    public Point intersectionWith(Line other) {
        if (other == null) return null;
        double x1 = this.start.getX(), y1 = this.start.getY();
        double x2 = this.end.getX(), y2 = this.end.getY();
        double x3 = other.start.getX(), y3 = other.start.getY();
        double x4 = other.end.getX(), y4 = other.end.getY();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (Math.abs(denominator) < EPSILON) {
            if (this.start.equals(other.start) || this.start.equals(other.end)) return this.start;
            if (this.end.equals(other.start) || this.end.equals(other.end)) return this.end;
            return null;
        }

        double px = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator;
        double py = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator;

        Point p = new Point(px, py);
        if (this.isPointOnSegment(p) && other.isPointOnSegment(p)) return p;
        return null;
    }

    public boolean equals(Line other) {
        if (other == null) return false;
        return (this.start.equals(other.start) && this.end.equals(other.end)) || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        if (rect == null) return null;
        List<Point> points = rect.intersectionPoints(this);
        if (points.isEmpty()) return null;

        Point closest = points.get(0);
        double minDistance = this.start.distance(closest);
        for (int i = 1; i < points.size(); i++) {
            double distance = this.start.distance(points.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                closest = points.get(i);
            }
        }
        return closest;
    }

    private boolean isPointOnSegment(Point p) {
        double minX = Math.min(this.start.getX(), this.end.getX()) - EPSILON;
        double maxX = Math.max(this.start.getX(), this.end.getX()) + EPSILON;
        double minY = Math.min(this.start.getY(), this.end.getY()) - EPSILON;
        double maxY = Math.max(this.start.getY(), this.end.getY()) + EPSILON;
        return p.getX() >= minX && p.getX() <= maxX && p.getY() >= minY && p.getY() <= maxY;
    }
}