package shot;

import bomb.Bomb;

public class BaseShot extends Shot {
    public static int size;

    public BaseShot(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
        setSpeed(10);
        setDamage(2);
        setSize(6);
    }

    public boolean colide(Bomb bomb) {
        int distance = distance(getPosX() + getSize() / 2, getPosY() + getSize() / 2, bomb.getPosX() + bomb.getSize() / 2, bomb.getPosY() + bomb.getSize() / 2);
        return distance < bomb.getSize() / 2 + getSize() / 2;
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    @Override
    public void dealDamage(Bomb bomb) {
        bomb.setHealth(bomb.getHealth() - getDamage());
    }
}
