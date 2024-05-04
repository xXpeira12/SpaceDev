package entity.shot;

import entity.bomb.Bomb;
import entity.rocket.Rocket;
import javafx.scene.paint.Color;

import static application.Main.gc;
import static config.Config.*;

public class BaseShot extends Shot {
    public BaseShot(int posX, int posY) {
        super(posX, posY, BASESHOT_SIZE, null);
        setSpeed(BASESHOT_SPEED);
        setDamage(BASESHOT_DAMAGE);
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

    public void drawBombShot() {
        gc.setFill(Color.YELLOW);
        gc.fillOval(getPosX() + PADDING_BOMB, getPosY() + PADDING_BOMB, getSize(), getSize());
    }

    public void updateBombShot() {
        setPosY(getPosY() + getSpeed() + BOSS_SHOT_SPEED);
    }

    @Override
    public void dealDamage(Bomb bomb) {
        bomb.setHealth(bomb.getHealth() - getDamage());
    }
}
