/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.userinterface;

import com.phat.effect.CacheDataLoader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class GameFrame extends JFrame {//class quản lý các frame dc vẽ ra 

    //thông số khung chương trình
    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 600;

    GamePanel gamePanel;

    public GameFrame() {

        super("Mega Man java game");

        //dùng để canh giữa tọa độ (0,0)
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(((dimension.width - SCREEN_WIDTH) / 2), (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);//tạo 1 box padding bên ngoài
//        SCREEN_WIDTH = dimension.width;//Độ rộng của khung hình phụ thuộc vào máy
//        SCREEN_HEIGHT = dimension.height;//Độ cao của khung hình phụ thuộc vào máy

        //tắt giao diện
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            CacheDataLoader.getInstance().LoadData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //liên kết với tạo giao diện
        gamePanel = new GamePanel();
        add(gamePanel);

        //nhận thông tin và xử lí khi bấm nút điều khiển
        this.addKeyListener(gamePanel);

    }

//    public void maximizeWindow() {//phương thức phóng to full size màn hình
//    setExtendedState(JFrame.MAXIMIZED_BOTH);
//}
    public void startGame() {//bắt đầu game
//        maximizeWindow();
        gamePanel.startGame();
        this.setVisible(true);
    }

    public static void main(String arg[]) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.startGame();
    }
}
