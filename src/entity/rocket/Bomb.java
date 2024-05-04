package entity.rocket;

import ability.Updatable;
import javafx.scene.image.Image;

import static application.Main.HEIGHT;
import static application.Main.score;

public class Bomb extends Rocket implements Updatable {
    private int speed = (score / 15) + 2;
    private int health = 5;

    public Bomb(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    @Override
    public void update() {
        super.update();
        if(!isExploding() && !isDestroyed()) setPosY(getPosY() + this.speed);
        if(getPosY() > HEIGHT) setDestroyed(true);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
