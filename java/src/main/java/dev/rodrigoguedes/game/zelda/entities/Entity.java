package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    private int x;
    private int y;
    private int width;
    private int height;

    private int maskX;
    private int maskY;
    private int maskW;
    private int maskH;

    private Camera camera;
    private World world;

    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.camera = camera;
        this.world = world;

        this.maskX = 0;
        this.maskY = 0;
        this.maskW = this.width;
        this.maskH = this.height;
    }

    public boolean isColidding(Entity e1,Entity e2){
        Rectangle e1Mask = new Rectangle(getX() + getMaskX(),e1.getY() + getMaskY(),getMaskW(), getMaskH());
        Rectangle e2Mask = new Rectangle(getX() + getMaskX(),e2.getY() + getMaskY(),getMaskW(), getMaskH());

        return e1Mask.intersects(e2Mask);
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, getX() - this.camera.getX(), getY() - this.camera.getY(), null);
        graphics.setColor(Color.red);
        graphics.fillRect(this.getX() + getMaskX() - this.camera.getX(),this.getY() + this.getMaskY() - this.camera.getY(),getMaskW(),getMaskH());
    }

    public void tick() {
    }

    public Camera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaskX() {
        return maskX;
    }

    public void setMaskX(int maskX) {
        this.maskX = maskX;
    }

    public int getMaskY() {
        return maskY;
    }

    public void setMaskY(int maskY) {
        this.maskY = maskY;
    }

    public int getMaskW() {
        return maskW;
    }

    public void setMaskW(int maskW) {
        this.maskW = maskW;
    }

    public int getMaskH() {
        return maskH;
    }

    public void setMaskH(int maskH) {
        this.maskH = maskH;
    }
}
