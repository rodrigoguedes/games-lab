package dev.rodrigoguedes.game.zelda;

import dev.rodrigoguedes.game.zelda.entities.Entity;
import dev.rodrigoguedes.game.zelda.entities.Player;
import dev.rodrigoguedes.game.zelda.graphics.Menu;
import dev.rodrigoguedes.game.zelda.graphics.Spritesheet;
import dev.rodrigoguedes.game.zelda.graphics.UI;
import dev.rodrigoguedes.game.zelda.world.Camera;
import dev.rodrigoguedes.game.zelda.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.GlyphVector;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;

	private static boolean isRunning = true;
	private static Thread thread;

	public static final Spritesheet spritesheet = new Spritesheet("/zelda/16x16Sprites.png");
    public static final Spritesheet spritesheetWorld = new Spritesheet("/zelda/16x16Sprites.png");

	private BufferedImage layer;

	private int currentLevel = 1;
	private int maxLevel = 2;

	private Player player;
	private World world;
	private Camera camera;
	private List<Entity> entities;

	private GameState gameState = GameState.MENU;
	private boolean showMessageGamerOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;

	private UI ui;

	private Menu menu;

	public static final Random rand = new Random();

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		this.camera = new Camera();

		this.menu = new Menu(this);

//		this.entities = new ArrayList<Entity>();
//		this.world = new World("/zelda/level1.png", this.entities, camera);
//		this.player = new Player(16,16, 16, 32, spritesheet.getSprite(0, 0, 16, 32), camera, world);
//		this.entities.add(player);
//		this.ui = new UI(world);
		startGame();
	}

	public void startGame() {
		this.restartGame = false;
		this.gameState = GameState.MENU;

		this.entities = new ArrayList<Entity>();
		String levelMap = String.format("/zelda/level%d.png", currentLevel);

		this.world = new World(levelMap, this.entities, camera, this);
		this.player = new Player(16,16, 16, 32, spritesheet.getSprite(0, 0, 16, 32), camera, world);
		this.entities.add(player);
		this.ui = new UI(world);
	}


	private void tick() {
		if (gameState == GameState.NORMAL) {
			this.restartGame = false; //Workaround!!!! Refactory this!!!!

			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).tick();
			}

			// throws java.util.ConcurrentModificationException
			// to avoid this may I can use ImmutableList (Guava or Native JDK)
			// Move entities from Game to World
			//		for (Entity entity: entities) {
			//			entity.tick();
			//		}

			for (int i = 0; i < world.getBullets().size(); i++) {
				world.getBullets().get(i).tick();
			}

			if (world.getEnemies().size() == 0) {
				currentLevel++;
				if (currentLevel > maxLevel) {
					currentLevel = 1;
				}

				startGame();
			}
		} else if (gameState == GameState.GAME_OVER) {
			this.framesGameOver++;
			if (this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if (this.showMessageGamerOver) {
					this.showMessageGamerOver = false;
				} else {
					this.showMessageGamerOver = true;
				}
			}

			if (restartGame) {
				currentLevel = 1;
				startGame();
			}
		} else if (gameState == GameState.MENU) {
			this.menu.tick();
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

        this.world.render(graphics);

		for (Entity entity: entities) {
			entity.render(graphics);
		}

		//Todo layers
		for (Entity entity: world.getBullets()) {
			entity.render(graphics);
		}

		ui.render(graphics);

		graphics.dispose();
		graphics = bs.getDrawGraphics();
		graphics.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		graphics.setFont(new Font("Arial", Font.BOLD, 20));
		graphics.setColor(Color.WHITE);
		graphics.drawString("Ammo: " + this.player.getAmmo(), 600, 20);

		if (gameState == GameState.GAME_OVER) {
			Graphics2D graphics2D = (Graphics2D) graphics;
			graphics2D.setColor(new Color(0, 0, 0, 100));
			graphics2D.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

			graphics.setFont(new Font("Arial", Font.BOLD, 36));
			graphics.setColor(Color.WHITE);
			graphics.drawString("Game Over", WIDTH*SCALE / 2 - 50, HEIGHT*SCALE / 2 - 20);
			graphics.setFont(new Font("Arial", Font.BOLD, 32));
			if (showMessageGamerOver) {
				graphics.drawString(">Press Enter to continue<", WIDTH * SCALE / 2 - 150, HEIGHT * SCALE / 2 + 20);
			}
		} else if (gameState == GameState.MENU) {
			this.menu.render(graphics);
		}

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

		requestFocus();
		
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

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.moveToUp();

			if (gameState == GameState.MENU) {
				this.menu.moveToUp();
			}

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.moveToDown();

			if (gameState == GameState.MENU) {
				this.menu.moveToDown();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_X) {
			player.startShoot();
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.startMouseShoot((e.getX() / SCALE), (e.getY() / SCALE));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public static void main(String[] args) {
		//Workaround to remove slowness when calling drawString
		//It is happen only on MACOS
		GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		Game game = new Game();

		JFrame frame = new JFrame("Zelda");
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


		game.start();
	}

}
