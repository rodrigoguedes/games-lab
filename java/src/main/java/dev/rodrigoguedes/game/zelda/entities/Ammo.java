package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.image.BufferedImage;

public class Ammo extends Entity {

    public static final BufferedImage AMMO_EN = Game.spritesheetWorld.getSprite(16 * 8, 16 * 7, 16, 16);

    public Ammo(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        super(x, y, width, height, sprite, camera, world);
    }
}
