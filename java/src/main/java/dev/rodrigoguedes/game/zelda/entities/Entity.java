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
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, getX() - this.camera.getX(), getY() - this.camera.getY(), null);
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
}
