package shot;

import ability.Drawable;
import ability.Updatable;
import bomb.Bomb;
import javafx.scene.paint.Color;

import static application.Main.gc;

public abstract class Shot implements Drawable, Updatable {
    protected boolean toRemove;
    protected int posX;
    protected int posY;
    protected int speed;
    protected int damage;
    public static int size;

    public abstract boolean colide(Bomb bomb);

    public abstract void dealDamage(Bomb bomb);

    public boolean isToRemove() {
        return toRemove;
    }

    ;

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    ;

    public int getPosX() {
        return posX;
    }

    ;

    public void setPosX(int posX) {
        this.posX = posX;
    }

    ;

    public int getPosY() {
        return posY;
    }

    ;

    public void setPosY(int posY) {
        this.posY = posY;
    }

    ;

    public int getSpeed() {
        return speed;
    }

    ;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    ;

    public int getDamage() {
        return damage;
    }

    ;

    public void setDamage(int damage) {
        this.damage = damage;
    }

    ;

    public int getSize() {
        return size;
    }

    ;

    public void setSize(int size) {
        this.size = size;
    }

    ;

    public void draw() {
        gc.setFill(Color.RED);
        gc.fillOval(getPosX(), getPosY(), getSize(), getSize());
    }

    public void update() {
        setPosY(getPosY() - getSpeed());
    }
}
