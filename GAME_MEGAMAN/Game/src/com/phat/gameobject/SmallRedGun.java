/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.Animation;
import com.phat.effect.CacheDataLoader;
import static com.phat.gameobject.ParticularObject.LEFT_DIR;
import static com.phat.gameobject.ParticularObject.NOBEHURT;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author LTP
 */
public class SmallRedGun extends ParticularObject {//class nhân vật SmallRedGun

    private Animation forwardAnim, backAnim;

    private long startTimeToShoot;

    public SmallRedGun(float x, float y, GameWorld gameWorld) {
        super(x, y, 127, 89, 0, 50, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setTimeForNoBehurt(300000000);
    }

    @Override
    public void attack() {//phương thức tấn công

        Bullet bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(-3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

        bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
    }

    public void Update() {//tốc độ bắn
        super.Update();
        if (System.nanoTime() - startTimeToShoot > 1000 * 10000000 * 2.0) {
            attack();
            System.out.println("Red Eye attack");
            startTimeToShoot = System.nanoTime();
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//va chạm
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;

        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {//phương thức camera của ụ súng
        if (!isObjectOutOfCameraView()) {
            if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
                // plash...
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
