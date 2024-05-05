package entity.bomb;

import entity.shot.BaseShot;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static application.GamePlay.*;
import static config.Config.BOSS_BOMB_BASE_SPEED;
import static config.Config.BOSS_BOMB_SPEED_FACTOR;

public class BossBomb extends Bomb {

    private List<BaseShot> shots = new ArrayList<>();

    public BossBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
    }

    public List<BaseShot> getShots() {
        return shots;
    }

    public void shot() {
        // Create and add a new BaseShot to shots
        shots.add(new BaseShot(this.getPosX(), this.getPosY()));
    }

    @Override
    protected int calculateSpeed() {
        return (score / BOSS_BOMB_SPEED_FACTOR) + BOSS_BOMB_BASE_SPEED;
    }
}
