package shot;

import ability.Drawable;
import ability.Updatable;
import javafx.scene.paint.Color;
import rocket.Bomb;
import rocket.Rocket;

import static application.Main.gc;

public class BaseShot extends Shot {
    public static int size;

    public BaseShot(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
        setSpeed(10);
        setDamage(2);
        setSize(6);
    }

    public boolean colide(Rocket rocket) {
        int distance = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2, rocket.getPosX() + rocket.getSize() / 2, rocket.getPosY() + rocket.getSize() / 2);
        return distance < rocket.getSize() / 2 + getSize() / 2;
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    @Override
    public void dealDamage(Rocket rocket) {
        if(rocket instanceof Bomb) {
            ((Bomb) rocket).setHealth(((Bomb) rocket).getHealth() - getDamage());
        }
    }
}
