/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;
import com.phat.gameobject.GameWorld    ;
import java.awt.Graphics2D;
/**
 *
 * @author Lam Tan Phat - CE181023
 */
public abstract class Bullet extends ParticularObject {//class khai báo các thuộc tính của đạn( Bullet )

    public Bullet(float x, float y, float width, float height, float mass, int damage, GameWorld gameWorld) {//phương thức khai báo thông số và vẽ viên đạn
            super(x, y, width, height, mass, 1, gameWorld);
            setDamage(damage);
    }
    
    public abstract void draw(Graphics2D g2d);

    public void Update(){//update các thông số của đạn
        super.Update();
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());        
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.beHurt(getDamage());
            System.out.println("Bullet set behurt for enemy");
        }
    }
    
}
