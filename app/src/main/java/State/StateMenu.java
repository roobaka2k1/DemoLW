package State;

import UI.FrameGame;
import UI.PanelGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Controll.Btn;
import Controll.RectBtn;



public class StateMenu extends State
{
    public StateMenu(PanelGame gamePanel) {
        super(gamePanel);
        //TODO Auto-generated constructor stub
        bufferedImage = new BufferedImage(FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        buttons = new Btn[NUMBER_OF_BUTTON];
        buttons[0] = new RectBtn("NEW GAME", 300, 100, 100, 40, 15, 25, Color.ORANGE);
		buttons[0].setHoverBgColor(Color.BLUE);
		buttons[0].setPressedBgColor(Color.GREEN);

//		buttons[1] = new RectangleButton("CONTINUE", 300, 160, 100, 40, 15, 25, Color.ORANGE);
//		buttons[1].setHoverBgColor(Color.BLUE);
//		buttons[1].setPressedBgColor(Color.GREEN);
		

		buttons[1] = new RectBtn("EXIT", 300, 160, 100, 40, 15, 25, Color.ORANGE);
		buttons[1].setHoverBgColor(Color.BLUE);
		buttons[1].setPressedBgColor(Color.GREEN);
    }

    public final int NUMBER_OF_BUTTON = 2;
    private BufferedImage bufferedImage;
    Graphics graphicsPaint;

    private Btn[] buttons;
	private int buttonSelected = 0;
	private boolean canContinueGame = false;

    
    @Override
    public void Update() {
        for(int i = 0;i<NUMBER_OF_BUTTON;i++) {
            if(i == buttonSelected) {
                buttons[i].setState(Btn.HOVER);
            } else {
                buttons[i].setState(Btn.NONE);
            }
        }
    }

    @Override
    public void Render() {
        if(bufferedImage == null) {
            bufferedImage = new BufferedImage(FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            return;
        }
        graphicsPaint = bufferedImage.getGraphics();
        if(graphicsPaint == null) {
            graphicsPaint = bufferedImage.getGraphics();
            return;
        }
        graphicsPaint.setColor(Color.CYAN);
		graphicsPaint.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		for (Btn bt : buttons) {
			bt.draw(graphicsPaint);
		}
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        switch(code) {
            case KeyEvent.VK_DOWN:
                buttonSelected++;
                if(buttonSelected >= NUMBER_OF_BUTTON) {
                    buttonSelected = 0;
                }
                break;
            case KeyEvent.VK_UP:
                buttonSelected--;
                if(buttonSelected < 0) {
                    buttonSelected = NUMBER_OF_BUTTON - 1;
                }
                break;
            case KeyEvent.VK_ENTER:
                actionMenu();
                break;
        }
    }

    @Override
    public void setReleasedButton(int code) {}
    
    private void actionMenu() {
        switch(buttonSelected) {
            case 0:
                gamePanel.setState(new WorldState(gamePanel));
                break;
           
            case 1:
                System.exit(0);
                break;
        }
    }
}
