package dev.rodrigoguedes.game.zelda.world;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.entities.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {

    private Tile[] tiles;

    private int width;
    private int height;

    private List<Entity> entities;
    private List<Enemy> enemies;

    private Camera camera;

    public World(String path, List<Entity> entities, Camera camera) {
        try {
            this.enemies = new ArrayList<>();
            this.entities = entities;
            this.camera = camera;

            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];

            this.width = map.getWidth();
            this.height = map.getHeight();
            
            this.tiles = new Tile[map.getWidth() * map.getHeight()];

            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

            for (int x = 0; x < map.getWidth(); x++) {
                for (int y = 0; y < map.getHeight(); y++) {
                    int currentPixel = pixels[x + (y * map.getWidth())];
                    tiles[x + (y * width)] = new FloorTile(x * 16, y * 16, FloorTile.TILE_FLOOR, camera);
                    if (currentPixel == 0xFF000000) {
                        //Floor
                        tiles[x + (y * width)] = new FloorTile(x * 16, y * 16, FloorTile.TILE_FLOOR, camera);
                    } else if (currentPixel == 0xFFFFFFFF) {
                        //Wall
                        tiles[x + (y * width)] = new WallTile(x * 16, y * 16, WallTile.TILE_WALL, camera);
                    } else if (currentPixel == 0xFFff0000) {
                        //Enemy
                        Enemy enemy = new Enemy(x * 16, y * 16, 16, 16, Enemy.ENEMY_EN, camera, this);
                        this.entities.add(enemy);
                        this.enemies.add(enemy);
                    } else if (currentPixel == 0xFFFF6A00) {
                        //Weapon
                        this.entities.add(new Weapon(x * 16, y * 16, 16, 16, Weapon.WEAPON_EN, camera, this));
                    } else if (currentPixel == 0xFFFF7F7F) {
                        //Life Pack
                        LifePack lifePack = new LifePack(x * 16, y * 16, 16, 16, LifePack.LIFEPACK_EN, camera, this);
                        lifePack.setMaskX(4);
                        lifePack.setMaskY(4);
                        lifePack.setMaskW(4);
                        lifePack.setMaskH(4);
                        this.entities.add(lifePack);
                    } else if (currentPixel == 0xFFFFD800) {
                        //Bullet
                        this.entities.add(new Bullet(x * 16, y * 16, 16, 16, Bullet.BULLET_EN, camera, this));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Player getPlayer() {
    	Entity entity = this.entities.stream().filter(e -> e instanceof Player).findFirst().get();
    	return (Player) entity;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    public boolean isFree(int xNext, int yNext) {
        // TODO replace all code from 16 to const TILE_SIZE=16
        int x1 = xNext / 16;
        int y1 = yNext / 16;

        int x2 = (xNext + 16 - 1) / 16;
        int y2 = yNext / 16;

        int x3 = xNext / 16;
        int y3 = (yNext + 16 - 1) / 16;

        int x4 = (xNext + 16 - 1) / 16;
        int y4 = (yNext + 16 - 1) / 16;

        return !((tiles[x1 + (y1 * this.width)] instanceof WallTile) ||
                (tiles[x2 + (y2 * this.width)] instanceof WallTile) ||
                (tiles[x3 + (y3 * this.width)] instanceof WallTile) ||
                (tiles[x4 + (y4 * this.width)] instanceof WallTile));
    }

    public void render (Graphics graphics) {
        int xStart = this.camera.getX() >> 4;
        int yStart = this.camera.getY() >> 4;

        int xFinal = xStart + (Game.WIDTH >> 4) ;
        int yFinal = yStart + (Game.HEIGHT >> 4);

        for (int x = xStart; x <= xFinal; x++) {
            for (int y = yStart; y <= yFinal; y++) {
                if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
                    continue;
                }
                Tile tile = this.tiles[x + (y * this.width)];
                tile.render(graphics);
            }
        }
    }
    
    

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
