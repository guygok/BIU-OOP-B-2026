package src.collision;
import src.geometry.Rectangle;
import src.geometry.Point;
import src.geometry.Velocity;
import src.sprites.Ball;

public interface Collidable {
    Rectangle getCollisionRectangle();
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}