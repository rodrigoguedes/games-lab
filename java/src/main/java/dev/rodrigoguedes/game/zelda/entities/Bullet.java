package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.image.BufferedImage;

public class Bullet extends Entity {

    public static final BufferedImage BULLET_EN = Game.spritesheetWorld.getSprite(16 * 8, 16 * 7, 16, 16);

    public Bullet(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        super(x, y, width, height, sprite, camera, world);
    }
}
