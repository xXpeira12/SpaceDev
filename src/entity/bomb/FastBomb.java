package entity.bomb;

import javafx.scene.image.Image;

import static application.Main.*;
import static config.Config.FAST_BOMB_BASE_SPEED;
import static config.Config.FAST_BOMB_SPEED_FACTOR;

public class FastBomb extends Bomb {

    public FastBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
    }

    @Override
    protected int calculateSpeed() {
        return (score / FAST_BOMB_SPEED_FACTOR) + FAST_BOMB_BASE_SPEED;
    }
}
