/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.userinterface;

import com.phat.effect.Animation;
import com.phat.effect.CacheDataLoader;
import com.phat.effect.FrameImage;
import com.phat.gameobject.GameWorld;
import com.phat.gameobject.Megaman;
import com.phat.gameobject.PhysicalMap;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {//tao giao diện, khung chương trình, khung game

    GameWorld gameWorld;
    InputManager inputManager;
    Thread gameThread;
    public boolean isRunning = true;

    public GamePanel() {
        gameWorld = new GameWorld(this);
        inputManager = new InputManager(gameWorld);
    }

    public void startGame() {//bắt đầu chương trình
        gameThread = new Thread(this);
        gameThread.start();
    }

    int a = 0;

    @Override
    public void run() {//phương thức chạy game/ chương trình

        long previousTime = System.nanoTime();//nanoTime là hàm lấy thời gian hệ thống thực đơn vị nano
        long currentTime;
        long sleepTime;

        long period = 1000000000 / 120;

        while (isRunning) {//tạo vòng lập để update frame

            gameWorld.Update();

            gameWorld.Render();

            repaint();
            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1000000);
                } else {
                    Thread.sleep(period / 2000000);
                }
            } catch (InterruptedException ex) {
            }
            previousTime = System.nanoTime();//tạo chu kì làm mới giao diện
        }
    }

    @Override
    public void paint(Graphics g) {//vẽ các hình ra theo tọa độ bên dưới 
        //in bức hình đó ra console, 2 con số là tọa độ cái hình in dc in ra ở đâu( tính gốc tọa độ từ góc trên trái )
        g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
    }

    //3 cái này là callback method keyTyped, keyPressed, keyReleased
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {//nhấn bàn phím(input)

        inputManager.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {//đầu ra(output)
        inputManager.processKeyRelease(e.getKeyCode());
    }

}
