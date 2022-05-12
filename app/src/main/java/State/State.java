package State;

import UI.PanelGame;
import java.awt.image.BufferedImage;

public abstract class State // tại sao chỗ này abstract
{
    protected PanelGame gamePanel;
    
    public State(PanelGame gamePanel) {
       this.gamePanel = gamePanel; 
    }
    
    public abstract void Update();
    public abstract void Render();
    public abstract BufferedImage getBufferedImage();
    
    public abstract void setPressedButton(int code);
    public abstract void setReleasedButton(int code);
}
