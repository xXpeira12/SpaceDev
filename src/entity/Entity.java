package entity;

import ability.Drawable;
import ability.Updatable;
import javafx.scene.image.Image;

public abstract class Entity implements Drawable {
    protected int posX;
    protected int posY;
    protected int size;
    protected boolean exploding;
    protected boolean destroyed;
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
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
