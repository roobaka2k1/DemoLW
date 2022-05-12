package UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import Anima.Loader_CacheData;

public class FrameGame extends JFrame
{
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600;

    PanelGame gamePanel;

    public FrameGame(){

        super("Mega Man java game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension solution = toolkit.getScreenSize();

        try {
            Loader_CacheData.getInstance().LoadData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.setBounds((solution.width - SCREEN_WIDTH)/2, (solution.height - SCREEN_HEIGHT)/2, SCREEN_WIDTH, SCREEN_HEIGHT);

        gamePanel = new PanelGame();
        addKeyListener(gamePanel);
        add(gamePanel);

    }

    public void startGame(){

            gamePanel.startGame();
            this.setVisible(true);

    }

    public static void main(String arg[]){




        FrameGame gameFrame = new FrameGame();
            gameFrame.startGame();

    }
}
