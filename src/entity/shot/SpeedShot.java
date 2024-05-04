package entity.shot;

public class SpeedShot extends BaseShot {
    public SpeedShot(int posX, int posY) {
        super(posX, posY);
        setSpeed(20);
        setSize(4);
        setDamage(1);
    }
}
