package State;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Anima.FrameIMG;
import Anima.Loader_CacheData;
import Object.BGMap;
import Object.BossEnemy;
import Object.Cam;
import Object.ManageBullet;
import Object.ManagePartiObject;
import Object.PartiObject;
import Object.PhyMap;
import Object.RedEyesEnemy;
import Object.Shinobi;
import UI.FrameGame;
import UI.PanelGame;

public class WorldState extends State
{

    private BufferedImage bufferedImage;
    private int lastState;

    public ManagePartiObject particularObjectManager;
    public ManageBullet bulletManager;

    public Shinobi shinobi;
   
    public PhyMap physicalMap;
    public BGMap backgroundMap;
    public Cam camera;

    public static final int finalBossX = 3600;
    
    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;
    public static final int PAUSEGAME = 5;
    
    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;
    
    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;
    
    public int storyTutorial = 0;
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1;
    
    private boolean finalbossTrigger = true;
    PartiObject boss;
    
    FrameIMG avatar = Loader_CacheData.getInstance().getFrameImage("avatar");
    
    
    private int numberOfLife = 3;
    
    public AudioClip bgMusic;
    public WorldState(PanelGame gamePanel) {
        super(gamePanel);
        //TODO Auto-generated constructor stub
        texts1[0] = "Nhân danh bin đẹp trai và hoàng đẹp gái \nFROM\n19DTHC3....";
        texts1[1] = "Chúng em sẽ giải cứu bạn Vũ Trung Nguyên 3Đ \n"
                + "và sau đó sẽ giúp bạn ấy thẳng raaa....";
        texts1[2] = "thử thách 6 ngày 6 đêm cứu bạn Nguyên...."
        +"\nai cản cũng không cứu!!";
        texts1[3] = "      GÉT GÔOOOOOOOOOOO!.....";
        textTutorial = texts1[0];

        
        bufferedImage = new BufferedImage(FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        shinobi = new Shinobi(600, 1500, this);
        physicalMap = new PhyMap(0, 0, this);
        backgroundMap = new BGMap(0, 0, this);
        camera = new Cam(0, 50, FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT, this);
        bulletManager = new ManageBullet(this);
        
        particularObjectManager = new ManagePartiObject(this);
        particularObjectManager.addObject(shinobi);
        
        initEnemies();

        bgMusic = Loader_CacheData.getInstance().getSound("bgmusic");
        
    }




    private void initEnemies(){
        PartiObject redeye = new RedEyesEnemy(1250, 410, this);
        redeye.setDirection(PartiObject.LEFT_DIR);
        redeye.setTeamType(PartiObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye);
        
        PartiObject smallRedGun = new RedEyesEnemy(1600, 180, this);
        smallRedGun.setDirection(PartiObject.LEFT_DIR);
        smallRedGun.setTeamType(PartiObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun);
        
        PartiObject smallRedGun2 = new RedEyesEnemy(2000, 200, this);
        smallRedGun2.setTeamType(PartiObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun2);
        
        
        PartiObject redeye2 = new RedEyesEnemy(2500, 500, this);
        redeye2.setDirection(PartiObject.LEFT_DIR);
        redeye2.setTeamType(PartiObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye2);
        
        PartiObject redeye3 = new RedEyesEnemy(3450, 500, this);
        redeye3.setDirection(PartiObject.LEFT_DIR);
        redeye3.setTeamType(PartiObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye3);
        
        PartiObject redeye4 = new RedEyesEnemy(500, 1190, this);
        redeye4.setDirection(PartiObject.RIGHT_DIR);
        redeye4.setTeamType(PartiObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye4);
        
        
        
        
        PartiObject smallRedGun3 = new RedEyesEnemy(1700, 980, this);
        smallRedGun3.setDirection(PartiObject.LEFT_DIR);
        smallRedGun3.setTeamType(PartiObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun3);
    }

    public void switchState(int state){
        previousState = this.state;
        this.state = state;
    }
    
    private void TutorialUpdate(){
        switch(tutorialState){
            case INTROGAME:
                
                if(storyTutorial == 0){
                    if(openIntroGameY < 450) {
                        openIntroGameY+=4;
                    }else storyTutorial ++;
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
            case MEETFINALBOSS:
                if(storyTutorial == 0){
                    if(openIntroGameY >= 450) {
                        openIntroGameY-=1;
                    }
                    if(camera.getPosX() < finalBossX){
                        camera.setPosX(camera.getPosX() + 2);
                    }
                    
                    if(shinobi.getPosX() < finalBossX + 150){
                        shinobi.setDirection(PartiObject.RIGHT_DIR);
                        shinobi.run();
                        shinobi.Update();
                    }else{
                        shinobi.stopRun();
                    }
                    
                    if(openIntroGameY < 450 && camera.getPosX() >= finalBossX && shinobi.getPosX() >= finalBossX + 150){ 
                        camera.lock();
                        storyTutorial++;
                        shinobi.stopRun();
                        physicalMap.phys_map[14][120] = 1;
                        physicalMap.phys_map[15][120] = 1;
                        physicalMap.phys_map[16][120] = 1;
                        physicalMap.phys_map[17][120] = 1;
                        
                        backgroundMap.map[14][120] = 17;
                        backgroundMap.map[15][120] = 17;
                        backgroundMap.map[16][120] = 17;
                        backgroundMap.map[17][120] = 17;
                    }
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
        }
    }
    
    private void drawString(Graphics2D g2, String text, int x, int y){
        for(String str : text.split("\n"))
            g2.drawString(str, x, y+=g2.getFontMetrics().getHeight());
    }
    
    private void TutorialRender(Graphics2D g2){
        switch(tutorialState){
            case INTROGAME:
                int yMid = FrameGame.SCREEN_HEIGHT/2 - 15;
                int y1 = yMid - FrameGame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                int y2 = yMid + openIntroGameY/2;
                
                if(storyTutorial >= 1){
                    g2.drawImage(avatar.getImage(), 600, 350, null);
                    g2.setColor(Color.white);
                    g2.fillRect(150, 480, 450, 150);
                    g2.setColor(Color.black);
                    String text = textTutorial.substring(0, currentSize - 1);
                    drawString(g2, text, 160, 480);
                }
                
                break;
            case MEETFINALBOSS:
                // yMid = FrameGame.SCREEN_HEIGHT/2 - 15;
                // y1 = yMid - FrameGame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                // y2 = yMid + openIntroGameY/2;

                // g2.setColor(Color.BLACK);
                // g2.fillRect(0, y1, FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT/2);
                // g2.fillRect(0, y2, FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT/2);
                break;
        }
    }
 
    @Override
    public void Render() {
        // TODO Auto-generated method stub
        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

        if(g2!=null){

            // NOTE: two lines below make the error splash white screen....
            // need to remove this line
            //g2.setColor(Color.WHITE);
            //g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
            
            
            //physicalMap.draw(g2);
            
            switch(state){
                case INIT_GAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ENTER TO CONTINUE", 400, 300);
                    break;
                case PAUSEGAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(300, 260, 500, 70);
                    g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ENTER TO CONTINUE", 400, 300);
                    break;
                case TUTORIAL:
                    backgroundMap.draw(g2);
                    if(tutorialState == MEETFINALBOSS){
                        particularObjectManager.draw(g2);
                    }
                    TutorialRender(g2);
                    
                    break;
                case GAMEWIN:
                case GAMEPLAY:
                    backgroundMap.draw(g2);
                    particularObjectManager.draw(g2);  
                    bulletManager.draw(g2);
                    
                    g2.setColor(Color.GRAY);
                    g2.fillRect(19, 59, 102, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(20, 60, shinobi.getBlood(), 20);
                    
                    for(int i = 0; i < numberOfLife; i++){
                        g2.drawImage(Loader_CacheData.getInstance().getFrameImage("hearth").getImage(), 20 + i*40, 18, null);
                    }
                    
                    
                    if(state == GAMEWIN){
                        g2.drawImage(Loader_CacheData.getInstance().getFrameImage("gamewin").getImage(), 300, 300, null);
                    }
                    
                    break;
                case GAMEOVER:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, FrameGame.SCREEN_WIDTH, FrameGame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                    break;

            }
            

        }
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        switch(state){
            case INIT_GAME:
                
                break;
            case TUTORIAL:
                TutorialUpdate();
                
                break;
            case GAMEPLAY:
                particularObjectManager.UpdateObjects();
                bulletManager.UpdateObjects();
        
                physicalMap.Update();
                camera.Update();
                
                
                // if(shinobi.getPosX() > finalBossX && finalbossTrigger){
                //     finalbossTrigger = false;
                //     switchState(TUTORIAL);
                //     tutorialState = MEETFINALBOSS;
                //     storyTutorial = 0;
                //     openIntroGameY = 550;
                    
                //     boss = new BossEnemy(finalBossX - 3400, 400, this);
                //     boss.setTeamType(PartiObject.ENEMY_TEAM);
                //     boss.setDirection(PartiObject.LEFT_DIR);
                //     particularObjectManager.addObject(boss);

                // }
                
                if(shinobi.getState() == PartiObject.DEATH){
                    numberOfLife --;
                    if(numberOfLife >= 0){
                        shinobi.setBlood(100);
                        shinobi.setPosY(shinobi.getPosY() - 50);
                        shinobi.setState(PartiObject.NOBEHURT);
                        particularObjectManager.addObject(shinobi);
                    }else{
                        switchState(GAMEOVER);
                        bgMusic.stop();
                    }
                }
                if(!finalbossTrigger && boss.getState() == PartiObject.DEATH)
                    switchState(GAMEWIN);
                
                break;
            case GAMEOVER:
                
                break;
            case GAMEWIN:
                
                break;
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        // TODO Auto-generated method stub
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        // TODO Auto-generated method stub
        switch(code){
            
            case KeyEvent.VK_DOWN:
            shinobi.dick();
                break;
                
            case KeyEvent.VK_RIGHT:
            shinobi.setDirection(shinobi.RIGHT_DIR);
            shinobi.run();
                break;
                
            case KeyEvent.VK_LEFT:
            shinobi.setDirection(shinobi.LEFT_DIR);
            shinobi.run();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == WorldState.INIT_GAME){
                    if(previousState == WorldState.GAMEPLAY)
                        switchState(WorldState.GAMEPLAY);
                    else switchState(WorldState.TUTORIAL);
                    
                    bgMusic.loop();
                }
                if(state == WorldState.TUTORIAL && storyTutorial >= 1){
                    if(storyTutorial<=3){
                        storyTutorial ++;
                        currentSize = 1;
                        textTutorial = texts1[storyTutorial-1];
                    }else{
                        switchState(WorldState.GAMEPLAY);
                    }
                    
                    // for meeting boss tutorial
                    if(tutorialState == WorldState.MEETFINALBOSS){
                        switchState(WorldState.GAMEPLAY);
                    }
                }
                break;
                
            case KeyEvent.VK_SPACE:
                shinobi.jump();
                break;
                
            case KeyEvent.VK_A:
                shinobi.attack();
                break;
                
        }
    }

    @Override
    public void setReleasedButton(int code) {
        // TODO Auto-generated method stub
        switch(code){
            
            case KeyEvent.VK_UP:
                
                break;
                
            case KeyEvent.VK_DOWN:
            shinobi.standUp();
                break;
                
            case KeyEvent.VK_RIGHT:
                if(shinobi.getSpeedX() > 0)
                shinobi.stopRun();
                break;
                
            case KeyEvent.VK_LEFT:
                if(shinobi.getSpeedX() < 0)
                shinobi.stopRun();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == GAMEOVER || state == GAMEWIN) {
                    gamePanel.setState(new StateMenu(gamePanel));
                } else if(state == PAUSEGAME) {
                    state = lastState;
                }
                break;
                
            case KeyEvent.VK_SPACE:
                
                break;
                
            case KeyEvent.VK_A:
                
                break;
            case KeyEvent.VK_ESCAPE:
                lastState = state;
                state = PAUSEGAME;
                break;
                
        }
    }
    
}
