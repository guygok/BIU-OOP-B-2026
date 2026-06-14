package src.sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

public class SpriteCollection {
    private List<Sprite> sprites;

    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    public void addSprite(Sprite s) { this.sprites.add(s); }
    public void removeSprite(Sprite s) { this.sprites.remove(s); }

    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<Sprite>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}