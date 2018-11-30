package org.vmorales.tetris.component;

public class GameData {

	public static final int DEFAULT_SPEED = 15;
	public static final int FAST_SPEED = 1;
	private long cycleCounter;
	private int score;
	private int level;
	private int totalFullLines;
	private int speed = DEFAULT_SPEED;
	private int currentSpeed;
	
	public GameData(){
		currentSpeed = speed;
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTotalFullLines() {
		return totalFullLines;
	}

	public void setTotalFullLines(int totalFullLines) {
		this.totalFullLines = totalFullLines;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void toggleSpeedUp(boolean speedUp) {
		speed = speedUp ? FAST_SPEED : currentSpeed;
	}

	public long getCycleCounter() {
		return cycleCounter;
	}

	public void setCycleCounter(long cycleCounter) {
		this.cycleCounter = cycleCounter;
	}

	public void increaseCycleCounter() {
		this.cycleCounter++;
	}
}
