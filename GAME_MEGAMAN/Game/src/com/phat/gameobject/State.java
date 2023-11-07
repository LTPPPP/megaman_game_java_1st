/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.userinterface.GamePanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public abstract class State {//class abstract khai báo cái class
    
    protected GamePanel gamePanel;
    
    public State(GamePanel gamePanel) {
       this.gamePanel = gamePanel; 
    }
    
    public abstract void Update();
    public abstract void Render();
    public abstract BufferedImage getBufferedImage();
 
}