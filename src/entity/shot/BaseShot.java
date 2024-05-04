package entity.shot;

import entity.bomb.Bomb;
import entity.rocket.Rocket;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static application.Main.BASE_SHOT_SIZE;
import static application.Main.gc;

public class BaseShot extends Shot {
    public BaseShot(int posX, int posY) {
        super(posX, posY, BASE_SHOT_SIZE, null);
        setSpeed(10);
        setDamage(2);
        setSize(6);
    }

    @Override
    public boolean collide(Bomb bomb) {
        int distance = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2, bomb.getPosX() + bomb.getSize() / 2, bomb.getPosY() + bomb.getSize() / 2);
        return distance < bomb.getSize() / 2 + getSize() / 2;
    }

    @Override
    public boolean collide(Rocket rocket) {
        int distance = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2, rocket.getPosX() + rocket.getSize() / 2, rocket.getPosY() + rocket.getSize() / 2);
        return distance < rocket.getSize() / 2 + getSize() / 2;
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public void drawBombShot() {
        gc.setFill(Color.PURPLE);
        gc.fillOval(getPosX() + 30, getPosY() + 30, getSize(), getSize());
    }

    public void updateBombShot() {
        setPosY(getPosY() + getSpeed() + 4);
    }

    @Override
    public void dealDamage(Bomb bomb) {
        bomb.setHealth(bomb.getHealth() - getDamage());
    }
}
