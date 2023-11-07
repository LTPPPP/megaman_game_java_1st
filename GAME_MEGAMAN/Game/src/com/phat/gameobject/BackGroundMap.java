/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.CacheDataLoader;
import com.phat.userinterface.GameFrame;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class BackGroundMap extends GameObject {

    public int[][] map;
    private int tileSize;
    
    public BackGroundMap(float x, float y, GameWorld gameWorld) {//phương thức vẽ backgroundmap theo tọa độ x y
        super(x, y, gameWorld);
        map = CacheDataLoader.getInstance().getBackGroundMap();
        tileSize = 30;//set bằng với physical map ( size map )
    }

    @Override
    public void Update() {}
   
    public void draw(Graphics2D g2){//phương thức vẽ map theo chuyển động của frame và camera__camera di chuyển theo nhân vật và map
        
        Camera camera = getGameWorld().camera;
        
        g2.setColor(Color.RED);
        for(int i = 0;i< map.length;i++)
            for(int j = 0;j<map[0].length;j++)
                if(map[i][j]!=0 && j*tileSize - camera.getPosX() > -30 && j*tileSize - camera.getPosX() < GameFrame.SCREEN_WIDTH
                        && i*tileSize - camera.getPosY() > -30 && i*tileSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT){ 
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+map[i][j]).getImage(), (int) getPosX() + j*tileSize - (int) camera.getPosX(), 
                        (int) getPosY() + i*tileSize - (int) camera.getPosY(), null);
                }        
    }   
}

