package dev.rodrigoguedes.game.zelda.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

public class Player extends Entity {

    private boolean right;
    private boolean left;
    private boolean down;
    private boolean up;

    private int right_dir = 0;
    private int left_dir = 1;
    private int up_dir = 2;
    private int down_dir = 3;
    private int dir = right_dir;
    
    private int speed = 1;

    private int frames = 0;
    private int maxFrames = 7;
    private int index = 0;
    private int maxIndex = 2;
    private boolean moved = false;
    
    private BufferedImage[] rightPlayer = new BufferedImage[4];
    private BufferedImage[] leftPlayer = new BufferedImage[4];
    private BufferedImage[] upPlayer = new BufferedImage[4];
    private BufferedImage[] downPlayer = new BufferedImage[4];
    
    public Player(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        super(x, y, width, height, sprite, camera, world);

        for (int i = 0; i < 4; i++) {
        	rightPlayer[i] = Game.spritesheet.getSprite((i * 16) + (16 * 1), (16 * 5), 16, 16);
		}

        for (int i = 0; i < 4; i++) {
        	leftPlayer[i] = Game.spritesheet.getSprite((i * 16) + (16 * 6), (16 * 8), 16, 16);
		}
        
        for (int i = 0; i < 4; i++) {
        	upPlayer[i] = Game.spritesheet.getSprite((i * 16) + (16 * 7), (16 * 5), 16, 16);
		}
        
        for (int i = 0; i < 4; i++) {
        	downPlayer[i] = Game.spritesheet.getSprite((i * 16) + (16 * 4), (16 * 5), 16, 16);
		}        
    }

    @Override
    public void tick() {
    	this.moved = false;
        if (right && this.getWorld().isFree(this.getX() + speed, this.getY())) {
        	this.moved = true;
        	this.dir = right_dir;
            this.setX(this.getX() + speed);
        } else if (left && this.getWorld().isFree(this.getX() - speed, this.getY())) {
        	this.moved = true;
        	this.dir = left_dir;
            this.setX(this.getX() - speed);
        } else if (up && this.getWorld().isFree(this.getX(), this.getY() - speed)) {
        	this.moved = true;
        	this.dir = up_dir;
            this.setY(this.getY() - speed);
        } else if (down && this.getWorld().isFree(this.getX(), this.getY() + speed)) {
        	this.moved = true;
        	this.dir = down_dir;
            this.setY(this.getY() + speed);
        }

        if (moved) {
        	this.frames++;
        	if (this.frames == this.maxFrames) {
        		this.frames = 0;
        		this.index++;
        		if (this.index > this.maxIndex) {
        			this.index = 0;
        		}
        	}
        }

        this.getCamera().setX(Camera.clamp(this.getX() - (Game.WIDTH/2), 0, this.getWorld().getWidth() * 16 - Game.WIDTH));
        this.getCamera().setY(Camera.clamp(this.getY() - (Game.HEIGHT/2),0, this.getWorld().getHeight() * 16 - Game.HEIGHT));
    }
    
    @Override
    public void render(Graphics graphics) {

    	if (dir == right_dir) {
    	    if (moved) {
    	    	graphics.drawImage(rightPlayer[index], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    	    } else {
    	    	graphics.drawImage(rightPlayer[0], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    	    }
    	} else if (dir == left_dir) {
    		if (moved) {
    			graphics.drawImage(leftPlayer[index], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    		} else {
    			graphics.drawImage(leftPlayer[0], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    		}
    	} else if (dir == up_dir) {
    		if (moved) {
    			graphics.drawImage(upPlayer[index], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    		} else {
    			graphics.drawImage(upPlayer[0], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    		}
    	} else if (dir == down_dir) {
    		if (moved) {
    			graphics.drawImage(downPlayer[index], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    		} else {
    			graphics.drawImage(downPlayer[0], getX() - this.getCamera().getX(), getY() - this.getCamera().getY(), null);
    		}
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
