package src.geometry;

public class Velocity {
    private double dx;
    private double dy;

    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() { return this.dx; }
    public double getDy() { return this.dy; }

    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);
        double dy = -speed * Math.cos(radians);
        return new Velocity(dx, dy);
    }

    public double getSpeed() {
        return Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    }
}