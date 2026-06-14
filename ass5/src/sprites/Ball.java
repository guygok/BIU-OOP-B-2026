package src.sprites;
import java.awt.Color;
import biuoop.DrawSurface;
import src.collision.CollisionInfo;
import src.collision.Collidable;
import src.geometry.Line;
import src.geometry.Point;
import src.geometry.Rectangle;
import src.geometry.Velocity;
import src.game.Game;
import src.game.GameEnvironment;

public class Ball implements Sprite {
    private static final double EPSILON = 0.1;
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;

    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = Math.max(1, r);
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.environment = new GameEnvironment();
    }

    public int getX() { return (int) Math.round(this.center.getX()); }
    public int getY() { return (int) Math.round(this.center.getY()); }
    public int getSize() { return this.radius; }
    public Color getColor() { return this.color; }
    public void setColor(Color color) { this.color = color; }

    public void setGameEnvironment(GameEnvironment environment) { this.environment = environment; }
    public void setVelocity(Velocity v) { this.velocity = (v == null) ? new Velocity(0, 0) : v; }
    public void setVelocity(double dx, double dy) { this.velocity = new Velocity(dx, dy); }
    public Velocity getVelocity() { return this.velocity; }

    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    public void moveOneStep() {
        if (this.velocity == null) return;

        if (this.environment.getCollidables() != null) {
            for (Collidable c : this.environment.getCollidables()) {
                Rectangle rect = c.getCollisionRectangle();
                if (this.center.getX() > rect.getLeft() && this.center.getX() < rect.getRight()
                        && this.center.getY() > rect.getTop() && this.center.getY() < rect.getBottom()) {
                    this.center = new Point(this.center.getX(), rect.getTop() - this.radius - EPSILON);
                    if (this.velocity.getDy() > 0) {
                        this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
                    }
                    return;
                }
            }
        }

        Point end = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(this.center, end);
        CollisionInfo collision = this.environment.getClosestCollision(trajectory);

        if (collision == null) {
            this.center = end;
            return;
        }

        Point collisionPoint = collision.collisionPoint();
        double dx = this.velocity.getDx(), dy = this.velocity.getDy();
        double speed = Math.sqrt(dx * dx + dy * dy);

        if (speed != 0) {
            double newX = collisionPoint.getX() - (dx / speed) * EPSILON;
            double newY = collisionPoint.getY() - (dy / speed) * EPSILON;
            this.center = new Point(newX, newY);
        }

        this.velocity = collision.collisionObject().hit(this, collisionPoint, this.velocity);
    }

    public void timePassed() { this.moveOneStep(); }

    public void addToGame(Game g) {
        g.addSprite(this);
        this.setGameEnvironment(g.getEnvironment());
    }

    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}