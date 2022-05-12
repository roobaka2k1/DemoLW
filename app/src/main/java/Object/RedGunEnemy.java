package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Anima.Animation;
import Anima.Loader_CacheData;
import State.WorldState;

public class RedGunEnemy extends PartiObject{

    private Animation forwardAnim, backAnim;
    
    private long startTimeToShoot;
    public RedGunEnemy(float x, float y, WorldState gameWorld) {
        super(x, y, 127, 89, 0, 1000, gameWorld);
        backAnim = Loader_CacheData.getInstance().getAnimation("smallredgun");
        forwardAnim = Loader_CacheData.getInstance().getAnimation("smallredgun");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setTimeForNoBehurt(300000000);
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub
        Bullet bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(-3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
        
        bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
         getGameWorld().bulletManager.addObject(bullet);
        
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void draw(Graphics2D g2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        super.Update();
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*2.0){
            attack();
            // System.out.println("Red Eye attack");
            startTimeToShoot = System.nanoTime();
        }
    }
    
}
