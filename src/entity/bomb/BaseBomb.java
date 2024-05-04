package entity.bomb;

import javafx.scene.image.Image;

import static application.GamePlay.*;
import static config.Config.BASE_BOMB_BASE_SPEED;
import static config.Config.BASE_BOMB_SPEED_FACTOR;

public class BaseBomb extends Bomb {
    public BaseBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
    }

    @Override
    protected int calculateSpeed() {
        return (score / BASE_BOMB_SPEED_FACTOR) + BASE_BOMB_BASE_SPEED;
    }
}
