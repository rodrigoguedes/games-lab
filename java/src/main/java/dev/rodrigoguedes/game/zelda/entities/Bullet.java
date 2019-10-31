package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Entity {

    private double directionX;
    private double directionY;
    private int speed = 4;

    private int life = 30;
    private int currentLife = 0;

    public Bullet(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world, double dx, double dy) {
        super(x, y, width, height, sprite, camera, world);
        this.directionX = dx;
        this.directionY = dy;
    }

    @Override
    public void tick() {
        setX(getX() + (int)(directionX * speed));
        setY(getY() + (int)(directionY * speed));
        currentLife++;
        if (currentLife == life) {
            this.getWorld().getBullets().remove(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(this.getX() - this.getCamera().getX(), this.getY() - this.getCamera().getY(), this.getWidth(), this.getHeight());
    }

}
