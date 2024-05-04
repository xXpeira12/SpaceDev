package entity.shot;

import ability.Drawable;
import ability.Updatable;
import entity.bomb.Bomb;
import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static application.Main.gc;

public abstract class Shot extends Entity implements Drawable, Updatable {
    protected boolean toRemove;
    protected int speed;
    protected int damage;

    public Shot(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public abstract boolean collide(Bomb bomb);

    public abstract void dealDamage(Bomb bomb);

    public boolean isToRemove() {
        return toRemove;
    }


    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }


    public int getSpeed() {
        return speed;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void draw() {
        gc.setFill(Color.RED);
        gc.fillOval(getPosX(), getPosY(), getSize(), getSize());
    }

    @Override
    public void update() {
        setPosY(getPosY() - getSpeed());
    }
}
