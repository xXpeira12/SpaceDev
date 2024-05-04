package entity.bomb;

import javafx.scene.image.Image;

import static application.Main.*;

public class BaseBomb extends Bomb {
    private int speed = (score / 15) + 2;

    public BaseBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
        setSpeed(speed);
    }
}
