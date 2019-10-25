package dev.rodrigoguedes.game.zelda.graphics;

import dev.rodrigoguedes.game.zelda.world.World;

import java.awt.*;

public class UI {

    private World world;

    public UI(World world) {
        this.world = world;
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect(8, 4, 50, 8);
        graphics.setColor(Color.GREEN);
        graphics.fillRect(8, 4, (int)(world.getPlayer().getLife() / world.getPlayer().getMaxLife() * 50), 8);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial", Font.BOLD, 8));
        graphics.drawString((int)world.getPlayer().getLife() + "/" + (int)world.getPlayer().getMaxLife(), 18, 11);
    }

}


