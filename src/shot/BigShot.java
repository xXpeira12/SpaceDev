package shot;

import ability.Drawable;
import ability.Updatable;
import javafx.scene.paint.Color;

import static application.Main.gc;

public class BigShot extends Shot {

    public BigShot(int posX, int posY) {
        super(posX, posY);
        setDamage(5);
        setSpeed(3);
        setSize(9);
    }

    public void draw() {
        gc.setFill(Color.RED);
        gc.fillOval(getPosX(), getPosY(), size, size);
    }
}
