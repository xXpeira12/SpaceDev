package shot;

import javafx.scene.paint.Color;

import static application.Main.gc;

public class BigShot extends BaseShot {

    public BigShot(int posX, int posY) {
        super(posX, posY);
        setDamage(5);
        setSpeed(3);
        setSize(9);
    }
}
