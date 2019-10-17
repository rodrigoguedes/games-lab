package dev.rodrigoguedes.game.zelda;

import dev.rodrigoguedes.game.zelda.entities.Entity;
import dev.rodrigoguedes.game.zelda.entities.Player;
import dev.rodrigoguedes.game.zelda.graphics.Spritesheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;

	private static boolean isRunning = true;
	private static Thread thread;

	private List<Entity> entities;
	public static final Spritesheet spritesheet = new Spritesheet("/zelda/character.png");

	private Player player;

	private BufferedImage layer;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addKeyListener(this);

		this.layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.entities = new ArrayList<Entity>();

		this.player = new Player(0,0, 16, 32, spritesheet.getSprite(0, 0, 16, 32));
		entities.add(player);
	}

	private void tick() {
		for (Entity entity: entities) {
			entity.tick();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics graphics = layer.getGraphics();
		graphics.setColor(new Color(19, 19, 19));
		graphics.fillRect(0, 0 , WIDTH, HEIGHT);

		for (Entity entity: entities) {
			entity.render(graphics);
		}

		graphics.dispose();
		graphics = bs.getDrawGraphics();
		graphics.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}

	private synchronized void start() {
		this.isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		this.isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		int frames = 0;
		double timer = System.currentTimeMillis();

		while(this.isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				this.tick();
				this.render();

				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println("FPS:" + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.moveToRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.moveToLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.moveToUp();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.moveToDown();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.stopMoveToRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.stopMoveToLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.stopMoveToUp();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.stopMoveToDown();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();

		JFrame frame = new JFrame("Zelda");
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		game.start();
	}
}
