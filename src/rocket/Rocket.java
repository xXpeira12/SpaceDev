package rocket;

import ability.Drawable;
import ability.Updatable;
import shot.BaseShot;
import javafx.scene.image.Image;
import shot.BigShot;
import shot.SpeedShot;
import shot.SpreadShot;

import static application.Main.*;

public class Rocket implements Drawable, Updatable {
    private int posX;
    private int posY;
    private int size;
    private boolean exploding;
    private boolean destroyed;
    private Image img;
    private int explosionsStep = 0;

    public Rocket(int posX, int posY, int size, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = image;
    }

    public BaseShot shoot() {
        return new BaseShot(this.posX + this.size / 2 - BaseShot.size / 2, this.posY - BaseShot.size);
//        return new SpeedShot(this.posX + this.size / 2 - SpeedShot.size / 2, this.posY - SpeedShot.size);
//        return new BigShot(this.posX + this.size / 2 - BigShot.size / 2, this.posY - BigShot.size);
//        return new SpreadShot(this.posX + this.size / 2 - SpeedShot.size / 2, this.posY - SpreadShot.size, 4, 20);
    }

    public void update() {
        if(isExploding()) setExplosionsStep(getExplosionsStep() + 1);
        setDestroyed(getExplosionsStep() > EXPLOSION_STEPS);
    }

    public void draw() {
        if(isExploding()) {
            gc.drawImage(EXPLOSION_IMG, getExplosionsStep() % EXPLOSION_COLS * EXPLOSION_W, (getExplosionsStep() / EXPLOSION_ROWS) * EXPLOSION_H + 1, EXPLOSION_W, EXPLOSION_H, getPosX(), getPosY(), getSize(), getSize());
        } else {
            gc.drawImage(getImg(), getPosX(), getPosY(), getSize(), getSize());
        }
    }

    public boolean colide(Rocket other) {
        int d = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2,
                other.getPosX() + other.getSize() / 2, other.getPosY() + other.getSize() / 2);
        return d < other.getSize() / 2 + getSize() / 2 ;
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public void explode() {
        setExploding(true);
        setExplosionsStep(-1);
    }

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

    public int getExplosionsStep() {
        return explosionsStep;
    }

    public void setExplosionsStep(int explosionsStep) {
        this.explosionsStep = explosionsStep;
    }
}
