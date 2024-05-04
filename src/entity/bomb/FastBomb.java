package entity.bomb;

import javafx.scene.image.Image;

import static application.Main.*;

public class FastBomb extends Bomb {

    public FastBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
    }

    @Override
    protected int calculateSpeed() {
        return (score / FAST_BOMB_SPEED_FACTOR) + FAST_BOMB_BASE_SPEED;
    }
}
