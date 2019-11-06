package dev.rodrigoguedes.game.zelda.graphics;

import dev.rodrigoguedes.game.zelda.Game;

import java.awt.*;

public class Menu {

    private Game game;

    private String[] options = {"New", "Load", "Quit"};

    public int currentOption = 0;
    public int maxOption = options.length - 1;

    private boolean up;
    private boolean down;

    public Menu(Game game) {
        this.game = game;
    }

    public void tick() {
        if (up) {
            up = false;
            currentOption--;
            if (currentOption < 0) {
                currentOption = maxOption;
            }
        }
        if (down) {
            down = false;
            currentOption++;
            if (currentOption > maxOption) {
                currentOption = 0;
            }
        }
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0 , this.game.getWidth() * Game.SCALE, this.game.getHeight() * Game.SCALE);

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.BOLD, 36));
        graphics.drawString(">Rodrigo Guedes de Souza<", (this.game.getWidth() * Game.SCALE)/ 2 - 950, (this.game.getHeight() * Game.SCALE)/ 2 - 680);

        graphics.setColor(Color.WHITE);

        graphics.setFont(new Font("Arial", Font.BOLD, 24));
        renderMenuItem(graphics,0,  (this.game.getWidth() * Game.SCALE)/ 2 - 950, (this.game.getHeight() * Game.SCALE)/ 2 - 580);
        renderMenuItem(graphics,1,  (this.game.getWidth() * Game.SCALE)/ 2 - 950, (this.game.getHeight() * Game.SCALE)/ 2 - 540);
        renderMenuItem(graphics,2,  (this.game.getWidth() * Game.SCALE)/ 2 - 950, (this.game.getHeight() * Game.SCALE)/ 2 - 500);
    }

    private void renderMenuItem(Graphics graphics, int pos, int x, int y) {
        graphics.drawString(options[pos], x , y);
        if (currentOption == pos) {
            graphics.drawString(">", x - 20, y);
        }
    }

    public void moveToUp() {
        this.up = true;
    }

    public void moveToDown() {
        this.down = true;
    }

}
