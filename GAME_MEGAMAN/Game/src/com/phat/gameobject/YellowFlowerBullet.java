/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.Animation;
import com.phat.gameobject.GameWorld;
import com.phat.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class YellowFlowerBullet extends Bullet {//class quản lí viên đạn, thuộc tính của YellowFlowerBullet

    private Animation forwardBulletAnim, backBulletAnim;

    public YellowFlowerBullet(float x, float y, GameWorld gameWorld) {
        super(x, y, 30, 30, 1.0f, 50, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("yellow_flower_bullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("yellow_flower_bullet");
        backBulletAnim.flipAllImage();
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//phương thức va chạm với kẻ địch
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {//vẽ personal camera của object
        if (getSpeedX() > 0) {
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        } else {
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
    }

    @Override
    public void Update() {
        super.Update();
    }

    @Override
    public void attack() {
    }

}
