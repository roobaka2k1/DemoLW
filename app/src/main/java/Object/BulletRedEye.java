package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Anima.Animation;
import Anima.Loader_CacheData;
import State.WorldState;

public class BulletRedEye extends Bullet{

    private Animation forwardBulletAnim, backBulletAnim;
    public BulletRedEye(float x, float y, WorldState gameWorld) {
        super(x, y, 30, 30, 1.0f, 5, gameWorld);
        //TODO Auto-generated constructor stub
        forwardBulletAnim = Loader_CacheData.getInstance().getAnimation("redeyebullet");
            backBulletAnim = Loader_CacheData.getInstance().getAnimation("redeyebullet");
            backBulletAnim.flipAllImage();
    }

    @Override
    public void draw(Graphics2D g2d) {
        // TODO Auto-generated method stub
        if(getSpeedX() > 0){          
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
        }else{
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
        }
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        // TODO Auto-generated method stub
        return getBoundForCollisionWithMap();
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        super.Update();
    }
    
}
