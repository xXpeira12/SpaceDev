package shot;

import ability.Drawable;
import ability.Updatable;
import javafx.scene.paint.Color;
import rocket.Rocket;

import static application.Main.gc;
import static application.Main.score;

public class Shot implements Drawable, Updatable {
    public boolean toRemove;
    private int posX = 10;
    private int posY = 10;
    private int speed = 10;
    public static final int size = 6;

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

    public boolean colide(Rocket rocket) {
        int distance = distance(getPosX() + size / 2, getPosY() + size / 2, rocket.getPosX() + rocket.getSize() / 2, rocket.getPosY() + rocket.getSize() / 2);
        return distance < rocket.getSize() / 2 + size / 2;
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
}
