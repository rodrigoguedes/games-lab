package dev.rodrigoguedes.game.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;

	private static boolean isRunning = true;
	private static Thread thread;

	private Player player;
	private Enemy enemy;
	private Ball ball;

	private BufferedImage layer;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addKeyListener(this);

		startPlay();

		this.layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}

	public void startPlay() {
		this.player = new Player(100, HEIGHT - 10);
		this.enemy = new Enemy(100, 0);
		this.ball = new Ball(100, HEIGHT / 2 - 1);
	}

	private void tick() {
		player.tick();
		enemy.tick(ball);
		ball.tick(this, player, enemy);
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

		player.render(graphics);
		enemy.render(graphics);
		ball.render(graphics);

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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.stopMoveToRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.stopMoveToLeft();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();

		JFrame frame = new JFrame("Pong");
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		game.start();
	}
}
