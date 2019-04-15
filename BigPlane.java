package shooter;
//大敌机
public class BigPlane extends FlyObject implements Enemy {
    private int blood;
    public BigPlane(){
        img = Main.bigplane;
        width = img.getWidth();
        height = img.getHeight();
        x = (int) (Math.random()*(Main.WIDTH - width));
        y = 0;
        speed = 4;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public void step() {
        y = y + speed;
    }

    public int getScore() {
        return 20;
    }
}
