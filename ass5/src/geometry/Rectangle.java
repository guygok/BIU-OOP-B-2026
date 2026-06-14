package src.geometry;
import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    public List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<Point>();
        Line[] edges = this.getEdges();
        for (int i = 0; i < edges.length; i++) {
            Point p = edges[i].intersectionWith(line);
            if (p != null && !containsPoint(intersections, p)) intersections.add(p);
        }
        return intersections;
    }

    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }
    public Point getUpperLeft() { return this.upperLeft; }
    public double getLeft() { return this.upperLeft.getX(); }
    public double getRight() { return this.upperLeft.getX() + this.width; }
    public double getTop() { return this.upperLeft.getY(); }
    public double getBottom() { return this.upperLeft.getY() + this.height; }

    public Line[] getEdges() {
        Point topLeft = this.upperLeft;
        Point topRight = new Point(this.getRight(), this.getTop());
        Point bottomLeft = new Point(this.getLeft(), this.getBottom());
        Point bottomRight = new Point(this.getRight(), this.getBottom());
        return new Line[] { new Line(topLeft, topRight), new Line(topRight, bottomRight), new Line(bottomRight, bottomLeft), new Line(bottomLeft, topLeft) };
    }

    private boolean containsPoint(List<Point> points, Point p) {
        for (Point point : points) {
            if (point.equals(p)) return true;
        }
        return false;
    }
}