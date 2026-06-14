package src.game;
import java.util.ArrayList;
import java.util.List;
import src.collision.Collidable;
import src.collision.CollisionInfo;
import src.geometry.Line;
import src.geometry.Point;

public class GameEnvironment {
    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    public void addCollidable(Collidable c) { this.collidables.add(c); }
    public void removeCollidable(Collidable c) { this.collidables.remove(c); }
    public List<Collidable> getCollidables() { return this.collidables; }

    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestObject = null;
        double minDistance = Double.MAX_VALUE;

        for (Collidable c : this.collidables) {
            Point p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (p != null) {
                double distance = trajectory.start().distance(p);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = p;
                    closestObject = c;
                }
            }
        }
        if (closestPoint == null) return null;
        return new CollisionInfo(closestPoint, closestObject);
    }
}