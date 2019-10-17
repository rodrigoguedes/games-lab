package dev.rodrigoguedes.game.zelda.world;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    private Tile[] tiles;

    private int width;
    private int height;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];

            this.width = map.getWidth();
            this.height = map.getHeight();
            
            this.tiles = new Tile[map.getWidth() * map.getHeight()];

            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

            for (int x = 0; x < map.getWidth(); x++) {
                for (int y = 0; y < map.getHeight(); y++) {
                    int currentPixel = pixels[x + (y * map.getWidth())];
                    if (currentPixel == 0xFFFF0000) {
                        tiles[x + (y * width)] = new FloorTile(x * 16, y * 16, FloorTile.TILE_FLOOR);
                    } else if (currentPixel == 0xFFFFFFFF) {
                        tiles[x + (y * width)] = new WallTile(x * 16, y * 16, WallTile.TILE_WALL);
                    } else if (currentPixel == 0xFF0026FF) {
                        tiles[x + (y * width)] = new FloorTile(x * 16, y * 16, FloorTile.TILE_FLOOR);
                    } else {
                        tiles[x + (y * width)] = new FloorTile(x * 16, y * 16, FloorTile.TILE_FLOOR);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render (Graphics graphics) {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Tile tile = this.tiles[x + (y * this.width)];
                tile.render(graphics);
            }
        }
    }
}
