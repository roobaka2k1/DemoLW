package Object;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import State.WorldState;

public class ManagePartiObject {
    protected List<PartiObject> particularObjects;

    private WorldState gameWorld;
    
    public ManagePartiObject(WorldState gameWorld){
        
        particularObjects = Collections.synchronizedList(new LinkedList<PartiObject>());
        this.gameWorld = gameWorld;
        
    }
    
    public WorldState getGameWorld(){
        return gameWorld;
    }
    
    public void addObject(PartiObject particularObject){
        
        
        synchronized(particularObjects){
            particularObjects.add(particularObject);
        }
        
    }
    
    public void RemoveObject(PartiObject particularObject){
        synchronized(particularObjects){
        
            for(int id = 0; id < particularObjects.size(); id++){
                
                PartiObject object = particularObjects.get(id);
                if(object == particularObject)
                    particularObjects.remove(id);

            }
        }
    }
    
    public PartiObject getCollisionWidthEnemyObject(PartiObject object){
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                PartiObject objectInList = particularObjects.get(id);

                if(object.getTeamType() != objectInList.getTeamType() && 
                        object.getBoundForCollisionWithEnemy().intersects(objectInList.getBoundForCollisionWithEnemy())){
                    return objectInList;
                }
            }
        }
        return null;
    }
    
    public void UpdateObjects(){
        
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                PartiObject object = particularObjects.get(id);
                
                
                if(!object.isObjectOutOfCameraView()) object.Update();
                
                if(object.getState() == PartiObject.DEATH){
                    particularObjects.remove(id);
                }
            }
        }

        //System.out.println("Camerawidth  = "+camera.getWidth());
        
    }
    
    public void draw(Graphics2D g2){
        synchronized(particularObjects){
            for(PartiObject object: particularObjects)
                if(!object.isObjectOutOfCameraView()) object.draw(g2);
        }
    }
}
