package src.sprites;
import biuoop.DrawSurface;

public interface Sprite {
    void drawOn(DrawSurface d);
    void timePassed();
}