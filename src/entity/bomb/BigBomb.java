package entity.bomb;

import javafx.scene.image.Image;

import static application.Main.*;
import static config.Config.BIG_BOMB_BASE_SPEED;
import static config.Config.BIG_BOMB_SPEED_FACTOR;

public class BigBomb extends Bomb {

    public BigBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
    }

    @Override
    protected int calculateSpeed() {
        return (score / BIG_BOMB_SPEED_FACTOR) + BIG_BOMB_BASE_SPEED;
    }

}
