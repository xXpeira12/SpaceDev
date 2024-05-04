package entity.bomb;

import javafx.scene.image.Image;

import static application.Main.*;

public class BigBomb extends Bomb {
    private int speed = (score / 35) + 2;

    public BigBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
        setSpeed(speed);
    }
}
