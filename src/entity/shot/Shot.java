package entity.shot;

import entity.bomb.Bomb;
import entity.Entity;
import entity.rocket.Rocket;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static application.Main.gc;

public abstract class Shot extends Entity {
    private boolean isRemove;
    private int speed;
    private int damage;

    public Shot(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public abstract boolean collide(Bomb bomb);

    public abstract boolean collide(Rocket rocket);

    public abstract void dealDamage(Bomb bomb);

    public boolean isRemove() {
        return isRemove;
    }

    public void setIsRemove(boolean isRemove) {
        this.isRemove = isRemove;
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
