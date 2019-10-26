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

    // TODO change to double
    private int speed = 2;

    private int frames = 0;
    private int maxFrames = 7;
    private int index = 0;
    private int maxIndex = 2;
    private boolean moved = false;

    private double life = 100;
    private double maxLife = 100;

    private int ammo = 0;

    private boolean damaged = false;
    
    private BufferedImage[] rightPlayer = new BufferedImage[4];
    private BufferedImage[] leftPlayer = new BufferedImage[4];
    private BufferedImage[] upPlayer = new BufferedImage[4];
    private BufferedImage[] downPlayer = new BufferedImage[4];

    private BufferedImage playerDamage;
    private int damageFrames = 0;
    
    public Player(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        super(x, y, width, height, sprite, camera, world);

        this.playerDamage = Game.spritesheet.getSprite(16 * 0, 16 * 9, 16, 16);

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

        this.checkCollisionLifePack();
        this.checkCollisionBullet();

        if (isDamaged()) {
            this.damageFrames++;
            if (this.damageFrames == 8) {
                this.damageFrames = 0;
                this.setDamaged(false);
            }
        }

        this.getCamera().setX(Camera.clamp(this.getX() - (Game.WIDTH/2), 0, this.getWorld().getWidth() * 16 - Game.WIDTH));
        this.getCamera().setY(Camera.clamp(this.getY() - (Game.HEIGHT/2),0, this.getWorld().getHeight() * 16 - Game.HEIGHT));
    }

    public void checkCollisionBullet(){
        for(int i = 0; i < getWorld().getEntities().size(); i++){
            Entity current = getWorld().getEntities().get(i);
            if(current instanceof Bullet) {
                if (isColidding(this, current)) {
                    ammo += 10;
                    getWorld().getEntities().remove(current);
                }
            }
        }
    }

    public void checkCollisionLifePack(){
        for(int i = 0; i < getWorld().getEntities().size(); i++){
            Entity current = getWorld().getEntities().get(i);
            if(current instanceof LifePack) {
                if (isColidding(this, current)) {
                    life += 10;
                    if (life > 100) {
                        life = 100;
                    }
                    getWorld().getEntities().remove(current);
                }
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        if (!isDamaged()) {
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
        } else {
            graphics.drawImage(playerDamage, this.getX() - this.getCamera().getX(), this.getY() - this.getCamera().getY(), null);
        }


    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public int getAmmo() {
        return ammo;
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public double getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(double maxLife) {
        this.maxLife = maxLife;
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
