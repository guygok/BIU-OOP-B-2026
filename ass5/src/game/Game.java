package src.game;
import java.awt.Color;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import src.collision.Collidable;
import src.geometry.Point;
import src.geometry.Rectangle;
import src.geometry.Velocity;
import src.listeners.BlockRemover;
import src.listeners.BallRemover;
import src.listeners.ScoreTrackingListener;
import src.sprites.Ball;
import src.sprites.Block;
import src.sprites.Paddle;
import src.sprites.ScoreIndicator;
import src.sprites.Sprite;
import src.sprites.SpriteCollection;

public class Game {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private biuoop.KeyboardSensor keyboard;

    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter score;

    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Ass5 Game", WIDTH, HEIGHT);
        this.keyboard = this.gui.getKeyboardSensor();

        this.blocksCounter = new Counter(0);
        this.ballsCounter = new Counter(0);
        this.score = new Counter(0);
    }

    public GameEnvironment getEnvironment() { return this.environment; }
    public void addCollidable(Collidable c) { this.environment.addCollidable(c); }
    public void addSprite(Sprite s) { this.sprites.addSprite(s); }
    public void removeCollidable(Collidable c) { this.environment.removeCollidable(c); }
    public void removeSprite(Sprite s) { this.sprites.removeSprite(s); }

    public void initialize() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);

        createBordersAndDeathRegion();
        createBlocks();
        createBalls();
        createPaddle();
    }

    private void createBordersAndDeathRegion() {
        int thickness = 20;

        new Block(new Rectangle(new Point(0, 20), WIDTH, thickness), Color.GRAY).addToGame(this);
        new Block(new Rectangle(new Point(0, 20), thickness, HEIGHT), Color.GRAY).addToGame(this);
        new Block(new Rectangle(new Point(WIDTH - thickness, 20), thickness, HEIGHT), Color.GRAY).addToGame(this);

        Block deathRegion = new Block(new Rectangle(new Point(0, HEIGHT + 10), WIDTH, thickness), Color.MAGENTA);
        BallRemover ballRemover = new BallRemover(this, this.ballsCounter);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
    }

    private void createBlocks() {
        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this.score);

        Color[] colors = new Color[] { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.PINK };
        int blockWidth = 50, blockHeight = 25;
        int startX = 500, startY = 100;
        int rows = 6;

        for (int row = 0; row < rows; row++) {
            int blocksInRow = rows - row;
            for (int col = 0; col < blocksInRow; col++) {
                int x = startX - col * blockWidth;
                int y = startY + row * blockHeight;

                Block block = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), colors[row % colors.length]);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreListener);
                block.addToGame(this);
                this.blocksCounter.increase(1);
            }
        }
    }

    private void createBalls() {
        Ball b1 = new Ball(new Point(390, 520), 5, Color.WHITE);
        b1.setVelocity(Velocity.fromAngleAndSpeed(45, 5));
        b1.addToGame(this);
        this.ballsCounter.increase(1);

        Ball b2 = new Ball(new Point(420, 520), 5, Color.WHITE);
        b2.setVelocity(Velocity.fromAngleAndSpeed(315, 5));
        b2.addToGame(this);
        this.ballsCounter.increase(1);
    }

    private void createPaddle() {
        Paddle paddle = new Paddle(this.keyboard, new Rectangle(new Point(350, 560), 100, 15), Color.YELLOW);
        paddle.addToGame(this);
    }

    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (this.blocksCounter.getValue() > 0 && this.ballsCounter.getValue() > 0) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = this.gui.getDrawSurface();
            d.setColor(Color.BLUE);
            d.fillRectangle(0, 0, WIDTH, HEIGHT);

            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) sleeper.sleepFor(milliSecondLeftToSleep);
        }

        if (this.blocksCounter.getValue() == 0) {
            this.score.increase(100);
            System.out.println("You Win!\nYour score is: " + this.score.getValue());
        } else {
            System.out.println("Game Over.\nYour score is: " + this.score.getValue());
        }

        this.gui.close();
    }
}