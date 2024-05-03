package shot;

import ability.Drawable;
import ability.Updatable;
import javafx.scene.paint.Color;
import rocket.Rocket;
import bomb.Bomb;

import static application.Main.gc;
import static application.Main.score;

public class Shot implements Drawable, Updatable {
    public boolean toRemove;
    private int posX = 10;
    private int posY = 10;
    private int speed = 10;
    private int damage =  2;
    public static int size = 5;

    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void update() {
        setPosY(getPosY() - getSpeed());
    }

    public void draw() {
        gc.setFill(Color.RED);
        if(score >= 50 && score <= 70 || score >= 120) {
            gc.setFill(Color.YELLOWGREEN);
            setSpeed(50);
            gc.fillRect(getPosX() - 5, getPosY() - 10, size + 10, size + 30);
        } else {
            gc.fillOval(getPosX(), getPosY(), size, size);
        }
    }

    public boolean colide(Bomb bomb) {
        int distance = distance(getPosX() + size / 2, getPosY() + size / 2, bomb.getPosX() + bomb.getSize() / 2,bomb.getPosY() + bomb.getSize() / 2);
        return distance < bomb.getSize() / 2 + size / 2;
    }

    public boolean collide(Rocket rocket) {
        // This is a simple example. You'll need to replace this with your actual collision detection logic.
        return this.posX == rocket.getPosX() && this.posY == rocket.getPosY();
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
