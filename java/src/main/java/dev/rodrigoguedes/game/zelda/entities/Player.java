package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.pong.Game;

import java.awt.image.BufferedImage;

public class Player extends Entity {

    private boolean right;
    private boolean left;
    private boolean down;
    private boolean up;
    private int speed = 1;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    @Override
    public void tick() {
        if (right) {
            this.setX(this.getX() + speed);
        } else if (left) {
            this.setX(this.getX() - speed);
        } else if (up) {
            this.setY(this.getY() - speed);
        } else if (down) {
            this.setY(this.getY() + speed);
        }

        if (this.getX() + this.getWidth() > Game.WIDTH) {
            this.setX(Game.WIDTH - this.getWidth());
        } else if (this.getX() < 0) {
            this.setX(0);
        }
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

    public void moveToDown() {
        this.down = true;
    }

    public void moveToUp() {
        this.up = true;
    }

    public void stopMoveToDown() {
        this.down = false;
    }

    public void stopMoveToUp() {
        this.up = false;
    }

}
