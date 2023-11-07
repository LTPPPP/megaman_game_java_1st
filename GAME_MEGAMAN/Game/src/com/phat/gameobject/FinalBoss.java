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
import java.util.Hashtable;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class FinalBoss extends Human {//class quản lí final boss dựa theo thuộc tính của human

    //khai báo tất cả các biến
    private Animation idleforward, idleback;
    private Animation shootingforward, shootingback;
    private Animation slideforward, slideback;

    private long startTimeForAttacked;

    private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>();
    private String[] attackType = new String[4];
    private int attackIndex = 0;
    private long lastAttackTime;

    public FinalBoss(float x, float y, GameWorld gameWorld) {//lấy thông số của boss và hình ảnh từ file
        super(x, y, 110, 150, 0.1f, 500, gameWorld);
        idleback = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward.flipAllImage();

        shootingback = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward.flipAllImage();

        slideback = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward.flipAllImage();

        setDamage(10);

        attackType[0] = "NONE";
        attackType[1] = "shooting";
        attackType[2] = "jump";
        attackType[3] = "slide";

        timeAttack.put("NONE", new Long(2000));
        timeAttack.put("shooting", new Long(500));
        timeAttack.put("slide", new Long(5000));
        timeAttack.put("jump", new Long(500));

    }

    public void Update() {//phương thức update các hành động của boss

        super.Update();

        //xác định trái phải của boss
        if (getGameWorld().megaman.getPosX() > getPosX()) {
            setDirection(RIGHT_DIR);
        } else {
            setDirection(LEFT_DIR);
        }

        //set thời gian tấn công của boss dựa vào thời gian thực ( tính theo mili second )
        if (startTimeForAttacked == 0) {
            startTimeForAttacked = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - startTimeForAttacked > 10) {
            attack();
            startTimeForAttacked = System.currentTimeMillis();
        }

        if (!attackType[attackIndex].equals("NONE")) {

            if (attackType[attackIndex].equals("shooting")) {//lệnh bắn của boss

                Bullet bullet = new RocketBullet(getPosX(), getPosY() - 50, getGameWorld());
                if (getDirection() == LEFT_DIR) {
                    bullet.setSpeedX(-3);//set tốc độ viên đạn
                } else {
                    bullet.setSpeedX(3);
                }
                bullet.setTeamType(getTeamType());
                getGameWorld().bulletManager.addObject(bullet);

            } else if (attackType[attackIndex].equals("slide")) {//lệnh di chuyển_lướt qua nhân vật

                if (getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
                    setSpeedX(4);//tốc độ lướt phải
                }
                if (getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {
                    setSpeedX(-4);//tốc độ lướt trái              
                }
                setPosX(getPosX() + getSpeedX());//vị trí lướt
            }
        } else {
            setSpeedX(0);
        }
    }

    @Override
    public void run() {
    }

    @Override
    public void jump() {
        setSpeedY(-5);
    }

    @Override
    public void crouch() {
    }

    @Override
    public void standUp() {
    }

    @Override
    public void stopRun() {
    }

    @Override
    public void attack() {//phương thức tấn công

        if (System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])) {
            lastAttackTime = System.currentTimeMillis();

            attackIndex++;
            if (attackIndex >= attackType.length) {
                attackIndex = 0;
            }

            if (attackType[attackIndex].equals("slide")) {//lệnh lướt của boss
                if (getPosX() < getGameWorld().megaman.getPosX()) {
                    setSpeedX(4);
                } else {
                    setSpeedX(-4);
                }
            }
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {//phương thức lướt và gây sát thương khi va chạm_hit box
        if (attackType[attackIndex].equals("slide")) {
            Rectangle rect = getBoundForCollisionWithMap();
            rect.y += 100;
            rect.height -= 100;
            return rect;
        } else {
            return getBoundForCollisionWithMap();
        }
    }

    @Override
    public void draw(Graphics2D g2) {//phương thức vẽ các hoạt động
        if (attackType[attackIndex].equals("NONE")) {
            if (getDirection() == RIGHT_DIR) {
                idleforward.Update(System.nanoTime());
                idleforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else {
                idleback.Update(System.nanoTime());
                idleback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
        } else if (attackType[attackIndex].equals("shooting")) {//lệnh bắn                
            if (getDirection() == RIGHT_DIR) {
                shootingforward.Update(System.nanoTime());
                shootingforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else {
                shootingback.Update(System.nanoTime());
                shootingback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
        } else if (attackType[attackIndex].equals("slide")) {//lệnh lướt
            if (getSpeedX() > 0) {
                slideforward.Update(System.nanoTime());
                slideforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            } else {
                slideback.Update(System.nanoTime());
                slideback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
        }
    }
}
