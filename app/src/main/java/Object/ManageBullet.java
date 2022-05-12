package Object;

import State.WorldState;

public class ManageBullet extends ManagePartiObject{

    public ManageBullet(WorldState gameWorld) {
        super(gameWorld);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void UpdateObjects() {
        // TODO Auto-generated method stub
        super.UpdateObjects();
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                PartiObject object = particularObjects.get(id);
                
                if(object.isObjectOutOfCameraView() || object.getState() == PartiObject.DEATH){
                    particularObjects.remove(id);
                }
            }
        }
    }
    
}
