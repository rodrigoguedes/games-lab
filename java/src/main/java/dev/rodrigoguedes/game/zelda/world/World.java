package dev.rodrigoguedes.game.zelda.world;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.entities.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class World {

    private Tile[] tiles;

    private int width;
    private int height;

    private List<Entity> entities;
    private Camera camera;

    public World(String path, List<Entity> entities, Camera camera) {
        try {
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
                        this.entities.add(new Enemy(x * 16, y * 16, 16, 16, Enemy.ENEMY_EN, camera, this));
                    } else if (currentPixel == 0xFFFF6A00) {
                        //Weapon
                        this.entities.add(new Weapon(x * 16, y * 16, 16, 16, Weapon.WEAPON_EN, camera, this));
                    } else if (currentPixel == 0xFFFF7F7F) {
                        //Life Pack
                        this.entities.add(new LifePack(x * 16, y * 16, 16, 16, LifePack.LIFEPACK_EN, camera, this));
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
