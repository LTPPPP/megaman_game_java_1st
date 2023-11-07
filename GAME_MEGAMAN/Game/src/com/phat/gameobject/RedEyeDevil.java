/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.Animation;
import com.phat.effect.CacheDataLoader;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class RedEyeDevil extends ParticularObject {//class quản lý nhân vật Redeye

    private Animation forwardAnim, backAnim;

    private long startTimeToShoot;

    private AudioClip shooting;

    public RedEyeDevil(float x, float y, GameWorld gameWorld) {//phương thức nhân vật quái
        super(x, y, 127, 89, 0, 70, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setDamage(100);//khi chạm-bị gây sát thương- bị - máu
        shooting = CacheDataLoader.getInstance().getSound("redeyeshooting");
    }

    @Override
    public void attack() {//phương thức tấn công của quái

        shooting.play();

        Bullet bullet = new RedEyeBullet(getPosX(), getPosY(), getGameWorld());
        if (getDirection() == LEFT_DIR) {//tốc độ viên đạn
            bullet.setSpeedX(-2);
        } else {
            bullet.setSpeedX(2);
        }
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    public void Update() {//delay tốc độ bắn giữa các viên đạn với nhau
        super.Update();
        if (System.nanoTime() - startTimeToShoot > 4000000000L) {
            attack();
            System.out.println("Red Eye attack");
            startTimeToShoot = System.nanoTime();
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//phương thức va chạm với kẻ thù( khác team )
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {//phương thức vẽ nhân vật
        if (!isObjectOutOfCameraView()) {//nếu nằm ngoài tầm camera
            if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
            } else {
                if (getDirection() == LEFT_DIR) {
                    backAnim.Update(System.nanoTime());
                    backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                            (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                } else {
                    forwardAnim.Update(System.nanoTime());
                    forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                            (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
    }

}
