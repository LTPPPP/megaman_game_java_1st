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
public class RobotRBullet extends Bullet {//class quản lí viên đạn của RobotR

    private Animation forwardBulletAnim, backBulletAnim;

    public RobotRBullet(float x, float y, GameWorld gameWorld) {//thuộc tính viên đạn
        super(x, y, 60, 30, 1.0f, 50, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
        backBulletAnim.flipAllImage();
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//va chạm với kẻ địch của viên đạn
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {//vẽ đối tượng RobotR
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
