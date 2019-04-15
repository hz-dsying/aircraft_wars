package shooter;
//小蜜蜂
public class Bee extends FlyObject implements Award {
    private int type;
    public Bee(){
        img = Main.bee;
        width = img.getWidth();
        height = img.getHeight();
        x = (int) (Math.random()*(Main.WIDTH - width));
        y = 0;
        speed = 2;
        type = (int) (Math.random()*2);
    }

    public int getType() {
        return 0;
    }

    public void step() {
        if(x > Main.WIDTH - width){
            speed = -2;
        }
        x = x + speed;
        y = y + 5;
    }
}
