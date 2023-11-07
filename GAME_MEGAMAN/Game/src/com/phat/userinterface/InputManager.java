/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.userinterface;

import com.phat.gameobject.ParticularObject;
import com.phat.gameobject.GameWorld;
import com.sun.glass.events.KeyEvent;

/**
 * class này dùng để nhận giá trị từ bàn phím và xử lí
 *
 * @author Lam Tan Phat - CE181023
 */
public class InputManager {//class quản lý các các thao tác input từ bàn phím

    private GamePanel gamePanel;
    private GameWorld gameWorld;

    public InputManager(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void processKeyPressed(int keyCode) {//phương thức dùng để xác định bấm phím nào
        switch (keyCode) {

            case KeyEvent.VK_UP:
                gameWorld.megaman.jump();
                break;

            case KeyEvent.VK_DOWN:
                gameWorld.megaman.crouch();
                break;

            case KeyEvent.VK_LEFT:
                gameWorld.megaman.setDirection(gameWorld.megaman.LEFT_DIR);//xác định hướng đang quay
                gameWorld.megaman.run();
                break;

            case KeyEvent.VK_RIGHT:
                gameWorld.megaman.setDirection(gameWorld.megaman.RIGHT_DIR);//xác định hướng đang quay
                gameWorld.megaman.run();
                break;

            case KeyEvent.VK_ENTER:
                gameWorld.switchState(gameWorld.GAMEPLAY);

                break;

            case KeyEvent.VK_SPACE:
                gameWorld.megaman.jump();
                break;

            case KeyEvent.VK_A:
                gameWorld.megaman.setDirection(gameWorld.megaman.LEFT_DIR);//xác định hướng đang quay
                gameWorld.megaman.run();
                break;
            case KeyEvent.VK_W:
                gameWorld.megaman.jump();
                break;
            case KeyEvent.VK_S:
                gameWorld.megaman.crouch();
                break;
            case KeyEvent.VK_D:
                gameWorld.megaman.setDirection(gameWorld.megaman.RIGHT_DIR);//xác định hướng đang quay
                gameWorld.megaman.run();
                break;
            case KeyEvent.VK_F:
                gameWorld.megaman.attack();
                break;
        }
    }

    public void processKeyRelease(int keyCode) {//phương thức dùng để xác định bấm phím nào
        switch (keyCode) {

            case KeyEvent.VK_UP:

                break;

            case KeyEvent.VK_DOWN:
                gameWorld.megaman.standUp();
                break;

            case KeyEvent.VK_LEFT:
                if (gameWorld.megaman.getPosX() > 0) {
                    gameWorld.megaman.stopRun();
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (gameWorld.megaman.getPosX() > 0) {
                    gameWorld.megaman.stopRun();
                }
                break;

            case KeyEvent.VK_ENTER:

                break;

            case KeyEvent.VK_SPACE:

                break;

            case KeyEvent.VK_A:
                if (gameWorld.megaman.getPosX() > 0) {
                    gameWorld.megaman.stopRun();
                }
                break;
            case KeyEvent.VK_W:

                break;
            case KeyEvent.VK_S:
                gameWorld.megaman.standUp();
                break;
            case KeyEvent.VK_D:
                if (gameWorld.megaman.getPosX() > 0) {
                    gameWorld.megaman.stopRun();
                }
                break;
        }
    }
}
