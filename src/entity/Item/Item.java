package entity.Item;

import entity.Entity;
import entity.rocket.Rocket;
import javafx.scene.image.Image;

import static application.Main.*;

public abstract class Item extends Entity {
    public Item(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    @Override
    public void update() {
        setPosY(getPosY() + ITEM_DROP_SPEED);
        if (getPosY() > HEIGHT) setDestroyed(true);
    }

    @Override
    public void draw() {
        if (!isDestroyed()) {
            gc.drawImage(getImg(), getPosX(), getPosY(), getSize(), getSize());
        }
    }

    public abstract boolean collide(Rocket rocket);
}