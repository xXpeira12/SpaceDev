package entity.bomb;

import entity.Entity;
import javafx.scene.image.Image;

import static application.Main.*;

public abstract class Bomb extends Entity {
    private int explosionsStep = 0;
    private int speed;
    private int health;

    public Bomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img);
        this.health = health;
        this.speed = calculateSpeed();
    }

    public void update() {
        if (isExploding()) setExplosionsStep(getExplosionsStep() + 1);
        setDestroyed(getExplosionsStep() > EXPLOSION_STEPS);
        if (!isExploding() && !isDestroyed()) setPosY(getPosY() + this.speed);
        if (getPosY() > HEIGHT) setDestroyed(true);
    }

    public void draw() {
        if (isExploding()) {
            gc.drawImage(EXPLOSION_IMG, getExplosionsStep() % EXPLOSION_COLS * EXPLOSION_W, (getExplosionsStep() / EXPLOSION_ROWS) * EXPLOSION_H + 1, EXPLOSION_W, EXPLOSION_H, getPosX(), getPosY(), getSize(), getSize());
        } else {
            gc.drawImage(getImg(), getPosX(), getPosY(), getSize(), getSize());
        }
    }

    public void explode() {
        setExploding(true);
        setExplosionsStep(-1);
    }

    public int getExplosionsStep() {
        return explosionsStep;
    }

    public void setExplosionsStep(int explosionsStep) {
        this.explosionsStep = explosionsStep;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    protected abstract int calculateSpeed();
}
