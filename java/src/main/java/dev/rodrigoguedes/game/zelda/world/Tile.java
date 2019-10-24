package dev.rodrigoguedes.game.zelda.world;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private Camera camera;

    private BufferedImage sprite;
    private int x;
    private int y;

    public Tile(int x, int y, BufferedImage sprite, Camera camera) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.camera = camera;
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, x - this.camera.getX(), y - this.camera.getY(), null);
    }

}
