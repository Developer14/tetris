package org.vmorales.tetris.controller;


public class Engine implements Runnable {

	private static final int DEFAULT_DELAY = 25;
	
	private GameController gameController;
	
	public Engine(GameController gameController){
		this.gameController = gameController;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				gameController.controlGame();
				Thread.sleep(DEFAULT_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void startGraphics(){
		new Thread(this).start();
	}
}
