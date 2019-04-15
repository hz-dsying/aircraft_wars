package shooter;

import java.awt.image.BufferedImage;

//英雄机
public class Hero {
    private int life;
    private int score;
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage img;
    private int doublefire;

    public Hero(){
        img = Main.hero0;
        life = 3;
        width = img.getWidth();
        height = img.getHeight();
        x = (Main.WIDTH - width)/2;
        y = Main.HEIGHT - height;
    }

    public Bullet shoot(){
        Bullet b = new Bullet(this.getX()+width/2, this.getY());
        return b;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getFire() {
        return doublefire;
    }

    public void setFire(int fire) {
        this.doublefire = fire;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDoublefire() {
        return doublefire;
    }

    public void setDoublefire(int doublefire) {
        this.doublefire = doublefire;
    }
}
