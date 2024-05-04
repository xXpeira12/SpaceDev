package entity.Item;

import ability.Drawable;
import entity.rocket.Rocket;

import static application.Main.*;

public class BoostShotItem extends Item implements Drawable {
    private final Rocket.RocketStatus STATUS;

    public BoostShotItem(int posX, int posY, int size, int type) {
        super(posX, posY, size, BoostShot_IMG[type]);
        STATUS = Rocket.RocketStatus.values()[type];
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public boolean collide(Rocket rocket) {
        int d = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2,
                rocket.getPosX() + rocket.getSize() / 2, rocket.getPosY() + rocket.getSize() / 2);
        if (d < rocket.getSize() / 2 + getSize() / 2) {
            setDestroyed(true);
            rocket.setStatus(STATUS);
            return true;
        }
        return false;
    }
}
