/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.CacheDataLoader;
import com.phat.effect.FrameImage;
import com.phat.gameobject.BackGroundMap;
import com.phat.gameobject.BulletManager;
import com.phat.gameobject.Camera;
import com.phat.gameobject.FinalBoss;
import com.phat.gameobject.Megaman;
import com.phat.gameobject.ParticularObject;
import com.phat.gameobject.ParticularObjectManager;
import com.phat.gameobject.PhysicalMap;
import com.phat.gameobject.RedEyeDevil;
import com.phat.userinterface.GameFrame;
import com.phat.userinterface.GamePanel;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class GameWorld extends State {//class chứa tất cả các thuộc tính, nhân vật, giao diện,... của game

    //khai báo các biến, các phương thức, các class, các thông số,...
    private BufferedImage bufferedImage;
    private int lastState;

    public ParticularObjectManager particularObjectManager;
    public BulletManager bulletManager;

    public Megaman megaman;

    public PhysicalMap physicalMap;
    public BackGroundMap backgroundMap;
    public Camera camera;

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
    ParticularObject boss;

    FrameImage avatar = CacheDataLoader.getInstance().getFrameImage("avatar");

    private int numberOfLife = 3;//số mạng sống

    public AudioClip bgMusic;

    public GameWorld(GamePanel gamePanel) {//contructors

        super(gamePanel);

        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        megaman = new Megaman(400, 400, this);
        physicalMap = new PhysicalMap(0, 0, this);
        backgroundMap = new BackGroundMap(0, 0, this);
        camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
        bulletManager = new BulletManager(this);

        particularObjectManager = new ParticularObjectManager(this);
        particularObjectManager.addObject(megaman);

        initEnemies();

        bgMusic = CacheDataLoader.getInstance().getSound("bgmusic");//lấy âm thanh từ file

    }

    private void initEnemies() {//phương thức phản diện
        ParticularObject redeye = new RedEyeDevil(1250, 410, this);
        redeye.setDirection(ParticularObject.LEFT_DIR);
        redeye.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye);

        ParticularObject smallRedGun = new SmallRedGun(1600, 180, this);
        smallRedGun.setDirection(ParticularObject.LEFT_DIR);
        smallRedGun.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun);

        ParticularObject darkraise = new DarkRaise(2000, 200, this);
        darkraise.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise);

        ParticularObject darkraise2 = new DarkRaise(2800, 350, this);
        darkraise2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise2);

        ParticularObject robotR = new RobotR(900, 400, this);
        robotR.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR);

        ParticularObject robotR2 = new RobotR(3400, 350, this);
        robotR2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR2);

        ParticularObject redeye2 = new RedEyeDevil(2500, 500, this);
        redeye2.setDirection(ParticularObject.LEFT_DIR);
        redeye2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye2);

        ParticularObject redeye3 = new RedEyeDevil(3250, 500, this);
        redeye3.setDirection(ParticularObject.LEFT_DIR);
        redeye3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye3);

        ParticularObject redeye4 = new RedEyeDevil(500, 1190, this);
        redeye4.setDirection(ParticularObject.RIGHT_DIR);
        redeye4.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye4);

        ParticularObject darkraise3 = new DarkRaise(750, 650, this);
        darkraise3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise3);

        ParticularObject darkraise4 = new DarkRaise(770, 1300, this);
        darkraise4.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise4);

        ParticularObject robotR3 = new RobotR(1500, 1150, this);
        robotR3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR3);

        ParticularObject smallRedGun2 = new SmallRedGun(1700, 980, this);
        smallRedGun2.setDirection(ParticularObject.LEFT_DIR);
        smallRedGun2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun2);
    }

    public void switchState(int state) {//phương thức đổi state
        previousState = this.state;
        this.state = state;
    }

    private void drawString(Graphics2D g2, String text, int x, int y) {//vẽ ra các hình trong file
        for (String str : text.split("\n")) {
            g2.drawString(str, x, y += g2.getFontMetrics().getHeight());
        }
    }

    private void TutorialRender(Graphics2D g2) {//phương thức render của tutorials
        switch (tutorialState) {
            case INTROGAME:
                int yMid = GameFrame.SCREEN_HEIGHT / 2 - 15;
                int y1 = yMid - GameFrame.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
                int y2 = yMid + openIntroGameY / 2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);

                if (storyTutorial >= 1) {
                    g2.drawImage(avatar.getImage(), 600, 350, null);
                    g2.setColor(Color.BLUE);
                    g2.fillRect(280, 450, 350, 80);
                    g2.setColor(Color.WHITE);
                    String text = textTutorial.substring(0, currentSize - 1);
                    drawString(g2, text, 290, 480);
                }
                break;
            case MEETFINALBOSS:
                g2.setColor(Color.RED);
                g2.drawString("MEET BOSS!!! PRESS ENTER TO CONTINUE", 400, 300);
                yMid = GameFrame.SCREEN_HEIGHT / 2 - 15;
                y1 = yMid - GameFrame.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
                y2 = yMid + openIntroGameY / 2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
                break;
        }
    }

    public void Update() {//phương thức update các hành động

        switch (state) {
            case GAMEPLAY:
                particularObjectManager.UpdateObjects();
                bulletManager.UpdateObjects();

                physicalMap.Update();
                camera.Update();

                if (megaman.getPosX() > finalBossX && finalbossTrigger) {
                    finalbossTrigger = false;
                    switchState(TUTORIAL);
                    tutorialState = MEETFINALBOSS;
                    storyTutorial = 0;
                    openIntroGameY = 550;

                    boss = new FinalBoss(finalBossX + 700, 460, this);
                    boss.setTeamType(ParticularObject.ENEMY_TEAM);
                    boss.setDirection(ParticularObject.LEFT_DIR);
                    particularObjectManager.addObject(boss);

                }

                if (megaman.getState() == ParticularObject.DEATH) {
                    numberOfLife--;
                    if (numberOfLife >= 0) {
                        megaman.setBlood(100);
                        megaman.setPosY(megaman.getPosY() - 50);
                        megaman.setState(ParticularObject.NOBEHURT);
                        particularObjectManager.addObject(megaman);
                    } else {
                        switchState(GAMEOVER);
                        bgMusic.stop();
                    }
                }
                if (!finalbossTrigger && boss.getState() == ParticularObject.DEATH) {
                    switchState(GAMEWIN);
                }
        }
    }

    public void Render() {//hàm render các hành động

        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

        if (g2 != null) {

            switch (state) {
                case INIT_GAME://khi bắt đầu game
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ENTER TO CONTINUE", 400, 300);
                    g2.drawString("A,S,W,D or direction to move   -  F to shoot -  space to jump ", 550, 500);
                    break;
                case TUTORIAL://cảnh nghỉ
                    backgroundMap.draw(g2);
                    if (tutorialState == MEETFINALBOSS) {
                        particularObjectManager.draw(g2);
                    }
                    TutorialRender(g2);
                    break;
                case GAMEWIN://khi thắng
                case GAMEPLAY://khi đang hành động
                    backgroundMap.draw(g2);
                    particularObjectManager.draw(g2);
                    bulletManager.draw(g2);

                    g2.setColor(Color.GRAY);
                    g2.fillRect(19, 59, 102, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(20, 60, megaman.getBlood(), 20);

                    for (int i = 0; i < numberOfLife; i++) {//vẽ trái tim
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getImage(), 20 + i * 40, 18, null);
                    }

                    if (state == GAMEWIN) {
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(), 300, 300, null);
                    }

                    break;
                case GAMEOVER://khi thua
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                    break;
            }
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
