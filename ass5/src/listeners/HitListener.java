package src.listeners;
import src.sprites.Block;
import src.sprites.Ball;

public interface HitListener {
    void hitEvent(Block beingHit, Ball hitter);
}