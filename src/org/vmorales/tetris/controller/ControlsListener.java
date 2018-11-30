package org.vmorales.tetris.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.vmorales.tetris.component.Piece;
import org.vmorales.tetris.controller.ActionPiece.Direction;

public class ControlsListener implements KeyListener {

	private ActionPiece actionPiece;
	private ActionGame actionGame;
	private Piece currentPiece;
	
	public void keyPressed(KeyEvent a) {
		if (actionGame.areControlsEnabled()) {
			if (a.getKeyCode() == KeyEvent.VK_ENTER) {
//				if (!isPlaying) {
//					iniciar();
//					isPlaying = true;
//				}
			}
			if (a.getKeyCode() == KeyEvent.VK_UP) {
				actionPiece.performRotation(currentPiece);
			} else if (a.getKeyCode() == KeyEvent.VK_LEFT) {
				actionPiece.performHorizontalMovement(currentPiece, Direction.MOVE_LEFT);
			} else if (a.getKeyCode() == KeyEvent.VK_RIGHT) {
				actionPiece.performHorizontalMovement(currentPiece, Direction.MOVE_RIGHT);
			} else if (a.getKeyCode() == KeyEvent.VK_DOWN) {
				actionGame.performFallAcceleration(true);
			}
		}
		if (a.getKeyCode() == KeyEvent.VK_ESCAPE) {
			actionGame.togglePauseResumeGame();
		}
	}

	public void keyReleased(KeyEvent a) {
		if (actionGame.areControlsEnabled()) {
			if (a.getKeyCode() == KeyEvent.VK_DOWN) {
				actionGame.performFallAcceleration(false);
			} 
		}
	}

	public void keyTyped(KeyEvent a) {}

	public void setActionPiece(ActionPiece actionPiece) {
		this.actionPiece = actionPiece;
	}

	public void setCurrentPiece(Piece currentPiece) {
		this.currentPiece = currentPiece;
	}

	public void setActionGame(ActionGame actionGame) {
		this.actionGame = actionGame;
	}

}
