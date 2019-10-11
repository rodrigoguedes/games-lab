package dev.rodrigoguedes.game.pong;

import java.awt.*;

public class Player {

    private boolean right;
    private boolean left;

    private int x;
    private int y;
    private int width = 40;
    private int height = 10;

    private int speed = 1;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {
        if (right) {
            x += speed;
        } else if (left) {
            x -= speed;
        }

        if (x + width > Game.WIDTH) {
            x = Game.WIDTH - width;
        } else if (x < 0) {
            x = 0;
        }
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillRect(x, y, width, height);
    }

    public void moveToRight() {
        this.right = true;
    }

    public void moveToLeft() {
        this.left = true;
    }

    public void stopMoveToRight() {
        this.right = false;
    }

    public void stopMoveToLeft() {
        this.left = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
