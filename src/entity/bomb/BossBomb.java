package entity.bomb;

import entity.shot.BaseShot;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static application.Main.*;

public class BossBomb extends Bomb {

    private int speed = (score / 50) + 2;
    private List<BaseShot> shots = new ArrayList<>();

    public BossBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
        setSpeed(speed);
    }

    public List<BaseShot> getShots() {
        return shots;
    }

    public void shot() {
        // Create and add a new BaseShot to shots
        shots.add(new BaseShot(this.getPosX(), this.getPosY()));
    }


}
