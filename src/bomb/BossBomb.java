package bomb;

import javafx.scene.image.Image;
import shot.Shot;

import java.util.ArrayList;
import java.util.List;

import static application.Main.score;

public class BossBomb extends Bomb {

    private int SPEED = (score / 20) + 2;
    private long lastShotTime;
    private List<Shot> shots;

    public BossBomb(int posX, int posY, int size, Image img, int health) {
        super(posX, posY, size, img, health);
        lastShotTime = System.nanoTime();
        this.shots = new ArrayList<>();
    }


//    public void shoot(List<Shot> shots) {
//        long now = System.nanoTime();
//        if (now - lastShotTime < 500_000_000) { // 500_000_000 nanoseconds = 0.5 seconds
//            return;
//        }
//
//        // Create a new shot and add it to the shots list
//        Shot shot = new Shot(getPosX(), getPosY());
//        shots.add(shot);
//
//        lastShotTime = now;
//    }
//
//    public List<Shot> getShots() {
//        return this.shots;
//    }
}
