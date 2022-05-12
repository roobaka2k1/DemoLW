package Object;

import State.WorldState;
import Anima.Loader_CacheData;
import java.awt.Color;
import java.awt.Graphics2D;
import UI.FrameGame;
public class BGMap extends GObject
{

    public int[][] map;
    private int tileSize;

    public BGMap(float x, float y, WorldState gameWorld) {
        super(x, y, gameWorld);
        //TODO Auto-generated constructor stub
        map = Loader_CacheData.getInstance().getBackgroundMap();
        tileSize = 30;
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        
    }
    public void draw(Graphics2D g2){
        
        Cam camera = getGameWorld().camera;
        
        g2.setColor(Color.RED);
        for(int i = 0;i< map.length;i++)
            for(int j = 0;j<map[0].length;j++)
                if(map[i][j]!=0 && j*tileSize - camera.getPosX() > -30 && j*tileSize - camera.getPosX() < FrameGame.SCREEN_WIDTH
                        && i*tileSize - camera.getPosY() > -30 && i*tileSize - camera.getPosY() < FrameGame.SCREEN_HEIGHT){ 
                    g2.drawImage(Loader_CacheData.getInstance().getFrameImage("tiled"+map[i][j]).getImage(), (int) getPosX() + j*tileSize - (int) camera.getPosX(), 
                        (int) getPosY() + i*tileSize - (int) camera.getPosY(), null);
                }
        
    }
    
}
