package Object;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Anima.Loader_CacheData;
import State.WorldState;
import Anima.Animation;

public class Shinobi extends Character{

    private Animation runForwardAnim, runBackAnim,
     runShootingForwarAnim, runShootingBackAnim,
     runAttackFroAnimation,runAttackBackAnimation,
    flyShootingForwardAnim,flyShootingBackAnim;
    private Animation idleForwardAnim, idleBackAnim, 
    idleShootingForwardAnim, idleShootingBackAnim,
    idleAttackForwardAnimation,idleAttackBackAnimation;
    private Animation dickForwardAnim, dickBackAnim;
    private Animation flyForwardAnim, flyBackAnim, 
    flyAttackForwardAnim, flyAttackBackAnim;
    private Animation landingForwardAnim, landingBackAnim;
    
    private Animation climWallForward, climWallBack;
    
    private long lastShootingTime;
    private long lastAttakingTime;
    private boolean isShooting = false;
    
    private AudioClip hurtingSound;
    private AudioClip shooting1;
    public Shinobi(float x, float y, WorldState gameWorld) {
        super(x, y, 70, 90, 0.1f, 100, gameWorld);
        //TODO Auto-generated constructor stub
        shooting1 = Loader_CacheData.getInstance().getSound("bluefireshooting");
        hurtingSound = Loader_CacheData.getInstance().getSound("megamanhurt");
        
        setTeamType(LEAGUE_TEAM);

        setTimeForNoBehurt(2000*1000000);
        
        runForwardAnim = Loader_CacheData.getInstance().getAnimation("run");
        runBackAnim = Loader_CacheData.getInstance().getAnimation("run");
        runBackAnim.flipAllImage();   
        
        idleForwardAnim = Loader_CacheData.getInstance().getAnimation("idle");
        idleBackAnim = Loader_CacheData.getInstance().getAnimation("idle");
        idleBackAnim.flipAllImage();
        
        dickForwardAnim = Loader_CacheData.getInstance().getAnimation("dick");
        dickBackAnim = Loader_CacheData.getInstance().getAnimation("dick");
        dickBackAnim.flipAllImage();
        
        flyForwardAnim = Loader_CacheData.getInstance().getAnimation("flyingup");
        flyForwardAnim.setIsRepeated(false);
        flyBackAnim = Loader_CacheData.getInstance().getAnimation("flyingup");
        flyBackAnim.setIsRepeated(false);
        flyBackAnim.flipAllImage();
        
        landingForwardAnim = Loader_CacheData.getInstance().getAnimation("landing");
        landingBackAnim = Loader_CacheData.getInstance().getAnimation("landing");
        landingBackAnim.flipAllImage();
        
        climWallBack = Loader_CacheData.getInstance().getAnimation("clim_wall");
        climWallForward = Loader_CacheData.getInstance().getAnimation("clim_wall");
        climWallForward.flipAllImage();
        
        behurtForwardAnim = Loader_CacheData.getInstance().getAnimation("hurting");
        behurtBackAnim = Loader_CacheData.getInstance().getAnimation("hurting");
        behurtBackAnim.flipAllImage();
        
        idleShootingForwardAnim = Loader_CacheData.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim = Loader_CacheData.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim.flipAllImage();
        
        runShootingForwarAnim = Loader_CacheData.getInstance().getAnimation("runshoot");
        runShootingBackAnim = Loader_CacheData.getInstance().getAnimation("runshoot");
        runShootingBackAnim.flipAllImage();
        
        flyShootingForwardAnim = Loader_CacheData.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim = Loader_CacheData.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim.flipAllImage();

        //// Thêm đánh kiếm
        idleAttackForwardAnimation = Loader_CacheData.getInstance().getAnimation("idleatk");
        idleAttackBackAnimation = Loader_CacheData.getInstance().getAnimation("idleatk");
        idleAttackBackAnimation.flipAllImage();

        runAttackFroAnimation =  Loader_CacheData.getInstance().getAnimation("idleatk");
        runAttackBackAnimation =  Loader_CacheData.getInstance().getAnimation("idleatk");
        runAttackBackAnimation.flipAllImage();

        flyAttackForwardAnim = Loader_CacheData.getInstance().getAnimation("idleatk");
        flyAttackBackAnim =  Loader_CacheData.getInstance().getAnimation("idleatk");
        flyAttackBackAnim.flipAllImage();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if(getDirection() == LEFT_DIR)
        setSpeedX(-3);
    else setSpeedX(3);
    }

    @Override
    public void jump() {
        // TODO Auto-generated method stub
        if(!getIsJumping()){
            setIsJumping(true);
            setSpeedY(-9.0f);           
            flyBackAnim.reset();
            flyForwardAnim.reset();
        }
        // for clim wall
        else{
            Rectangle rectRightWall = getBoundForCollisionWithMap();
            rectRightWall.x += 1;
            Rectangle rectLeftWall = getBoundForCollisionWithMap();
            rectLeftWall.x -= 1;
            
            if(getGameWorld().physicalMap.haveCollisionWithRightWall(rectRightWall)!=null && getSpeedX() > 0){
                setSpeedY(-5.0f);
                //setSpeedX(-1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(LEFT_DIR);
            }else if(getGameWorld().physicalMap.haveCollisionWithLeftWall(rectLeftWall)!=null && getSpeedX() < 0){
                setSpeedY(-5.0f);
                //setSpeedX(1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(RIGHT_DIR);
            }
                
        }
    }

    @Override
    public void dick() {
        // TODO Auto-generated method stub
        if(!getIsJumping())
            setIsDicking(true);
    }

    @Override
    public void standUp() {
        // TODO Auto-generated method stub
        setIsDicking(false);
        idleForwardAnim.reset();
        idleBackAnim.reset();
        dickForwardAnim.reset();
        dickBackAnim.reset();
    }

    @Override
    public void stopRun() {
        // TODO Auto-generated method stub
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub
        if(!isShooting && !getIsDicking()){
            
            shooting1.play();
            
            Bullet bullet = new Shuriken(getPosX(), getPosY(), getGameWorld());
            if(getDirection() == LEFT_DIR) {
                bullet.setSpeedX(-10);
                bullet.setPosX(bullet.getPosX() - 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() - 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }else {
                bullet.setSpeedX(10);
                bullet.setPosX(bullet.getPosX() + 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() + 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }
            if(getIsJumping())
                bullet.setPosY(bullet.getPosY() - 20);
            
            bullet.setTeamType(getTeamType());
            getGameWorld().bulletManager.addObject(bullet);
            
            lastShootingTime = System.nanoTime();
            isShooting = true;
            
        }





        // if(!isAttaking && !getIsDicking()){
            
        //     shooting1.play();
            
        //     Bokken bokken = new Bokken(getPosX(), getPosY(), getGameWorld());
        //     if(getDirection() == LEFT_DIR) {
        //         bokken.setSpeedX(-100);
        //         bokken.setPosX(bokken.getPosX() - 15);
        //         if(getSpeedX() != 0 && getSpeedY() == 0){
        //             bokken.setPosX(bokken.getPosX() - 2);
        //             bokken.setPosY(bokken.getPosY() - 1);
        //         }
        //     }else {
        //         bokken.setSpeedX(100);
        //         bokken.setPosX(bokken.getPosX() + 15);
        //         if(getSpeedX() != 0 && getSpeedY() == 0){
        //             bokken.setPosX(bokken.getPosX() + 2);
        //             bokken.setPosY(bokken.getPosY() - 1);
        //         }
        //     }
        //     if(getIsJumping())
        //     bokken.setPosY(bokken.getPosY() - 20);
            
        //     bokken.setTeamType(getTeamType());
        //     getGameWorld().bulletManager.addObject(bokken);
            
        //     lastAttakingTime = System.nanoTime();
        //     isAttaking = true;
            
        // }
    }
    // @Override
    // public void hurtingCallback(){
    //     System.out.println("Call back hurting");
    //     hurtingSound.play();
    // }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        // TODO Auto-generated method stub
        Rectangle rect = getBoundForCollisionWithMap();
        
        if(getIsDicking()){
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 20;
            rect.width = 44;
            rect.height = 65;
        }else{
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 40;
            rect.width = 44;
            rect.height = 80;
        }
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        // TODO Auto-generated method stub
        switch(getState()){
        
            case ALIVE:
            case NOBEHURT:
                if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1)
                {
                    // System.out.println("Plash...");
                }else{
                    
                    if(getIsLanding()){

                        if(getDirection() == RIGHT_DIR){
                            landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
                            landingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - landingForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            landingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - landingBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else if(getIsJumping()){

                        if(getDirection() == RIGHT_DIR){
                            flyForwardAnim.Update(System.nanoTime());
                            if(isShooting){
                                flyShootingForwardAnim.setCurrentFrame(flyForwardAnim.getCurrentFrame());
                                flyShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()) + 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                flyForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }else{
                            flyBackAnim.Update(System.nanoTime());
                            if(isShooting){
                                flyShootingBackAnim.setCurrentFrame(flyBackAnim.getCurrentFrame());
                                flyShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()) - 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                            flyBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }

                    }else if(getIsDicking()){

                        if(getDirection() == RIGHT_DIR){
                            dickForwardAnim.Update(System.nanoTime());
                            dickForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - dickForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            dickBackAnim.Update(System.nanoTime());
                            dickBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - dickBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else{
                        if(getSpeedX() > 0){
                            runForwardAnim.Update(System.nanoTime());
                            if(isShooting){
                                runShootingForwarAnim.setCurrentFrame(runForwardAnim.getCurrentFrame() - 1);
                                runShootingForwarAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runForwardAnim.getCurrentFrame() == 1) runForwardAnim.setIgnoreFrame(0);
                        }else if(getSpeedX() < 0){
                            runBackAnim.Update(System.nanoTime());
                            if(isShooting){
                                runShootingBackAnim.setCurrentFrame(runBackAnim.getCurrentFrame() - 1);
                                runShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                runBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runBackAnim.getCurrentFrame() == 1) runBackAnim.setIgnoreFrame(0);
                        }else{
                            if(getDirection() == RIGHT_DIR){
                                if(isShooting){
                                    idleShootingForwardAnim.Update(System.nanoTime());
                                    idleShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }else{
                                    idleForwardAnim.Update(System.nanoTime());
                                    idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }else{
                                if(isShooting){
                                    idleShootingBackAnim.Update(System.nanoTime());
                                    idleShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }else{
                                    idleBackAnim.Update(System.nanoTime());
                                    idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }
                        }            
                    }
                }
                
                break;
            
            case BEHURT:
                if(getDirection() == RIGHT_DIR){
                    behurtForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    behurtBackAnim.setCurrentFrame(behurtForwardAnim.getCurrentFrame());
                    behurtBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;
             
            case FEY:
                
                break;

        }
        
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        super.Update();
        if(isShooting){
            if(System.nanoTime() - lastShootingTime > 500*1000000){
                isShooting = false;
            }
        }
        
        if(getIsLanding()){
            landingBackAnim.Update(System.nanoTime());
            if(landingBackAnim.isLastFrame()) {
                setIsLanding(false);
                landingBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
            }
        }
    }
    
}
