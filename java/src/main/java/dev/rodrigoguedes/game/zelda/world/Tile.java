package dev.rodrigoguedes.game.zelda.world;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage sprite;
    private int x;
    private int y;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, x, y, null);
    }

}
