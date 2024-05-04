package entity.shot;

public class BigShot extends BaseShot {

    public BigShot(int posX, int posY) {
        super(posX, posY);
        setDamage(5);
        setSpeed(3);
        setSize(9);
    }
}
