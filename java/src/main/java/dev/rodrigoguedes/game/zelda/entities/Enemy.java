package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    public static final BufferedImage ENEMY_EN = Game.spritesheetWorld.getSprite(16 * 2, 16 * 0, 16, 16);
	public static final BufferedImage ENEMY_DAMAGE = Game.spritesheet.getSprite(16 * 10, 16 * 0, 16, 16);

	// TODO change to double
    private int speed = 1;

	private int frames = 0;
	private int maxFrames = 20;
	private int index = 0;
	private int maxIndex = 1;

	private int life = 10;

	private boolean damaged = false;
	private int damageFrames = 0;

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
		if (!isCollidingWithPlayer()) {
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
		} else {
			Player player = this.getWorld().getPlayer();
			if (Game.rand.nextInt(100) < 10) {
				player.setLife(player.getLife() - 1);
				player.setDamaged(true);
			}
		}


		this.frames++;
		if (this.frames == this.maxFrames) {
			this.frames = 0;
			this.index++;
			if (this.index > this.maxIndex) {
				this.index = 0;
			}
		}

		isCollidingBullet();

		if (life <= 0) {
			destroySelf();
		}

		if (isDamaged()) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				this.damageFrames = 0;
				this.setDamaged(false);
			}
		}
    }

	public void destroySelf() {
		this.getWorld().getEnemies().remove(this);
    	this.getWorld().getEntities().remove(this);
	}

	public void isCollidingBullet() {
		for (int i = 0; i < this.getWorld().getBullets().size(); i++) {
			Entity e = this.getWorld().getBullets().get(i);
			if (this.isColliding(this, e)) {
				life--;
				this.setDamaged(true);
				this.getWorld().getBullets().remove(i);
				return;
			}
		}
	}

	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + this.getMaskX(), this.getY() + this.getMaskY(), this.getMaskW(), this.getMaskH());
		Rectangle player = new Rectangle(getWorld().getPlayer().getX(), getWorld().getPlayer().getY(), 16, 16);
		return enemyCurrent.intersects(player);
	}

    public boolean isColliding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext + this.getMaskX(), yNext + this.getMaskY(), this.getMaskW(), this.getMaskH());
		for (int i = 0; i < getWorld().getEnemies().size(); i++) {
			Enemy e = getWorld().getEnemies().get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + this.getMaskX(), e.getY() + this.getMaskY(), this.getMaskW(), this.getMaskH());
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void render(Graphics graphics) {
    	if (!isDamaged()) {
			graphics.drawImage(sprites[index], this.getX() - getCamera().getX(), this.getY() - getCamera().getY(), null);
		} else {
			graphics.drawImage(ENEMY_DAMAGE, this.getX() - getCamera().getX(), this.getY() - getCamera().getY(), null);
		}

		// To debug
//		graphics.setColor(Color.BLUE);
//		graphics.fillRect((this.getX() + this.getMaskX()) - getCamera().getX(), (this.getY() + this.getMaskY()) - getCamera().getY(), this.getMaskW(), this.getMaskH());
	}

	public boolean isDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
}
