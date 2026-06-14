package src.sprites;
import biuoop.DrawSurface;
import java.awt.Color;
import src.game.Counter;
import src.game.Game;

public class ScoreIndicator implements Sprite {
    private Counter score;

    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 20);
        d.setColor(Color.BLACK);
        d.drawText(350, 15, "Score: " + this.score.getValue(), 15);
    }

    public void timePassed() {}

    public void addToGame(Game g) {
        g.addSprite(this);
    }
}