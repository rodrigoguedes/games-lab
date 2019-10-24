package dev.rodrigoguedes.game.zelda.world;

import dev.rodrigoguedes.game.zelda.Game;

import java.awt.image.BufferedImage;

public class FloorTile extends Tile {

    public static BufferedImage TILE_FLOOR = Game.spritesheetWorld.getSprite(16 * 8, 16 * 4, 16, 16);

    public FloorTile(int x, int y, BufferedImage sprite, Camera camera) {
        super(x, y, sprite, camera);
    }
}
