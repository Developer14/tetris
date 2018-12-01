package org.vmorales.tetris.controller;

import org.vmorales.tetris.component.Piece;

public interface ActionPiece {

	public enum Direction {
		NEUTRO(0),
		MOVE_LEFT(-1), 
		MOVE_RIGHT(1);
		
		private int deltaX;
		Direction(int deltaX){
			this.deltaX = deltaX;
		}
		public int getDeltaX() {
			return deltaX;
		}
	}
	
	void performHorizontalMovement(Piece currentPiece, Direction direction);
	void performRotation(Piece currentPiece);
}
