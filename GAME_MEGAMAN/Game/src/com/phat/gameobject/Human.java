/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public abstract class Human extends ParticularObject {//class chứa các thuộc tính vật lý

    private boolean isJumping;//có đang trạng thái đang nhảy hay ko
    private boolean isCrouching;//có đang trạng thái đang ngồi hay ko

    private boolean isLanding;//có đang trạng thái đáp hay ko

    public Human(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
        super(x, y, width, height, mass, blood, gameWorld);
        setState(ALIVE);
    }

    public abstract void run();

    public abstract void jump();

    public abstract void crouch();

    public abstract void standUp();

    public abstract void stopRun();

    public boolean getIsJumping() {
        return isJumping;
    }

    public void setIsLanding(boolean b) {
        isLanding = b;
    }

    public boolean getIsLanding() {
        return isLanding;
    }

    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean getIsCrouching() {
        return isCrouching;
    }

    public void setIsCrouching(boolean isCrouching) {
        this.isCrouching = isCrouching;
    }

    @Override
    public void Update() {

        super.Update();

        if (getState() == ALIVE) {

            if (!isLanding) {//nếu ko tiếp đất ( trạng thái ko thể làm gì )
                setPosX(getPosX() + getSpeedX());//tiếp tục việc di chuyển

                //xét hướng quay mặt coi có va chạm với vật thể bên trái hay là ko, nếu ko có gì thì thực hiện tiếp
                if (getDirection() == LEFT_DIR
                        && getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {

                    Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);//xét có đụng vào tường bên trái hay là ko
                }

                //xét hướng quay mặt coi có va chạm với vật thể bên phải hay là ko, nếu ko có gì thì thực hiện tiếp
                if (getDirection() == RIGHT_DIR
                        && getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {

                    Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    setPosX(rectRightWall.x - getWidth() / 2);

                }

                //xác định vùng tiếp xúc của nhân vật và map trong tương lai, điểm đáp là ở đâu, có hay là ko( có thể bay lên )
                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);

                Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);

                Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTop(boundForCollisionWithMapFuture);

                if (rectTop != null) {//xét coi có chạm bên trên ko
                    setSpeedY(0);//set vận tốc thành 0 và dừng lại
                    setPosY(rectTop.y + getGameWorld().physicalMap.getTileSize() + getHeight() / 2);
                } else if (rectLand != null) {//trạng thái rớt khi mà chạm bên trên
                    setIsJumping(false);
                    if (getSpeedY() > 0) {//nếu mà trạng thái rơi tăng thì là đang rơi
                        setIsLanding(true);
                    }
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight() / 2 - 1);
                } else {
                    setIsJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }
        }
    }
}
