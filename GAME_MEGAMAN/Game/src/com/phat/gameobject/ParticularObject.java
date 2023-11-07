/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import com.phat.effect.Animation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public abstract class ParticularObject extends GameObject {//Class tất cả các object-thuộc tính 

    //xét phân chia thành 2 đối tượng chính ( 1 bên đồng minh/ 1 bên phản diện )
    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;

    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;
    public static final int A_DIR = 0;
    public static final int D_DIR = 1;

    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int DEATH = 3;
    public static final int NOBEHURT = 4;//miễn dame
    private int state = ALIVE;//biến để xác định coi nhân vật đang thuộc trong trạng thái nào

    //các thuộc tính chung của các object
    private float width;
    private float height;
    private float mass;
    private float speedX;
    private float speedY;
    private int blood;

    private int damage;//sát thương của mỗi object

    private int direction;//hướng di chuyển của mỗi object/xác định quay mặt về hướng nào

    protected Animation behurtForwardAnim, behurtBackAnim;

    private int teamType;//xác định team nào 

    //thời gian bất tử( immortal )
    private long startTimeNoBeHurt;
    private long timeForNoBeHurt;

    public ParticularObject(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {

        // posX and posY are the middle coordinate of the object
        super(x, y, gameWorld);
        setWidth(width);
        setHeight(height);
        setMass(mass);
        setBlood(blood);

        direction = RIGHT_DIR;

    }

    public void setTimeForNoBehurt(long time) {
        timeForNoBeHurt = time;
    }

    public long getTimeForNoBeHurt() {
        return timeForNoBeHurt;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setTeamType(int team) {
        teamType = team;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getMass() {
        return mass;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setBlood(int blood) {
        if (blood >= 0) {
            this.blood = blood;
        } else {
            this.blood = 0;
        }
    }

    public int getBlood() {
        return blood;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    public int getDirection() {
        return direction;
    }

    public abstract void attack();

    public boolean isObjectOutOfCameraView() {//kiểm tra xem nhân vật có nằm trong vùng camera hay là ở ngoài
        if (getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView()
                || getPosX() - getGameWorld().camera.getPosX() < -50
                || getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView()
                || getPosY() - getGameWorld().camera.getPosY() < -50) {
            return true;
        } else {
            return false;
        }
    }

    public Rectangle getBoundForCollisionWithMap() {// phương thức đi được trên mặt đất ko bị rơi tự do
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth() / 2));
        bound.y = (int) (getPosY() - (getHeight() / 2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void beHurt(int damgeEat) {//phương thức bị dính dame
        setBlood(getBlood() - damgeEat);
        state = BEHURT;
        hurtingCallback();
    }

    @Override
    public void Update() {
        switch (state) {

            case ALIVE:  //khi còn sống            
                // note: SET DAMAGE FOR OBJECT NO DAMAGE
                ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                if (object != null) {
                    if (object.getDamage() > 0) {
                        // switch state to fey if object die
                        System.out.println("eat damage.... from collision with enemy........ " + object.getDamage());
                        beHurt(object.getDamage());
                    }
                }
                break;

            case BEHURT://khi bị thương
                if (behurtBackAnim == null) {
                    state = NOBEHURT;
                    startTimeNoBeHurt = System.nanoTime();
                    if (getBlood() == 0) {
                        state = DEATH;
                    }
                } else {
                    behurtForwardAnim.Update(System.nanoTime());
                    if (behurtForwardAnim.isLastFrame()) {//nếu bị thương gần chết thì reset lại
                        behurtForwardAnim.reset();
                        state = NOBEHURT;
                        if (getBlood() == 0) {
                            state = DEATH;
                        }
                        startTimeNoBeHurt = System.nanoTime();
                    }
                }
                break;
            case NOBEHURT://khi bất tử
                System.out.println("immortal");
                if (System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt)//từ trạng thái bất tử chuyển sang sống lại từ đầu
                {
                    state = ALIVE;
                }
                break;
        }
    }

    public void drawBoundForCollisionWithMap(Graphics2D g2) {//phương thức va chạm giữa các object với mặt đất
        Rectangle rect = getBoundForCollisionWithMap();
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2) {//phương thức va chạm giữa các object với địch
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public abstract Rectangle getBoundForCollisionWithEnemy();

    public abstract void draw(Graphics2D g2);

    public void hurtingCallback() {
    }
;
}
