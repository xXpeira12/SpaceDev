package shot;

import static application.Main.gc;
import javafx.scene.paint.Color;
import rocket.Rocket;

import java.util.Arrays;

public class SpreadShot extends SpeedShot{
    private int numShots;
    private Shot[] shots;

    public SpreadShot(int posX, int posY, int numShots) {
        super(posX, posY);
        this.numShots = numShots;
        this.shots = new Shot[numShots];
        for(int i=0; i < numShots; i++) {
            shots[i] = new Shot((int) (posX + Math.pow(-1, i) * i * 30), posY);
        }
    }

    public void update() {
        for (int i = 0; i < numShots; i++) {
            shots[i].setPosY(shots[i].getPosY() - shots[i].getSpeed());
        }
    }

    public void draw() {
        for(int i = 0; i < numShots; i++) {
            gc.setFill(Color.RED);
            gc.fillOval(shots[i].getPosX(), shots[i].getPosY(), size, size);
        }
    }
}