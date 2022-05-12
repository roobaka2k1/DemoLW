package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import State.WorldState;

public abstract class Bullet extends PartiObject{


    public Bullet(float x, float y, float width, float height, float mass,int damage, WorldState gameWorld) {
        super(x, y, width, height, mass, 1, gameWorld);
        //TODO Auto-generated constructor stub
        setDamage(damage);
    }
    public abstract void draw(Graphics2D g2d);

    public void Update(){
        super.Update();
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        PartiObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.beHurt(getDamage());
            System.out.println("Bullet set behurt for enemy");
        }
    }
    
}
