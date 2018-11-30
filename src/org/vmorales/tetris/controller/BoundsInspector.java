package org.vmorales.tetris.controller;

import java.awt.Point;
import java.util.List;

import org.vmorales.tetris.component.GameGrid;
import org.vmorales.tetris.component.Piece;
import org.vmorales.tetris.piecefactory.ConcretePieceFactory;
import org.vmorales.tetris.util.TetrisUtils;
import org.vmorales.tetris.validator.Restriction;
import org.vmorales.tetris.validator.impl.PieceCollisionCheck;
import org.vmorales.tetris.validator.impl.WallCollisionCheck;

public class BoundsInspector implements ActionPiece {

	private GameGrid gameGrid;
	private Restriction wallCollisionCheck;
	private Restriction pieceCollisionCheck;
	private PhantomGenerator phantomGenerator;
	
	public void setPhantomGenerator(PhantomGenerator phantomGenerator) {
		this.phantomGenerator = phantomGenerator;
	}

	public BoundsInspector(GameGrid gameGrid) {
		this.gameGrid = gameGrid;
	}

	@Override
	public synchronized void performHorizontalMovement(Piece currentPiece, Direction direction) {
		((WallCollisionCheck) wallCollisionCheck).setDirection(direction);
		((PieceCollisionCheck) pieceCollisionCheck).setDirection(direction);
		if (wallCollisionCheck.movementAllowed(currentPiece) && pieceCollisionCheck.movementAllowed(currentPiece)) {
			currentPiece.getPoint().x = currentPiece.getPoint().x + direction.getDeltaX();
			generatePhantomPiece(currentPiece);
		}
	}
	@Override
	public synchronized void performRotation(Piece currentPiece) {
		Piece rotatedPiece = ConcretePieceFactory.INSTANCE.createRotatedPiece(currentPiece);
		((WallCollisionCheck) wallCollisionCheck).setDirection(Direction.NEUTRO);
		((PieceCollisionCheck) pieceCollisionCheck).setDirection(Direction.NEUTRO);
		if (wallCollisionCheck.movementAllowed(rotatedPiece) && pieceCollisionCheck.movementAllowed(rotatedPiece)) {
			phantomGenerator.switchToRotatedPiece(rotatedPiece);
			generatePhantomPiece(rotatedPiece);
		}
	}
	
	public void generatePhantomPiece(Piece piece){
		int deltaY = piece.calculateLandingPointY(gameGrid.getGrid());
		phantomGenerator.createPhantomPiece(deltaY);
	}
	
	public void fillGameGrid(Piece piece){
		List<Point> realPoints = TetrisUtils.calculateRealPoints(piece);
		gameGrid.fillGrid(realPoints, piece.getType());
	}
	
	public int[][] getGameGrid() {
		return gameGrid.getGrid();
	}

	public void setWallCollisionCheck(Restriction wallCollisionCheck) {
		this.wallCollisionCheck = wallCollisionCheck;
	}
	
	public void setPieceCollisionCheck(Restriction pieceCollisionCheck) {
		this.pieceCollisionCheck = pieceCollisionCheck;
		((PieceCollisionCheck)pieceCollisionCheck).setGameGrid(gameGrid.getGrid());
	}

}
