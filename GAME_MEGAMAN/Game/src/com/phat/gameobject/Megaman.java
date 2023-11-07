/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.Animation;
import com.phat.effect.CacheDataLoader;
import com.phat.gameobject.GameWorld;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class Megaman extends Human {//thuộc tính vật lý của nhân vật
    //với x, y là tọa độ gôc lấy từ gốc màn hình trái bên trên

//    public static final int RUNSPEED = 0;//khai báo biến tốc độ chạy
    //khai báo các thuộc tính
    private Animation runForwardAnim, runBackAnim, runShootingForwarAnim, runShootingBackAnim;
    private Animation idleForwardAnim, idleBackAnim, idleShootingForwardAnim, idleShootingBackAnim;
    private Animation crouchForwardAnim, crouchBackAnim;
    private Animation flyForwardAnim, flyBackAnim, flyShootingForwardAnim, flyShootingBackAnim;
    private Animation landingForwardAnim, landingBackAnim;

    private Animation climWallForward, climWallBack;

    private long lastShootingTime;
    private boolean isShooting = false;

    private AudioClip hurtingSound;
    private AudioClip shooting1;

    public Megaman(float x, float y, GameWorld gameWorld) {
        super(x, y, 70, 90, 0.1f, 100, gameWorld);

        shooting1 = CacheDataLoader.getInstance().getSound("bluefireshooting");
        hurtingSound = CacheDataLoader.getInstance().getSound("megamanhurt");
        setTeamType(LEAGUE_TEAM);//set team thuộc bên nào

        setTimeForNoBehurt(2 * 1000000);//điều chỉnh thời gian bất tử

        //khai báo thuộc tính chạy
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim.flipAllImage();

        //khai báo thuộc tính đứng idle
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim.flipAllImage();

        //khai báo thuộc tính crouch
        crouchForwardAnim = CacheDataLoader.getInstance().getAnimation("crouch");
        crouchBackAnim = CacheDataLoader.getInstance().getAnimation("crouch");
        crouchBackAnim.flipAllImage();

        //khai báo thuộc tính bay, nhảy
        flyForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        flyForwardAnim.setIsRepeated(false);
        flyBackAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        flyBackAnim.setIsRepeated(false);
        flyBackAnim.flipAllImage();

        //khsi báo thuộc tính thuộc tính đáp 
        landingForwardAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim.flipAllImage();

        //khai báo thuộc tính leo trèo
        climWallBack = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward.flipAllImage();

        //khai báo thuộc tính bị thương, bị dính dame
        behurtForwardAnim = CacheDataLoader.getInstance().getAnimation("hurting");
        behurtBackAnim = CacheDataLoader.getInstance().getAnimation("hurting");
        behurtBackAnim.flipAllImage();

        //khai báo thuộc tính bắn
        idleShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim.flipAllImage();

        //khai báo thuộc tính chạy bắn
        runShootingForwarAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim.flipAllImage();

        //khai báo thuộc tính bay/nhảy bắn
        flyShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim.flipAllImage();
    }

    @Override
    public void Update() {

        super.Update();

        if (isShooting) {//phương thức kiểm tra xem nhân vật có đang bắn ra đạn hay là không
            if (System.nanoTime() - lastShootingTime > 500 * 1000000) {//bắn ra viên đạn cuối cùng
                isShooting = false;
            }
        }

        if (getIsLanding()) {
            landingBackAnim.Update(System.nanoTime());
            if (landingBackAnim.isLastFrame()) {
                setIsLanding(false);
                landingBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
            }
        }

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        //tạo va chạm cho nhân vật
        Rectangle rect = getBoundForCollisionWithMap();
        if (getIsCrouching()) {//xét điều kiện giá trị khi bò
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 20;
            rect.width = 44;
            rect.height = 65;
        } else {
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 40;
            rect.width = 44;
            rect.height = 80;
        }
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {

        switch (getState()) {//phương thức các trạng thái của nhân vật
            case ALIVE://trạng thái khi còn sống
            case NOBEHURT://trạng thái bất tử
                if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
                } else {
                    if (getIsLanding()) {//trạng thái đáp đất

                        if (getDirection() == RIGHT_DIR) {//xác định hướng quay mặt của nhân vật
                            landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
                            landingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height / 2 - landingForwardAnim.getCurrentImage().getHeight() / 2),
                                    g2);
                        } else {
                            landingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height / 2 - landingBackAnim.getCurrentImage().getHeight() / 2),
                                    g2);
                        }

                    } else if (getIsJumping()) {//trạng thái nhảy
                        if (getDirection() == RIGHT_DIR) {//xác định hướng quay mặt của nhân vật
                            flyForwardAnim.Update(System.nanoTime());
                            if (isShooting) {
                                flyShootingForwardAnim.setCurrentFrame(flyForwardAnim.getCurrentFrame());
                                flyShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()) + 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            } else {
                                flyForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                        } else {
                            flyBackAnim.Update(System.nanoTime());
                            if (isShooting) {
                                flyShootingBackAnim.setCurrentFrame(flyBackAnim.getCurrentFrame());
                                flyShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()) - 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            } else {
                                flyBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                        }
                    } else if (getIsCrouching()) {//trạng thái bò

                        if (getDirection() == RIGHT_DIR) {//xác định hướng quay mặt của nhân vật
                            crouchForwardAnim.Update(System.nanoTime());
                            crouchForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height / 2 - crouchForwardAnim.getCurrentImage().getHeight() / 2),
                                    g2);
                        } else {
                            crouchBackAnim.Update(System.nanoTime());
                            crouchBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height / 2 - crouchBackAnim.getCurrentImage().getHeight() / 2),
                                    g2);
                        }

                    } else {//trạng thái chạy
                        if (getSpeedX() > 0) {
                            runForwardAnim.Update(System.nanoTime());
                            if (isShooting) {
                                runShootingForwarAnim.setCurrentFrame(runForwardAnim.getCurrentFrame() - 1);
                                runShootingForwarAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            } else {
                                runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                            if (runForwardAnim.getCurrentFrame() == 1) {
                                runForwardAnim.setIgnoreFrame(0);
                            }
                        } else if (getSpeedX() < 0) {
                            runBackAnim.Update(System.nanoTime());
                            if (isShooting) {
                                runShootingBackAnim.setCurrentFrame(runBackAnim.getCurrentFrame() - 1);
                                runShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            } else {
                                runBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                            if (runBackAnim.getCurrentFrame() == 1) {
                                runBackAnim.setIgnoreFrame(0);
                            }
                        } else {
                            if (getDirection() == RIGHT_DIR) {
                                if (isShooting) {
                                    idleShootingForwardAnim.Update(System.nanoTime());
                                    idleShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                } else {
                                    idleForwardAnim.Update(System.nanoTime());
                                    idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            } else {
                                if (isShooting) {
                                    idleShootingBackAnim.Update(System.nanoTime());
                                    idleShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                } else {
                                    idleBackAnim.Update(System.nanoTime());
                                    idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }
                        }
                    }
                }
                break;

            case BEHURT://trạng thái bị thương ( bị gây dame )
                if (getDirection() == RIGHT_DIR) {
                    behurtForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                } else {
                    behurtBackAnim.setCurrentFrame(behurtForwardAnim.getCurrentFrame());
                    behurtBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;
        }
    }

    @Override
    public void run() {//phương thức chạy
        if (getDirection() == LEFT_DIR) {//tốc độ chạy ( run )
            setSpeedX(-5);
        } else {
            setSpeedX(5);
        }
    }

    @Override
    public void jump() {//phương thức nhảy

        if (!getIsJumping()) {
            setIsJumping(true);
            setSpeedY(-5.0f);//độ cao nhảy
            flyBackAnim.reset();
            flyForwardAnim.reset();
        } // for clim wall
        else {
            Rectangle rectRightWall = getBoundForCollisionWithMap();
            rectRightWall.x += 1;
            Rectangle rectLeftWall = getBoundForCollisionWithMap();
            rectLeftWall.x -= 1;
            if (getGameWorld().physicalMap.haveCollisionWithRightWall(rectRightWall) != null && getSpeedX() > 0) {
                setSpeedY(-5.0f);
                flyBackAnim.reset();
                flyForwardAnim.reset();
            } else if (getGameWorld().physicalMap.haveCollisionWithLeftWall(rectLeftWall) != null && getSpeedX() < 0) {
                setSpeedY(-5.0f);
                flyBackAnim.reset();
                flyForwardAnim.reset();
            }
        }
    }

    @Override
    public void crouch() {//phương thức bò, ngồi
        if (!getIsJumping()) {
            setIsCrouching(true);
        }
    }

    @Override
    public void standUp() {//phương thức đứng lên
        setIsCrouching(false);
        idleForwardAnim.reset();
        idleBackAnim.reset();
        crouchForwardAnim.reset();
        crouchBackAnim.reset();
    }

    @Override
    public void stopRun() {//phương thức dừng chạy
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);
    }

    @Override
    public void attack() {//phương thức tấn công

        if (!isShooting && !getIsCrouching()) {

            shooting1.play();
            Bullet bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
            if (getDirection() == LEFT_DIR) {//xét hướng quay mặt để bắ
                bullet.setSpeedX(-10);//tốc độ viên đạn
                bullet.setPosX(bullet.getPosX() - 40);//vị trí viên đạn
                if (getSpeedX() != 0 && getSpeedY() == 0) {//nếu chưa bắn
                    bullet.setPosX(bullet.getPosX() - 10);//xác định vị trí viên đạn khi bắn ra
                    bullet.setPosY(bullet.getPosY() - 5);//xác định vị trí viên đạn khi bắn ra
                }
            } else {
                bullet.setSpeedX(10);//tốc độ viên đạn
                bullet.setPosX(bullet.getPosX() + 40);
                if (getSpeedX() != 0 && getSpeedY() == 0) {//nếu chưa bắn
                    bullet.setPosX(bullet.getPosX() + 10);//xác định vị trí viên đạn khi bắn ra
                    bullet.setPosY(bullet.getPosY() - 5);//xác định vị trí viên đạn khi bắn ra
                }
            }
            if (getIsJumping()) {//nhảy bắn
                bullet.setPosY(bullet.getPosY() - 20);
            }

            bullet.setTeamType(getTeamType());
            getGameWorld().bulletManager.addObject(bullet);

            lastShootingTime = System.nanoTime();//set viên đạn bắn cuối cùng bằng với thời gian thực
            isShooting = true;

        }
    }

    @Override
    public void hurtingCallback() {
        System.out.println("Call back hurting");
        hurtingSound.play();
    }
}
