package entity.Item;

import ability.Drawable;
import entity.Entity;
import entity.rocket.Rocket;
import javafx.scene.image.Image;

import static application.Main.*;
public class DropItem extends Entity implements Drawable {
    private final int SPEED = 10;
    public DropItem(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    @Override
    public void update() {
        setPosY(getPosY() + SPEED);
        if(getPosY() > HEIGHT) setDestroyed(true);
    }

    @Override
    public void draw() {
        if (!isDestroyed()) {
            gc.drawImage(getImg(), getPosX(), getPosY(), getSize(), getSize());
        }
    }

    public boolean collide(Rocket rocket) {
        int d = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2,
                rocket.getPosX() + rocket.getSize() / 2, rocket.getPosY() + rocket.getSize() / 2);
        if (d < rocket.getSize() / 2 + getSize() / 2) {
            setDestroyed(true);
            player.setStatus(Rocket.RocketStatus.BIG);
            return true;
        }
        return false;
    }

    public int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}