package application;

import javafx.scene.paint.Color;

import static application.SpaceInvaders.*;

public class Universe {
    private int posX;
    private int posY;
    private int h, w, r, g, b;
    private double opacity;

    public Universe() {
        this.posX = RAND.nextInt(WIDTH);
        this.posY = 0;
        this.h = RAND.nextInt(5) + 1;
        this.w = RAND.nextInt(5) + 1;
        this.r = RAND.nextInt(100) + 150;
        this.g = RAND.nextInt(100) + 150;
        this.b = RAND.nextInt(100) + 150;
        this.opacity = RAND.nextFloat();
        if(getOpacity() < 0) setOpacity(getOpacity() * (-1));
        if(getOpacity() > 0.5) setOpacity(0.5);
    }

    public void draw() {
        if(getOpacity() > 0.8) setOpacity(getOpacity() - 0.01);
        if(getOpacity() < 0.1) setOpacity(getOpacity() + 0.01);
        gc.setFill(Color.rgb(getR(), getG(), getB(), getOpacity()));
        gc.fillOval(getPosX(), getPosY(), getW(), getH());
        setPosY(getPosY() + 20);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }
}
