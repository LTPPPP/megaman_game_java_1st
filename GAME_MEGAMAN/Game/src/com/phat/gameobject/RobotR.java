/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.gameobject.GameWorld;
import com.phat.effect.Animation;
import com.phat.effect.CacheDataLoader;
import static com.phat.gameobject.ParticularObject.LEFT_DIR;
import static com.phat.gameobject.ParticularObject.NOBEHURT;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class RobotR extends ParticularObject {//class nhân vật RobotR

    private Animation forwardAnim, backAnim;

    private long startTimeToShoot;
    private float x1, x2, y1, y2;

    private AudioClip shooting;

    public RobotR(float x, float y, GameWorld gameWorld) {//thuộc tính vật lí
        super(x, y, 127, 89, 0, 50, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("robotR");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("robotR");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setTimeForNoBehurt(300000000);
        setDamage(25);//sát thương nhận khi va chạm

        x1 = x - 100;
        x2 = x + 100;
        y1 = y - 50;
        y2 = y + 50;

        setSpeedX(1);
        setSpeedY(1);

        shooting = CacheDataLoader.getInstance().getSound("robotRshooting");
    }

    @Override
    public void attack() {//tấn công

        shooting.play();
        Bullet bullet = new RobotRBullet(getPosX(), getPosY(), getGameWorld());

        if (getDirection() == LEFT_DIR) {//tốc độ viên đạn
            bullet.setSpeedX(3);
        } else {
            bullet.setSpeedX(-3);
        }
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    public void Update() {//phương thức update cho vị trí bắn và tốc độ bắn

        super.Update();

        //check xem nhân vật đang ở đâu
        if (getPosX() - getGameWorld().megaman.getPosX() > 0) {
            setDirection(ParticularObject.RIGHT_DIR);
        } else {
            setDirection(ParticularObject.LEFT_DIR);
        }

        if (getPosX() < x1) {
            setSpeedX(1);
        } else if (getPosX() > x2) {
            setSpeedX(-1);
        }
        setPosX(getPosX() + getSpeedX());

        if (getPosY() < y1) {
            setSpeedY(1);
        } else if (getPosY() > y2) {
            setSpeedY(-1);
        }
        setPosY(getPosY() + getSpeedY());

        if (System.nanoTime() - startTimeToShoot > 4000000000L) {//tốc độ bắn
            attack();
            startTimeToShoot = System.nanoTime();
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//va chạm với kẻ địch
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;

        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {//vẽ camera
        if (!isObjectOutOfCameraView()) {
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
