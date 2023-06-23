package utils;

import common.models.Worker;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerSprite {
    private final Worker worker;

    private double targetX;  //к какому x идём

    private double targetY;

    private double x;  //текущий x

    private double y;

    private final Canvas canvas;

    private double velX;  //скорость по x

    private double velY;

    //    private static final int HEIGHT = 113;
    private static final int HEIGHT = 96;

    //    private static final int WIDTH = 79;
    private static final int WIDTH = 96;

    private static final int VELOCITY = 1; // pixels per frame

    //    private static final String IMAGE_LOC = "animation/frame_%02d_delay-0.03s.gif";
//    private static final String IMAGE_LOC = "resources/animation/frame_%02d_delay-0.03s.gif";
    private static final String IMAGE_LOC = "resources/animationRight/lДuft e%04d.bmp";

    //    private static final Image images = new Image(IMAGE_LOC, HEIGHT, WIDTH, false, false);
    private static List<Image> images = new ArrayList<>(); //текущий набор картинок

    private static final List<Image> rightImages = new ArrayList<>();
    private static final List<Image> leftImages = new ArrayList<>();
    private static final List<Image> upImages = new ArrayList<>();
    private static final List<Image> downImages = new ArrayList<>();
    private static final List<Image> attackImages = new ArrayList<>();

    private List<Image> currentImages = attackImages;

    //    private static final int FRAMES = 31;
    private static final int FRAMES = 9;
    private static final int FRAMES_ATTACK = 13;

    private int frameNumber = 0;

    private boolean attack = false;

    static {
        for (int i = 0; i < FRAMES; i++) {
            rightImages.add(new Image("resources/animationRight/lДuft e%04d.bmp".formatted(i)));
            leftImages.add(new Image("resources/animationLeft/lДuft w%04d.bmp".formatted(i)));
            upImages.add(new Image("resources/animationUp/lДuft n%04d.bmp".formatted(i)));
            downImages.add(new Image("resources/animationDown/lДuft s%04d.bmp".formatted(i)));
            attackImages.add(new Image("resources/animationState/attack s%04d.bmp".formatted(i)));
//            System.out.println(IMAGE_LOC.formatted(i));
        }
        images = attackImages;
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
        attack = true;
        if (this.x < targetX) {
            velX = VELOCITY;
        } else {
            velX = -VELOCITY;
        }
        if (this.y < targetY) {
            velY = VELOCITY;
        } else {
            velY = -VELOCITY;
        }
    }

    public void refresh() {
        if ((int) this.targetX != (int) this.x) {
            if (this.velX < 0) {
                currentImages = leftImages;
                this.x = Math.max(this.x + this.velX, this.targetX);
            } else {
                currentImages = rightImages;
                this.x = Math.min(this.x + this.velX, this.targetX);
            }
        } else if ((int) this.targetY != (int) this.y) {
            if (this.velY < 0) {
                currentImages = upImages;
                this.y = Math.max(this.y + this.velY, this.targetY);
            } else {
                currentImages = downImages;
                this.y = Math.min(this.y + this.velY, this.targetY);
            }
        }

        if ((int) this.targetY == (int) this.y && (int) this.targetX == (int) this.x) {
            if (attack) {
                currentImages = attackImages;
                if (frameNumber == FRAMES_ATTACK - 1) {
                    attack = false;
                    frameNumber = 0;
                }
                else {
                    frameNumber = (frameNumber + 1) % FRAMES_ATTACK;
                }
            } else {
                currentImages = attackImages;
                frameNumber = 0;
            }
            return;
        }
        frameNumber = (frameNumber + 1) % FRAMES;
    }

    public void draw() {
        canvas.getGraphicsContext2D().drawImage(currentImages.get(frameNumber), x, y);
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