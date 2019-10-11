package dev.rodrigoguedes.game.study;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private final int WIDTH = 240;
	private final int HEIGHT = 160;
	private final int SCALE = 3;

	private BufferedImage image;

	private Spritesheet sheet;
	private BufferedImage[] player;
	private int frames = 0;
	private int maxFrames = 20; //speed
	private int curAnimation = 0;
	private int maxAnimations = 2;//

	private boolean isRunning = true;
	private Thread thread;

	public Game() {
		this.sheet = new Spritesheet("/spritesheet.png");
		this.player = new BufferedImage[2];
		this.player[0] = sheet.getSprite(0, 0, 16, 16);
		this.player[1] = sheet.getSprite(0, 16, 16, 16);

		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();

		this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}

	private void initFrame() {
		this.frame = new JFrame("Rodrigo's Game");
		this.frame.add(this);
		this.frame.setResizable(false);
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
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
	
	private void tick() {
		this.frames++;
		if (this.frames > this.maxFrames) {
			this.frames = 0;
			this.curAnimation++;
			if (this.curAnimation >= this.maxAnimations) {
				this.curAnimation = 0;
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		//Object 1
		Graphics g = image.getGraphics();
		g.setColor(new Color(19, 19, 19));
		g.fillRect(0, 0 , WIDTH, HEIGHT);
//
//		//Object 2
//		g.setColor(Color.CYAN);
//		g.fillRect(5, 5, 80, 80);
//
//		//Object 3
//		g.setColor(Color.GREEN);
//		g.fillOval(50, 50 , 50, 50);

//		//Object Font
//		g.setFont(new Font("Arial", Font.BOLD, 20));
//		g.setColor(Color.BLUE);
//		g.drawString("Hello World!", 5, 110);

//		//Object Players
//		g.setColor(new Color(0, 0, 0));
//		g.drawImage(player, 110, 30, null);
//		g.drawImage(player, 10, 30, null);
//		g.drawImage(player, 20, 20, null);
//		g.drawImage(player, 40, 40, null);

//		//Rotate
//		Graphics2D g2 = (Graphics2D) g;
//		g2.rotate(Math.toRadians(45), 90, 90);
//		g.drawImage(player, 40, 40, null);

		//Animation
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(player[curAnimation], 40, 40, null);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
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
				tick();
				render();

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

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}
