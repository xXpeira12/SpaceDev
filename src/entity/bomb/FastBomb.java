package entity.bomb;

import javafx.scene.image.Image;

import static application.Main.*;

public class FastBomb extends Bomb {
    private int speed = (score / 5) + 2;

    public FastBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
        setSpeed(speed);
    }
}
