package entity.bomb;

import javafx.scene.image.Image;

import static application.Main.*;

public class FastBomb extends Bomb {
    private int SPEED = (score / 5) + 2;

    public FastBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
    }

    public void update() {
        if (isExploding()) setExplosionsStep(getExplosionsStep() + 1);
        setDestroyed(getExplosionsStep() > EXPLOSION_STEPS);
        if (!isExploding() && !isDestroyed()) setPosY(getPosY() + this.SPEED);
        if (getPosY() > HEIGHT) setDestroyed(true);
    }
}
