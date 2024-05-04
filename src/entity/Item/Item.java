package entity.Item;

import ability.Drawable;
import entity.Entity;
import entity.rocket.Rocket;
import javafx.scene.image.Image;

import static application.Main.*;

public abstract class Item extends Entity implements Drawable {
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

    abstract public boolean collide(Rocket rocket);

    public int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}