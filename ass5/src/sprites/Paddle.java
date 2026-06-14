package src.sprites;
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import src.collision.Collidable;
import src.geometry.Point;
import src.geometry.Rectangle;
import src.geometry.Velocity;
import src.game.Game;

public class Paddle implements Sprite, Collidable {
    private static final int SCREEN_WIDTH = 800;
    private static final double SPEED = 7;
    private KeyboardSensor keyboard;
    private Rectangle rectangle;
    private Color color;

    public Paddle(KeyboardSensor keyboard, Rectangle rectangle, Color color) {
        this.keyboard = keyboard;
        this.rectangle = rectangle;
        this.color = color;
    }

    public void moveLeft() {
        double newX = this.rectangle.getUpperLeft().getX() - SPEED;
        if (newX + this.rectangle.getWidth() < 0) { newX = SCREEN_WIDTH; }
        this.rectangle = new Rectangle(new Point(newX, this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
    }

    public void moveRight() {
        double newX = this.rectangle.getUpperLeft().getX() + SPEED;
        if (newX > SCREEN_WIDTH) { newX = -this.rectangle.getWidth(); }
        this.rectangle = new Rectangle(new Point(newX, this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
    }

    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) { this.moveLeft(); }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) { this.moveRight(); }
    }

    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) Math.round(this.rectangle.getLeft()), (int) Math.round(this.rectangle.getTop()),
                (int) Math.round(this.rectangle.getWidth()), (int) Math.round(this.rectangle.getHeight()));
        d.setColor(Color.BLACK);
        d.drawRectangle((int) Math.round(this.rectangle.getLeft()), (int) Math.round(this.rectangle.getTop()),
                (int) Math.round(this.rectangle.getWidth()), (int) Math.round(this.rectangle.getHeight()));
    }

    public Rectangle getCollisionRectangle() { return this.rectangle; }

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double speed = currentVelocity.getSpeed();
        double regionWidth = this.rectangle.getWidth() / 5.0;
        double relativeX = collisionPoint.getX() - this.rectangle.getLeft();

        if (relativeX < regionWidth) { return Velocity.fromAngleAndSpeed(300, speed); }
        if (relativeX < 2 * regionWidth) { return Velocity.fromAngleAndSpeed(330, speed); }
        if (relativeX < 3 * regionWidth) { return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy())); }
        if (relativeX < 4 * regionWidth) { return Velocity.fromAngleAndSpeed(30, speed); }
        return Velocity.fromAngleAndSpeed(60, speed);
    }

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}