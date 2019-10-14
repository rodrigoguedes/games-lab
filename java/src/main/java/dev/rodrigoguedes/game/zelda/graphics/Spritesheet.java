package dev.rodrigoguedes.game.zelda.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spritesheet {

    private BufferedImage bufferedImage;

    public Spritesheet(String path) {
        try {
            this.bufferedImage = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return this.bufferedImage.getSubimage(x, y, width, height);
    }

}
