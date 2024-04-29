package application;

import javafx.scene.image.Image;

import static application.Main.HEIGHT;
import static application.Main.score;

public class Bomb extends Rocket{
    private int SPEED = (score / 5) + 2;

    public Bomb(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public void update() {
        super.update();
        if(!isExploding() && !isDestroyed()) setPosY(getPosY() + this.SPEED);
        if(getPosY() > HEIGHT) setDestroyed(true);
    }
}
