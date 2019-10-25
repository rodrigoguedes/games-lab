package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    public static final BufferedImage ENEMY_EN = Game.spritesheetWorld.getSprite(16 * 2, 16 * 0, 16, 16);
    
    private int speed = 1;

    private int maskX = 0;
	private int maskY = 0;
	private int maskW = 0;
	private int maskH = 0;

	private int frames = 0;
	private int maxFrames = 20;
	private int index = 0;
	private int maxIndex = 1;

	private BufferedImage[] sprites;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        super(x, y, width, height, null, camera, world);
        sprites = new BufferedImage[3];
        sprites[0] = Game.spritesheet.getSprite(0, 0, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(16 , 0, 16, 16);
    }
    
    @Override
    public void tick() {
//    	if (Game.rand.nextInt(100) < 30) {
			if (getX() < this.getWorld().getPlayer().getX() && this.getWorld().isFree(getX() + this.speed, getY())
				&& !isColliding(getX() + this.speed, getY())) {

				this.setX(this.getX() + this.speed);

			} else if (getX() > this.getWorld().getPlayer().getX() && this.getWorld().isFree(getX() - this.speed, getY())
				&& !isColliding(getX() - this.speed, getY())) {

				this.setX(this.getX() - this.speed);
			}

			if (getY() < this.getWorld().getPlayer().getY() && this.getWorld().isFree(getX(), getY() + this.speed)
				&& !isColliding(getX(), getY() + this.speed)) {

				this.setY(this.getY() + this.speed);

			} else if (getY() > this.getWorld().getPlayer().getY() && this.getWorld().isFree(getX(), getY() - this.speed)
				&& !isColliding(getX(), getY() - this.speed)) {

				this.setY(this.getY() - this.speed);
			}
//		}

		this.frames++;
		if (this.frames == this.maxFrames) {
			this.frames = 0;
			this.index++;
			if (this.index > this.maxIndex) {
				this.index = 0;
			}
		}
    }

    public boolean isColliding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext + maskX, yNext + maskY, 16 + maskW, 16 + maskH);
		for (int i = 0; i < getWorld().getEnemies().size(); i++) {
			Enemy e = getWorld().getEnemies().get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, 16 + maskW, 16 + maskH);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void render(Graphics graphics) {
    	graphics.drawImage(sprites[index], this.getX()- getCamera().getX(), this.getY()- getCamera().getY(), null);

		// To debug
//		graphics.setColor(Color.BLUE);
//		graphics.fillRect((this.getX() + maskX) - getCamera().getX(), (this.getY() + maskY) - getCamera().getY(), 16 + maskW, 16 + maskH);
	}
}
