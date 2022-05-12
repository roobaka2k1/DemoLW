package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Anima.Animation;
import Anima.Loader_CacheData;
import State.WorldState;

public class RocketBullet extends Bullet{

    private Animation forwardBulletAnimUp, forwardBulletAnimDown, forwardBulletAnim;
    private Animation backBulletAnimUp, backBulletAnimDown, backBulletAnim;

    private long startTimeForChangeSpeedY;
    public RocketBullet(float x, float y, WorldState gameWorld) {
        super(x, y, 30, 30, 1.0f, 5, gameWorld);
        //TODO Auto-generated constructor stub
        backBulletAnimUp = Loader_CacheData.getInstance().getAnimation("rocketUp");
            backBulletAnimDown = Loader_CacheData.getInstance().getAnimation("rocketDown");
            backBulletAnim = Loader_CacheData.getInstance().getAnimation("rocket");
            
            forwardBulletAnimUp = Loader_CacheData.getInstance().getAnimation("rocketUp");
            forwardBulletAnimUp.flipAllImage();
            forwardBulletAnimDown = Loader_CacheData.getInstance().getAnimation("rocketDown");
            forwardBulletAnimDown.flipAllImage();
            forwardBulletAnim = Loader_CacheData.getInstance().getAnimation("rocket");
            forwardBulletAnim.flipAllImage();
    }

    private void changeSpeedY(){
        if(System.currentTimeMillis() % 3 == 0){
            setSpeedY(getSpeedX());
        }else if(System.currentTimeMillis() % 3 == 1){
            setSpeedY(-getSpeedX());
        }else {
            setSpeedY(0);
            
        }
    }
    @Override
    public void draw(Graphics2D g2d) {
        // TODO Auto-generated method stub
        if(getSpeedX() > 0){  
            if(getSpeedY() > 0){
                forwardBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
            }else if(getSpeedY() < 0){
                forwardBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
            }else
                forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
        }else{
            if(getSpeedY() > 0){
                backBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
            }else if(getSpeedY() < 0){
                backBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2d);
            }else
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
        if(System.nanoTime() - startTimeForChangeSpeedY > 500*1000000){
            startTimeForChangeSpeedY = System.nanoTime();
            changeSpeedY();
        }
    }


}
