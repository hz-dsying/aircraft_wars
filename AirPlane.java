package shooter;
//敌机
public class AirPlane extends FlyObject implements Enemy {
    private int blood;
    public AirPlane(){
        img = Main.airplane;
        width = img.getWidth();
        height = img.getHeight();
        x = (int) (Math.random()*(Main.WIDTH - width));
        y = 0;
        speed = 3;
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
        return 10;
    }
}
