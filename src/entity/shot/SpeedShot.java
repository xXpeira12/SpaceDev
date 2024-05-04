package entity.shot;

import static config.Config.*;

public class SpeedShot extends BaseShot {
    public SpeedShot(int posX, int posY) {
        super(posX, posY);
        setSpeed(SPEEDSHOT_SPEED);
        setSize(SPEEDSHOT_SIZE);
        setDamage(SPEEDSHOT_DAMAGE);
    }
}
