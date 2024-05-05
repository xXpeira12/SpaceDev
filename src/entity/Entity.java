package entity;

import ability.Drawable;
import ability.Updatable;
import entity.bomb.Bomb;
import entity.rocket.Rocket;
import javafx.scene.image.Image;

public abstract class Entity implements Drawable, Updatable {
    protected int posX;
    protected int posY;
    protected int size;
    protected boolean exploding;
    protected boolean isDestroyed;
    protected Image img;

    public Entity(int posX, int posY, int size, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = image;
    }

    public abstract void update();

    public abstract void draw();

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}
