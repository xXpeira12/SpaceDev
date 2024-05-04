package entity.rocket;

import ability.Drawable;
import ability.Updatable;
import entity.Entity;
import javafx.scene.image.Image;
import shot.BigShot;
import shot.Shot;
import shot.SpeedShot;

import static application.Main.*;

public class Rocket extends Entity implements Drawable, Updatable {
    private int explosionsStep = 0;
    private RocketStatus status = RocketStatus.NORMAL;
    public Rocket(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public Shot shoot() {
        if (status == RocketStatus.NORMAL) {
            return new Shot(this.posX + this.size / 2 - Shot.size / 2, this.posY - Shot.size);
        } else if (status == RocketStatus.BIG) {
            return new BigShot(this.posX + this.size / 2 - SpeedShot.size / 2, this.posY - SpeedShot.size);
        }
        else return null;
//        return new Shot(this.posX + this.size / 2 - Shot.size / 2, this.posY - Shot.size);
//        return new BigShot(this.posX + this.size / 2 - BigShot.size / 2, this.posY - BigShot.size);
//        return new SpreadShot(this.posX + this.size / 2 - SpeedShot.size / 2, this.posY - SpreadShot.size, 4, 20);
    }

    @Override
    public void update() {
        if (isExploding()) setExplosionsStep(getExplosionsStep() + 1);
        setDestroyed(getExplosionsStep() > EXPLOSION_STEPS);
    }

    @Override
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

    public boolean colide(Rocket other) {
        int d = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2,
                other.getPosX() + other.getSize() / 2, other.getPosY() + other.getSize() / 2);
        return d < other.getSize() / 2 + getSize() / 2;
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public int getExplosionsStep() {
        return explosionsStep;
    }

    public void setExplosionsStep(int explosionsStep) {
        this.explosionsStep = explosionsStep;
    }

    public enum RocketStatus {
        NORMAL,
        SPEED,
        BIG,
        SPREAD
    }
    public void setStatus(RocketStatus status) {
        this.status = status;
    }
}
