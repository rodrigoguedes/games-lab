package dev.rodrigoguedes.game.pong;

import java.awt.*;

public class Enemy {

    private double x;
    private double y;
    private int width = 40;
    private int height = 10;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick(Ball ball) {
        this.x += (ball.getX() - this.x - 6) * 0.07;
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect((int)x, (int)y, width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
