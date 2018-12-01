package org.vmorales.tetris.validator.impl;

import static org.vmorales.tetris.util.TetrisUtils.calculateRealPoints;

import java.awt.Point;
import java.util.List;

import org.vmorales.tetris.component.Piece;
import org.vmorales.tetris.controller.ActionPiece.Direction;
import org.vmorales.tetris.piecefactory.ConcretePieceFactory;
import org.vmorales.tetris.validator.Restriction;

public class PieceCollisionCheck implements Restriction {

	private Direction direction;
	private int[][] gameGrid;
	
	public PieceCollisionCheck(){
	}
	
	@Override
	public boolean movementAllowed(Piece piece) {
		Piece fakePiece = ConcretePieceFactory.INSTANCE.clonePieceWithOffsetY(piece, 0);
		fakePiece.getPoint().x = fakePiece.getPoint().x + direction.getDeltaX();
		List<Point> realPoints = calculateRealPoints(fakePiece);
		boolean allowed = true;
		try {
			for (Point point : realPoints) {
				int v = gameGrid[point.y][point.x];
				if(v != 0) allowed = false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			allowed = false;
		}
		return allowed;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setGameGrid(int[][] gameGrid) {
		this.gameGrid = gameGrid;
	}

}
