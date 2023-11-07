/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.Animation;
import com.phat.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class RocketBullet extends Bullet {//class quản lý viên đạn của boss

    private Animation forwardBulletAnimUp, forwardBulletAnimDown, forwardBulletAnim;
    private Animation backBulletAnimUp, backBulletAnimDown, backBulletAnim;

    private long startTimeForChangeSpeedY;

    public RocketBullet(float x, float y, GameWorld gameWorld) {//thông số các viên đạn

        super(x, y, 30, 30, 1.0f, 50, gameWorld);

        backBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
        backBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");

        forwardBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
        forwardBulletAnimUp.flipAllImage();
        forwardBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
        forwardBulletAnimDown.flipAllImage();
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");
        forwardBulletAnim.flipAllImage();

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//va chạm với kẻ thù ( khác team )     
        return getBoundForCollisionWithMap();     
    }

    @Override
    public void draw(Graphics2D g2) {//phương thức vẽ viên đạn của final boss, hướng di chuyển và quỹ đạo của nó
        if (getSpeedX() > 0) {
            if (getSpeedY() > 0) {
                forwardBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else if (getSpeedY() < 0) {
                forwardBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else {
                forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
        } else {
            if (getSpeedY() > 0) {
                backBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else if (getSpeedY() < 0) {
                backBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else {
                backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
        }
    }

    private void changeSpeedY() {//phương thức thay đổi quỹ đạo lên xuống của viên đạn
        if (System.currentTimeMillis() % 5 == 0) {
            setSpeedY(getSpeedX());
        } else if (System.currentTimeMillis() % 5 == 1) {
            setSpeedY(-getSpeedX());
        } else {
            setSpeedY(0);
        }
    }

    @Override
    public void Update() {//phương thức update các hành động

        super.Update();
            if (System.nanoTime() - startTimeForChangeSpeedY > 500000000 * 1000000) {
            startTimeForChangeSpeedY = System.nanoTime();
            changeSpeedY();
        }
    }

    @Override
    public void attack() {
    }

}
