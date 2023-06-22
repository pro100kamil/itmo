package utils;

import common.models.Worker;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class WorkerSprite {
    private final Worker worker;

    private double targetX;

    private double targetY;

    private double x;

    private double y;

    private final Canvas canvas;

    private double velX;

    private double velY;

    private static final int HEIGHT = 113;

    private static final int WIDTH = 79;

    private static final int VELOCITY = 1; // pixels per frame

//    private static final String IMAGE_LOC = "animation/frame_%02d_delay-0.03s.gif";
    private static final String IMAGE_LOC = "resources/animation/frame_%02d_delay-0.03s.gif";

    //    private static final Image images = new Image(IMAGE_LOC, HEIGHT, WIDTH, false, false);
    private static final List<Image> images = new ArrayList<>();

    private static final int FRAMES = 31;

    private int frameNumber = 0;

    static {
        for (int i = 0; i < FRAMES; i++) {
            images.add(new Image(IMAGE_LOC.formatted(i)));
        }
    }

    public WorkerSprite(Worker worker, double x, double y, Canvas canvas) {
        this.worker = worker;
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
        this.canvas = canvas;
    }

    public void setTarget(double x, double y) {
        this.targetX = x;
        this.targetY = y;

        double tan = Math.abs(this.x - this.targetX) / Math.abs(this.y - this.targetY);

        this.velY = Math.sqrt(Math.pow(VELOCITY, 2) / (1 + Math.pow(tan, 2)));
        this.velY *= (this.targetY - this.y < 0 ? -1 : 1);

        this.velX = Math.sqrt(Math.pow(VELOCITY, 2) - Math.pow(velY, 2));
        this.velX *= (this.targetX - this.x < 0 ? -1 : 1);
    }

    public void refresh() {
        if ((int) this.targetY != (int) this.y) {
            if (this.velY < 0) {
                this.y = Math.max(this.y + this.velY, this.targetY);
            } else {
                this.y = Math.min(this.y + this.velY, this.targetY);
            }
        }
        if ((int) this.targetX != (int) this.x) {
            if (this.velX < 0) {
                this.x = Math.max(this.x + this.velX, this.targetX);
            } else {
                this.x = Math.min(this.x + this.velX, this.targetX);
            }
        }
        if ((int) this.targetY == (int) this.y && (int) this.targetX == (int) this.x) {
            frameNumber = 0;
            return;
        }
        frameNumber = (frameNumber + 1) % FRAMES;
    }

    public void draw() {
        canvas.getGraphicsContext2D().drawImage(images.get(frameNumber), x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public Worker getWorker() {
        return worker;
    }

    public boolean contains(double x, double y) {
        return (this.x <= x && x <= this.x + WIDTH) && (this.y <= y && y <= this.y + HEIGHT);
    }
}