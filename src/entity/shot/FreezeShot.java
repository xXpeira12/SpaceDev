package entity.shot;

import entity.bomb.BigBomb;
import entity.bomb.Bomb;

import java.util.Timer;
import java.util.TimerTask;

public class FreezeShot extends BigShot{
    private int reduceSpeed;
    public FreezeShot(int posX, int posY, int reduceSpeed) {
        super(posX, posY);
        this.reduceSpeed = reduceSpeed;
        setDamage(1);
    }

    @Override
    public void dealDamage(Bomb bomb) {
        bomb.setHealth(bomb.getHealth() - getDamage());
        slowTarget(bomb);
    }

    public void slowTarget(Bomb bomb) {
        bomb.setSpeed((int) (bomb.getSpeed() - reduceSpeed));
        if(bomb.getSpeed() <= 0) bomb.setSpeed(1);
        bomb.update();
    }
}
