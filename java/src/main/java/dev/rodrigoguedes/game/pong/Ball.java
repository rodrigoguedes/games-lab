package dev.rodrigoguedes.game.pong;

import java.awt.*;
import java.util.Random;

public class Ball {

    private double x;
    private double y;
    private int width = 4;
    private int height = 4;

    private double dx;
    private double dy;
    private double speed = 1.5;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;

//        this.dx = new Random().nextGaussian();
//        this.dy = new Random().nextGaussian();

        int angle = new Random().nextInt(75) + 46;
        dx = Math.cos(Math.toRadians(angle));
        dy = Math.sin(Math.toRadians(angle));
    }

    public void tick(Player player, Enemy enemy) {

        if (x+ (dx*speed) + width >= Game.WIDTH) {
            this.dx *= -1;
        } else if (x+(dx * speed) < 0) {
            dx *= -1;
        }

        if (y >= Game.HEIGHT) {
            //Enemy Win
        } else if (y < 0) {
            //Player Win
        }

        Rectangle bounds = new Rectangle((int)(x+(dx*speed)), (int)(y+(dy*speed)), width, height);

        Rectangle boundsPlayer = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        Rectangle boundsEnemy = new Rectangle((int)enemy.getX(), (int)enemy.getY(), enemy.getWidth(), enemy.getHeight());

        if (bounds.intersects(boundsPlayer)) {
            int angle = new Random().nextInt(75) + 46;
            dx = Math.cos(Math.toRadians(angle));
            dy = Math.sin(Math.toRadians(angle));
            if (dy > 0) {
                dy *= -1;
            }
        } else if (bounds.intersects(boundsEnemy)) {
            int angle = new Random().nextInt(75) + 46;
            dx = Math.cos(Math.toRadians(angle));
            dy = Math.sin(Math.toRadians(angle));
            if (dy < 0) {
                dy *= -1;
            }
        }

        this.x += dx*speed;
        this.y += dy*speed;
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.YELLOW);
        graphics.fillRect((int)x, (int)y, width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
