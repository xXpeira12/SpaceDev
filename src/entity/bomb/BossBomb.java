package entity.bomb;

import entity.rocket.Rocket;
import entity.shot.BaseShot;
import javafx.scene.image.Image;
import entity.shot.Shot;

import java.util.ArrayList;
import java.util.List;

import static application.Main.*;

public class BossBomb extends Bomb {

    private int speed = (score / 50) + 2;
    private long lastShotTime;
    private List<BaseShot> shots = new ArrayList<>();

    public BossBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
    }

    public void update() {
        if (isExploding()) setExplosionsStep(getExplosionsStep() + 1);
        setDestroyed(getExplosionsStep() > EXPLOSION_STEPS);
        if (!isExploding() && !isDestroyed()) setPosY(getPosY() + this.speed);
        if (getPosY() > HEIGHT) setDestroyed(true);
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public List<BaseShot> getShots() {
        return shots;
    }

    public void shoot() {
        // Create and add a new BaseShot to shots
        shots.add(new BaseShot(this.getPosX(), this.getPosY()));
    }


}
