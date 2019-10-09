package dev.rodrigoguedes.game;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = true;
	private Thread thread;
	
	public static void main(String[] args) {
		System.out.println("teste.......");
		Game game = new Game();
		game.start();
	}
	
	private synchronized void start() {
		System.out.println("start");
		thread = new Thread(this);
		thread.start();
	}
	
	public void tick() {
		System.out.println("Tick");
	}
	
	public void render() {
		System.out.println("Render");
	}
	
	public void run() {
		System.out.println("run....");
		while(isRunning) {
			tick();
			render();
		}
	}

}
