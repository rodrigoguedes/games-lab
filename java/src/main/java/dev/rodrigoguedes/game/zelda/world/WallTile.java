package dev.rodrigoguedes.game.zelda.world;

import dev.rodrigoguedes.game.zelda.Game;

import java.awt.image.BufferedImage;

public class WallTile extends Tile {

    public static BufferedImage TILE_WALL = Game.spritesheetWorld.getSprite(16 * 14, 16 * 16, 16, 16);

    public WallTile(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }
}
