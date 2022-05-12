package Object;

import State.WorldState;

public abstract class GObject {
    private float posX;
	private float posY;
	
	private WorldState gameWorld;
	
	public GObject(float x, float y, WorldState gameWorld){
		posX = x;
		posY = y;
		this.gameWorld = gameWorld;
	}
	
	public void setPosX(float x){
		posX = x;
	}
	
	public float getPosX(){
		return posX;
	}
	
	public void setPosY(float y){
		posY = y;
	}
	
	public float getPosY(){
		return posY;
	}
	
	public WorldState getGameWorld(){
		return gameWorld;
	}
	
	public abstract void Update();
}
