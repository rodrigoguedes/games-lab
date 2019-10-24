package dev.rodrigoguedes.game.zelda.entities;

import dev.rodrigoguedes.game.zelda.Game;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.image.BufferedImage;

public class Weapon extends Enemy {

    public static final BufferedImage WEAPON_EN = Game.spritesheetWorld.getSprite(16 * 7, 16 * 7, 16, 16);

    public Weapon(int x, int y, int width, int height, BufferedImage sprite, Camera camera, World world) {
        super(x, y, width, height, sprite, camera, world);
    }
}
