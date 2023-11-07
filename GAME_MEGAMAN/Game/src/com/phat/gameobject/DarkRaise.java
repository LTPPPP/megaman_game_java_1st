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
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class DarkRaise extends ParticularObject {//class nhân vật DarkRaise

    private Animation forwardAnim, backAnim;

    private long startTimeToShoot;
    private float x1, x2;

    public DarkRaise(float x, float y, GameWorld gameWorld) {//thuộc tính vật lí
        super(x, y, 127, 89, 30, 0, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setTimeForNoBehurt(300000000);

        x1 = x - 100;
        x2 = x + 100;
        setSpeedX(1);

        setDamage(10);//sát thương nhận khi va chạm
    }

    @Override
    public void attack() {//tấn công

        float megaManX = getGameWorld().megaman.getPosX();
        float megaManY = getGameWorld().megaman.getPosY();

        float deltaX = megaManX - getPosX();
        float deltaY = megaManY - getPosY();

        float speed = 3;
        float a = Math.abs(deltaX / deltaY);

        float speedX = (float) Math.sqrt(speed * speed * a * a / (a * a + 1));
        float speedY = (float) Math.sqrt(speed * speed / (a * a + 1));

        Bullet bullet = new DarkRaiseBullet(getPosX(), getPosY(), getGameWorld());

        if (deltaX < 0) {//tốc độ đạn
            bullet.setSpeedX(-speedX);
        } else {
            bullet.setSpeedX(speedX);
        }
        bullet.setSpeedY(speedY);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    public void Update() {
        super.Update();
        if (getPosX() < x1) {
            setSpeedX(1);
        } else if (getPosX() > x2) {
            setSpeedX(-1);
        }
        setPosX(getPosX() + getSpeedX());

        if (System.nanoTime() - startTimeToShoot > 4000000000L) {//delay mỗi viên đạn bắn ra
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
