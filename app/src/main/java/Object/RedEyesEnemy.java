package Object;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Anima.Animation;
import Anima.Loader_CacheData;
import State.WorldState;

public class RedEyesEnemy extends PartiObject{

    private Animation forwardAnim, backAnim;
    
    private long startTimeToShoot;
    
    private AudioClip shooting;
    public RedEyesEnemy(float x, float y,  WorldState gameWorld) {
        super(x, y, 127, 89, 0, 100, gameWorld);
        //TODO Auto-generated constructor stub
        backAnim = Loader_CacheData.getInstance().getAnimation("redeye");
        forwardAnim = Loader_CacheData.getInstance().getAnimation("redeye");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setDamage(10);
        setTimeForNoBehurt(300000000);
        shooting = Loader_CacheData.getInstance().getSound("redeyeshooting");
    
    }

    @Override
    public void attack() {
       
        shooting.play();
        Bullet bullet = new BulletRedEye(getPosX(), getPosY(), getGameWorld());
        if(getDirection() == LEFT_DIR) bullet.setSpeedX(-8);
        else bullet.setSpeedX(8);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!isObjectOutOfCameraView()){
            if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
                if(getDirection() == LEFT_DIR){
                    backAnim.Update(System.nanoTime());
                    backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    forwardAnim.Update(System.nanoTime());
                    forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
        
    }

    @Override
    public void Update() {
      
        super.Update();
        if(System.nanoTime() - startTimeToShoot > 1000*10000000){
            attack();
            // System.out.println("Red Eye attack");
            startTimeToShoot = System.nanoTime();
        }
    }

    
}
