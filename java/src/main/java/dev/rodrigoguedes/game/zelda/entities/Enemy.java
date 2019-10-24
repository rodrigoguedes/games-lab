package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    public static final BufferedImage ENEMY_EN = Game.spritesheetWorld.getSprite(16 * 2, 16 * 0, 16, 16);
    
    private int speed = 1;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        super(x, y, width, height, sprite, camera, world);
    }
    
    @Override
    public void tick() {
    	if (getX() < this.getWorld().getPlayer().getX() && this.getWorld().isFree(getX() + this.speed,  getY())) {
    		this.setX(this.getX() + this.speed);
    	} else if (getX() > this.getWorld().getPlayer().getX() && this.getWorld().isFree(getX() - this.speed,  getY())) {
    		this.setX(this.getX() - this.speed);
    	}
    	
    	if (getY() < this.getWorld().getPlayer().getY() && this.getWorld().isFree(getX(),  getY() + this.speed)) {
    		this.setY(this.getY() + this.speed);
    	} else if (getY() > this.getWorld().getPlayer().getY() && this.getWorld().isFree(getX(),  getY() - this.speed)) {
    		this.setY(this.getY() - this.speed);
    	}
    }
}
