/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.gameobject.GameWorld;
import com.phat.effect.Animation;
import com.phat.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class DarkRaiseBullet extends Bullet {//class quản lí viên đạn của nhân vật DarkRaise

    private Animation forwardBulletAnim, backBulletAnim;

    public DarkRaiseBullet(float x, float y, GameWorld gameWorld) {//thuộc tính vật lí
        super(x, y, 30, 30, 1.0f, 50, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
        backBulletAnim.flipAllImage();
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//va chạm với nhân vật
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {//vẽ
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
