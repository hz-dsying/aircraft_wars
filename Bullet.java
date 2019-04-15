package shooter;
//子弹
public class Bullet extends FlyObject {
    public Bullet(int x, int y){
        img = Main.bullet;
        this.x = x;
        this.y = y;
        speed = 10;
    }

    @Override
    public void step() {
        y = y - speed;
    }
}
