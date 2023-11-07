/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.CacheDataLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class PhysicalMap extends GameObject {//class tạo physical map 

    //khai báo ma trận và size của map
    public int[][] phys_map;
    private int tileSize;

    public PhysicalMap(float x, float y, GameWorld gameWorld) {// khai báo tọa độ của map
        super(x, y, gameWorld);
        this.tileSize = 30;
        phys_map = CacheDataLoader.getInstance().getPhysicalMap();
    }

    public int getTileSize() {
        return tileSize;
    }

    public void draw(Graphics2D g2) {//vẽ 1 khung cammera đi theo nhân vật

        Camera camera = getGameWorld().camera;

        g2.setColor(Color.GRAY);
        for (int i = 0; i < phys_map.length; i++) {
            for (int j = 0; j < phys_map[0].length; j++) {
                if (phys_map[i][j] != 0) {
                    g2.fillRect((int) getPosX() + j * tileSize - (int) camera.getPosX(),
                            (int) getPosY() + i * tileSize - (int) camera.getPosY(), tileSize, tileSize);
                }
            }
        }

    }

    public Rectangle haveCollisionWithLand(Rectangle rect) {//phương thức xét để nó đứng trên mặt đất được 
        //tính toán giá trị x và y và đọc tọa độ map
        int posX1 = rect.x / tileSize;
        posX1 -= 2;//xét mấy cái sai số
        int posX2 = (rect.x + rect.width) / tileSize;
        posX2 += 2;//xét mấy cái sai số

        int posY1 = (rect.y + rect.height) / tileSize;

        if (posX1 < 0) {//xét điều kiện ngoại lệ posX < 0
            posX1 = 0;
        }

        if (posX2 >= phys_map[0].length) {
            posX2 = phys_map[0].length - 1;
        }

        for (int y = posY1; y < phys_map.length; y++) {//
            for (int x = posX1; x <= posX2; x++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if (rect.intersects(r)) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    public Rectangle haveCollisionWithTop(Rectangle rect) {//phương thức chặn tường bên trên 

        int posX1 = rect.x / tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / tileSize;
        posX2 += 2;

        //int posY = (rect.y + rect.height)/tileSize;
        int posY = rect.y / tileSize;

        if (posX1 < 0) {
            posX1 = 0;
        }

        if (posX2 >= phys_map[0].length) {
            posX2 = phys_map[0].length - 1;
        }

        for (int y = posY; y >= 0; y--) {
            for (int x = posX1; x <= posX2; x++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if (rect.intersects(r)) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    public Rectangle haveCollisionWithRightWall(Rectangle rect) {//phương thức chặn tường bên phải

        int posY1 = rect.y / tileSize;
        posY1 -= 2;
        int posY2 = (rect.y + rect.height) / tileSize;
        posY2 += 2;

        int posX1 = (rect.x + rect.width) / tileSize;
        int posX2 = posX1 + 3;

        if (posX2 >= phys_map[0].length) {
            posX2 = phys_map[0].length - 1;
        }

        if (posY1 < 0) {
            posY1 = 0;
        }
        if (posY2 >= phys_map.length) {
            posY2 = phys_map.length - 1;
        }

        for (int x = posX1; x <= posX2; x++) {
            for (int y = posY1; y <= posY2; y++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if (r.y < rect.y + rect.height - 1 && rect.intersects(r)) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    public Rectangle haveCollisionWithLeftWall(Rectangle rect) {//phương thức chặn tường bên trái

        int posY1 = rect.y / tileSize;
        posY1 -= 2;
        int posY2 = (rect.y + rect.height) / tileSize;
        posY2 += 2;

        int posX1 = (rect.x + rect.width) / tileSize;
        int posX2 = posX1 - 3;

        if (posX2 < 0) {
            posX2 = 0;
        }

        if (posY1 < 0) {
            posY1 = 0;
        }
        if (posY2 >= phys_map.length) {
            posY2 = phys_map.length - 1;
        }

        for (int x = posX1; x >= posX2; x--) {
            for (int y = posY1; y <= posY2; y++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if (r.y < rect.y + rect.height - 1 && rect.intersects(r)) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void Update() {
    }
}
