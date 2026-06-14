package src.collision;
import src.geometry.Point;

public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    public Point collisionPoint() { return this.collisionPoint; }
    public Collidable collisionObject() { return this.collisionObject; }
}