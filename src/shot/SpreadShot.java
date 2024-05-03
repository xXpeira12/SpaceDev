package shot;

public class SpreadShot extends SpeedShot{
    private int numShots;
    private SpeedShot[] shots;
    private int spaceBetweenShot;

    public SpreadShot(int posX, int posY, int numShots, int spaceBetweenShot) {
        super(posX, posY);
        this.numShots = numShots;
        this.shots = new SpeedShot[numShots];
        this.spaceBetweenShot = spaceBetweenShot;
        int initialX = -this.spaceBetweenShot * (numShots / 2);
        for(int i=0; i < numShots; i++) {
            shots[i] = new SpeedShot(posX + initialX + i * this.spaceBetweenShot, posY);
        }
    }

    public int getNumShots() {
        return numShots;
    }

    public void setNumShots(int numShots) {
        this.numShots = numShots;
    }

    public Shot[] getShots() {
        return shots;
    }

    public void setShots(Shot[] shots) {
        this.shots = (SpeedShot[]) shots;
    }

    public int getSpaceBetweenShot() {
        return spaceBetweenShot;
    }

    public void setSpaceBetweenShot(int spaceBetweenShot) {
        this.spaceBetweenShot = spaceBetweenShot;
    }
}