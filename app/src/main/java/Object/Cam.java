package Object;

import State.WorldState;

public class Cam extends GObject{

    private float widthView;
    private float heightView;
    
    private boolean isLocked = false;

    public Cam(float x, float y,float widthView,float heightView, WorldState gameWorld) {
        super(x, y, gameWorld);
        //TODO Auto-generated constructor stub
        this.widthView = widthView;
        this.heightView = heightView;
    }

    public void lock(){
        isLocked = true;
    }
    
    public void unlock(){
        isLocked = false;
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        if(!isLocked){
        
            Shinobi mainCharacter = getGameWorld().shinobi;

            if(mainCharacter.getPosX() - getPosX() > 400) setPosX(mainCharacter.getPosX() - 400);
            if(mainCharacter.getPosX() - getPosX() < 200) setPosX(mainCharacter.getPosX() - 200);

            if(mainCharacter.getPosY() - getPosY() > 400) setPosY(mainCharacter.getPosY() - 400); // bottom
            else if(mainCharacter.getPosY() - getPosY() < 250) setPosY(mainCharacter.getPosY() - 250);// top 
        }
        
    }
    public float getWidthView() {
        return widthView;
    }

    public void setWidthView(float widthView) {
        this.widthView = widthView;
    }

    public float getHeightView() {
        return heightView;
    }

    public void setHeightView(float heightView) {
        this.heightView = heightView;
    }
    
}
