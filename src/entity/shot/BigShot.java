package entity.shot;

import static config.Config.*;

public class BigShot extends BaseShot {

    public BigShot(int posX, int posY) {
        super(posX, posY);
        setDamage(BIGSHOT_DAMAGE);
        setSpeed(BIGSHOT_SPEED);
        setSize(BIGSHOT_SIZE);
    }
}
