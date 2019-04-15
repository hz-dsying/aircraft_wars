package shooter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 界面类
 * 包含：要画的内容
 */
public class Main extends JPanel {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 650;
    public static BufferedImage airplane;
    public static BufferedImage background;
    public static BufferedImage bee;
    public static BufferedImage bigplane;
    public static BufferedImage bullet;
    public static BufferedImage gameover;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage start;

    //一个英雄机
    Hero hero = new Hero();
    //多个子弹
    Bullet[] bs = {};
    //多个小蜜蜂
    //多个敌机
    //多个大敌机
    FlyObject[] flyings = {};
    static{//游戏开始之前，加载图片
        try {
            //IO:input output -> 磁盘文件的读写
            //Main类和图片在同一个包下
            airplane = ImageIO.read(Main.class.getResource("airplane.png"));
            background = ImageIO.read(Main.class.getResource("background.png"));
            bee = ImageIO.read(Main.class.getResource("bee.png"));
            bigplane = ImageIO.read(Main.class.getResource("bigplane.png"));
            bullet = ImageIO.read(Main.class.getResource("bullet.png"));
            gameover = ImageIO.read(Main.class.getResource("gameover.png"));
            hero0 = ImageIO.read(Main.class.getResource("hero0.png"));
            hero1 = ImageIO.read(Main.class.getResource("hero1.png"));
            pause = ImageIO.read(Main.class.getResource("pause.png"));
            start = ImageIO.read(Main.class.getResource("start.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean Pause;
    private boolean Gameover = false;
    private boolean Start = true;
    //定时装置
    Timer timer = new Timer();
    int i = 0;
    //调用timer中的方法
    public void action(){
        /*
         * schedule：任务计划
         * 第一个参数：要做的事情
         * 第二个参数：时间 - long，定时器第一次执行的时间（10000,10s后执行）
         * 第三个参数：时间 - long，定时器的周期性执行时间
         * 匿名内部类：继承类，实现接口
         */
        timer.schedule(new TimerTask() { //匿名内部类
            @Override
            public void run() {
                if (!Gameover) {
                    if (!Pause) {
                        i++;
                        // 1.定时生成敌机/小蜜蜂
                        if (i % 20 == 0) {
                            createFlyObject();
                        }
                        // 2.英雄机定时发射子弹
                        if (i % 4 == 0) {
                            shoot();
                        }
                        // 3.敌机+小蜜蜂移动
                        moveFlyObject();
                        // 4.子弹移动
                        moveBullet();
                        // 5.英雄机移动（待定）
                        // 6.判断敌机、小蜜蜂有没有越界
                        isFlyObject();
                        // 7.判断子弹是否越界
                        isBullet();
                        // 8.判断敌机+小蜜蜂有没有被子弹击中
                        isFlyObjectAndBullet();
                        // 9.判断敌机+小蜜蜂有没有撞击英雄机
                        isFlyObjectAndHero();
                        // 10.判断游戏是否结束
                        isGameover();
                        // 11.重画界面 - 刷新界面
                        repaint();// - paint()
                    }
                }
            }
        },1000,20);

        /*MouseAdapter  实现了三个鼠标相关的监听接口，将接口中的所有方法都空实现*/
        MouseAdapter adapter = new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                // 开始游戏/重新开始游戏
                if (Gameover){
                    Start = true;
                    hero.setLife(3);
                    Gameover = false;
                }
                repaint();
            }
            public void mouseEntered(MouseEvent e) {
                // 继续游戏
                Pause = false;
                repaint();
            }
            public void mouseExited(MouseEvent e) {
                // 暂停游戏
                Pause = true;
                repaint();
            }
            public void mouseMoved(MouseEvent e) {
                // 控制英雄机
                // 获得鼠标的坐标
                hero.setX(e.getX()-hero.getWidth()/2);
                hero.setY(e.getY()-hero.getHeight()/2);
                repaint();
            }
        };
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
        //鼠标控制英雄机   监听器
        //监听器本身
        //监听的对象 - 界面中的鼠标
        //安装过程
        //可以监测到动作，做出相应的反应
    }

    /**生成飞行物*/
    public void createFlyObject(){
        int random = (int) (Math.random()*20);
        FlyObject fo;
        if (random == 0){
            fo = new Bee();
        }else if (random <= 16){
            fo = new AirPlane();
        }else{
            fo = new BigPlane();
        }
        flyings = Arrays.copyOf(flyings, flyings.length+1);
        flyings[flyings.length-1] = fo;
    }
    /**移动飞行物*/
    public void moveFlyObject(){
        for (int i = 0; i < flyings.length; i++){
            flyings[i].step();
        }
    }
    /**发射子弹*/
    public void shoot(){
        Bullet b = hero.shoot();
        bs = Arrays.copyOf(bs, bs.length+1);
        bs[bs.length-1] = b;
    }
    /**移动子弹*/
    public void moveBullet(){
        for (int i = 0; i < bs.length; i++){
            bs[i].step();
        }
    }
    /**判断敌机、小蜜蜂有没有越界*/
    public void isFlyObject(){
        FlyObject temp;
        for (int i = 0; i < flyings.length; i++){
            if (flyings[i].getY() >= (HEIGHT - flyings[i].getHeight())){
                flyings[i] = null;
                temp = flyings[i];
                flyings[i] = flyings[flyings.length-1];
                flyings[flyings.length-1] = temp;
                flyings = Arrays.copyOf(flyings,flyings.length-1);
            }
        }
    }
    /**判断子弹是否越界*/
    public void isBullet(){
        Bullet temp;
        for (int i = 0; i < bs.length; i++){
            if (bs[i].getY() <= 0){
                bs[i] = null;
                temp = bs[i];
                bs[i] = bs[bs.length-1];
                bs[bs.length-1] = temp;
                bs = Arrays.copyOf(bs,bs.length-1);
            }
        }
    }
    /**判断敌机+小蜜蜂有没有被子弹击中*/
    public void isFlyObjectAndBullet(){
        Bullet temp1;
        FlyObject temp2;
        for (int i = 0; i < bs.length; i++){
            for (int j = 0; j < flyings.length; j++){
                if((bs[i].getX()>(flyings[j].getX()-bs[i].getWidth())) && (bs[i].getX()<(flyings[j].getX()+flyings[j].getWidth()))
                        &&(bs[i].getY()>(flyings[j].getY()-bs[i].getHeight()))&&(bs[i].getY()<(flyings[j].getY()+flyings[j].getHeight()))){//撞上了
                    temp1 = bs[i];
                    bs[i] = bs[bs.length-1];
                    bs[bs.length-1] = temp1;
                    bs = Arrays.copyOf(bs,bs.length-1);
                    i--;
                    temp2 = flyings[j];
                    flyings[j] = flyings[flyings.length-1];
                    flyings[flyings.length-1] = temp2 ;
                    flyings = Arrays.copyOf(flyings,flyings.length-1);
                    break;
                }
            }
        }
    }
    /**判断敌机+小蜜蜂有没有撞击英雄机*/
    public void isFlyObjectAndHero(){
        FlyObject temp;
        for (int i = 0; i < flyings.length; i++){
            if ((hero.getX()>flyings[i].getX()-hero.getWidth())&&(hero.getX()<(flyings[i].getX()+flyings[i].getWidth()))
                    &&(hero.getY()>(flyings[i].getY()-hero.getHeight()))&&(hero.getY()<(flyings[i].getY()+flyings[i].getHeight()))){//撞上了
                        temp = flyings[i];
                        flyings[i] = flyings[flyings.length-1];
                        flyings[flyings.length-1] = temp;
                        flyings = Arrays.copyOf(flyings,flyings.length-1);
                        hero.setLife(hero.getLife()-1);
            }
        }
    }
    /**判断游戏是否结束*/
     public void isGameover(){
         if (hero.getLife() == 0){
             Gameover = true;
         }
     }
    //绘图的方法
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(background,0,0,null);
        //画游戏开始
        if (Start){
            g.drawImage(start,0,0,null);
        }
        //画英雄机
        paintHero(g);
        //化小蜜蜂
        //画敌机
        //画子弹
        paintFlys(g);
        //画暂停
        if (Pause){
            g.drawImage(pause,0,0,null);
        }
        //画游戏结束
        if (Gameover){
            g.drawImage(gameover,0,0,null);
        }
        //画分数
        paintScore(g);
    }
    /**画分数*/
    public void paintScore(Graphics g){
        Font font = new Font("微软雅黑", Font.BOLD, 20);
        g.setFont(font);
        //Color c = new Color(0,0,0);
        //g.setColor(c);
        g.drawString("Score：", 10, 20);
        String s = hero.getScore()+"";
        g.drawString(s,80,20);
        String c = hero.getLife()+"";
        g.drawString("Life：",10,40);
        g.drawString(c,60,40);
    }
    /**画英雄机*/
    public void paintHero(Graphics g){
        g.drawImage(hero.getImg(),hero.getX(),hero.getY(),null);
    }
    /**画飞行物(小蜜蜂、敌机、子弹)*/
    public void paintFlys(Graphics g){
        for (int i = 0; i < bs.length; i++){
            g.drawImage(bs[i].getImg(),bs[i].getX(), bs[i].getY(), null);
        }
        for (int i = 0; i < flyings.length; i++){
            g.drawImage(flyings[i].getImg(), flyings[i].getX(), flyings[i].getY(), null);
        }
    }

    public  void showMe(){
        //窗口
        JFrame window = new JFrame("飞机大战");
        //设置大小
        window.setSize(WIDTH,HEIGHT);
        //设置默认关闭选项
        //JFrame.EXIT_ON_CLOSE - 静态常量
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置位置居中
        window.setLocationRelativeTo(null);
        //设置置顶
//        window.setAlwaysOnTop(true);
        //设置大小不可调
        window.setResizable(false);
        //设置去除默认窗口效果 - 边框
        window.setUndecorated(true);
        //面板/画板 - 绘图
//        Main panel = new Main();
        //将画板添加到窗口上
        window.add(this);
        //显示  会自动调用paint方法
        window.setVisible(true);
        this.action();
    }
    public static void main(String[] args){
        //创建界面
        Main game = new Main();
        game.showMe();
    }
}
