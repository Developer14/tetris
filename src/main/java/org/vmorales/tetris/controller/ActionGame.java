package org.vmorales.tetris.controller;

public interface ActionGame {

	void performFallAcceleration(boolean accelerate);
	void togglePauseResumeGame();
	boolean areControlsEnabled();
}
