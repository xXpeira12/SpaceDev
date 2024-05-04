package entity.rocket;

import entity.bomb.*;
import javafx.scene.image.Image;
import entity.shot.*;
import entity.Entity;

import static application.GamePlay.*;
import static config.Config.*;

public class Rocket extends Entity {
    private int explosionsStep = 0;
    private RocketStatus status = RocketStatus.NORMAL;
    private int powerUpTimer = 0;

    public Rocket(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public Shot shoot() {
        if (status == RocketStatus.NORMAL) {
            return new BaseShot(this.posX + this.size / 2 - BASESHOT_SIZE / 2, this.posY - BASESHOT_SIZE);
        } else if (status == RocketStatus.BIG) {
            return new BigShot(this.posX + this.size / 2 - BIGSHOT_SIZE / 2, this.posY - BIGSHOT_SIZE);
        } else if (status == RocketStatus.SPEED) {
            return new SpeedShot(this.posX + this.size / 2 - SPEEDSHOT_SIZE / 2, this.posY - SPEEDSHOT_SIZE);
        } else if (status == RocketStatus.SPREAD) {
            return new SpreadShot(this.posX + this.size / 2 - SPEEDSHOT_SIZE / 2, this.posY - SPEEDSHOT_SIZE, SHOTS_PER_SHOOT, SPACE_BETWEEN_SHOT);
        } else {
            return new FreezeShot(this.posX + this.size / 2 - BIGSHOT_SIZE / 2, this.posY - BIGSHOT_SIZE, REDUCE_SPEED);
        }
    }

    @Override
    public void update() {
        if (isExploding()) setExplosionsStep(getExplosionsStep() + 1);
        setDestroyed(getExplosionsStep() > EXPLOSION_STEPS);

        if (status != RocketStatus.NORMAL) {
            powerUpTimer++;
            if (powerUpTimer >= POWER_UP_DURATION) {
                status = RocketStatus.NORMAL;
                powerUpTimer = 0;
            }
        }
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

    public boolean collide(Bomb other) {
        int d = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2,
                other.getPosX() + other.getSize() / 2, other.getPosY() + other.getSize() / 2);
        return d < other.getSize() / 2 + getSize() / 2;
    }

    public int getExplosionsStep() {
        return explosionsStep;
    }

    public void setExplosionsStep(int explosionsStep) {
        this.explosionsStep = explosionsStep;
    }

    public enum RocketStatus {
        SPEED,
        BIG,
        SPREAD,
        FREEZE,
        NORMAL
    }

    public void setStatus(RocketStatus status) {
        this.status = status;
        powerUpTimer = 0;
    }

    public RocketStatus getStatus() {
        return status;
    }

}
