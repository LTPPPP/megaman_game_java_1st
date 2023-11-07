/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phat.effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lam Tan Phat - CE181023
 */
public class FrameImage {

    private String name;
    private BufferedImage image;

    public FrameImage(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public FrameImage(FrameImage frameImage) {//in ra hình ảnh dựa vào vị trí của x và y

        image = new BufferedImage(frameImage.getWidthImage(),
                frameImage.getHeightImage(), frameImage.image.getType());
        Graphics g = image.getGraphics();
        g.drawImage(frameImage.getImage(), 0, 0, null);
    }

    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);//in ra hình giữa giao diện
    }

    public FrameImage() {
        this.name = null;
        image = null;
    }

    
     public int getWidthImage(){
        return image.getWidth();
    }

    public int getHeightImage(){
        return image.getHeight();
    }
    
    public int getImageWidth() {
        return image.getWidth();
    }

    public int getImageHeight() {
        return image.getHeight();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
