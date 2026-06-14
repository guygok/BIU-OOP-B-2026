package src.sprites;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import src.collision.Collidable;
import src.geometry.Point;
import src.geometry.Rectangle;
import src.geometry.Velocity;
import src.listeners.HitListener;
import src.listeners.HitNotifier;
import src.game.Game;

public class Block implements Collidable, Sprite, HitNotifier {
    private static final double EPSILON = 0.00001;
    private Rectangle rectangle;
    private Color color;
    private List<HitListener> hitListeners;

    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
    }

    public Rectangle getCollisionRectangle() { return this.rectangle; }

    public void addHitListener(HitListener hl) { this.hitListeners.add(hl); }
    public void removeHitListener(HitListener hl) { this.hitListeners.remove(hl); }

    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    public boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double left = this.rectangle.getLeft(), right = this.rectangle.getRight();
        double top = this.rectangle.getTop(), bottom = this.rectangle.getBottom();

        if (Math.abs(collisionPoint.getX() - left) < EPSILON || Math.abs(collisionPoint.getX() - right) < EPSILON) dx = -dx;
        if (Math.abs(collisionPoint.getY() - top) < EPSILON || Math.abs(collisionPoint.getY() - bottom) < EPSILON) dy = -dy;

        if (!ballColorMatch(hitter)) {
            hitter.setColor(this.color);
            this.notifyHit(hitter);
        }

        return new Velocity(dx, dy);
    }

    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) Math.round(this.rectangle.getLeft()), (int) Math.round(this.rectangle.getTop()),
                (int) Math.round(this.rectangle.getWidth()), (int) Math.round(this.rectangle.getHeight()));
        d.setColor(Color.BLACK);
        d.drawRectangle((int) Math.round(this.rectangle.getLeft()), (int) Math.round(this.rectangle.getTop()),
                (int) Math.round(this.rectangle.getWidth()), (int) Math.round(this.rectangle.getHeight()));
    }

    public void timePassed() {}

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    public void removeFromGame(Game g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }
}