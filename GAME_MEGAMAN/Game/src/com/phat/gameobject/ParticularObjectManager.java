/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.gameobject;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class ParticularObjectManager {//Class quản lý tất cả các object-thuộc tính 

    protected List<ParticularObject> particularObjects;//đưa tất cả các object vào trong 1 cái list

    private GameWorld gameWorld;

    public ParticularObjectManager(GameWorld gameWorld) {
        //đồng bộ(synchronized) hóa dữ liệu -- theo thứ tự -- ko bị nghẽn dữ liệu và lộn xộn khi lấy dữ liệu sử dụng
        particularObjects = Collections.synchronizedList(new LinkedList<ParticularObject>());
        this.gameWorld = gameWorld;

    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void addObject(ParticularObject particularObject) {//phương thức add ( thêm ) các object vào list

        synchronized (particularObjects) {
            particularObjects.add(particularObject);
        }

    }

    public void RemoveObject(ParticularObject particularObject) {//phương thức remove( bỏ ) các object vào list
        synchronized (particularObjects) {
            for (int id = 0; id < particularObjects.size(); id++) {
                ParticularObject object = particularObjects.get(id);
                if (object == particularObject) {
                    particularObjects.remove(id);
                }

            }
        }
    }

    //phương thức trả về đối tượng có va chạm với đối tượng khác-- nếu có va chạm thì trả về cái object nếu không có va chạm thì trả về null
    public ParticularObject getCollisionWidthEnemyObject(ParticularObject object) {
        synchronized (particularObjects) {
            for (int id = 0; id < particularObjects.size(); id++) {
                ParticularObject objectInList = particularObjects.get(id);
                if (object.getTeamType() != objectInList.getTeamType()
                        && object.getBoundForCollisionWithEnemy().intersects(objectInList.getBoundForCollisionWithEnemy())) {
                    return objectInList;
                }
            }
        }
        return null;
    }

    public void UpdateObjects() {//phương thức update các giá trị được đưa vào list
        synchronized (particularObjects) {
            for (int id = 0; id < particularObjects.size(); id++) {
                ParticularObject object = particularObjects.get(id);

                if (!object.isObjectOutOfCameraView()) {
                    object.Update();
                }

                if (object.getState() == ParticularObject.DEATH) {//kiểm tra xem khi vào trạng thái DEATH ( chết ) thì xóa bỏ các object đó 
                    particularObjects.remove(id);
                }
            }
        }
    }

    public void draw(Graphics2D g2) {//phương thức để vẽ các object đã đưa vào
        synchronized (particularObjects) {
            for (ParticularObject object : particularObjects) {
                if (!object.isObjectOutOfCameraView()) {
                    object.draw(g2);
                }
            }
        }
    }

}
