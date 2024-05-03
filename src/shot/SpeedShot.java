package shot;

import javafx.scene.paint.Color;

import static application.Main.gc;

public class SpeedShot extends Shot {
    public SpeedShot(int posX, int posY) {
        super(posX, posY);
        setSpeed(20);
        setSize(4);
        setDamage(1);
    }

    public void draw() {
        gc.setFill(Color.RED);
        gc.fillOval(getPosX(), getPosY(), size, size);
    }
}
