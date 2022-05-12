package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Anima.Animation;
import Anima.Loader_CacheData;
import State.WorldState;

public class Shuriken extends Bullet{

    public Shuriken(float x, float y,WorldState gameWorld) {
        super(x, y, 60, 30, 1.0f, 50, gameWorld);
        //TODO Auto-generated constructor stub
        forwardBulletAnim = Loader_CacheData.getInstance().getAnimation("bluefire");
        backBulletAnim = Loader_CacheData.getInstance().getAnimation("bluefire");
        backBulletAnim.flipAllImage();
    }

    private Animation forwardBulletAnim, backBulletAnim;

    @Override
    public void draw(Graphics2D g2d) {
        // TODO Auto-generated method stub
        if(getSpeedX() > 0){
            if(!forwardBulletAnim.isIgnoreFrame(0) && forwardBulletAnim.getCurrentFrame() == 3){
                forwardBulletAnim.setIgnoreFrame(0);
                forwardBulletAnim.setIgnoreFrame(1);
                forwardBulletAnim.setIgnoreFrame(2);
            }
                
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
        }else{
            if(!backBulletAnim.isIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 3){
                backBulletAnim.setIgnoreFrame(0);
                backBulletAnim.setIgnoreFrame(1);
                backBulletAnim.setIgnoreFrame(2);
            }
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
        if(forwardBulletAnim.isIgnoreFrame(0) || backBulletAnim.isIgnoreFrame(0))
            setPosX(getPosX() + getSpeedX());
        PartiObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
            // System.out.println("Bullet set behurt for enemy");
        }
    }

}
