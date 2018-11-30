package org.vmorales.tetris.piecefactory;

import java.awt.Point;
import java.util.Arrays;

import org.vmorales.tetris.component.Piece;
import org.vmorales.tetris.component.Stage;
import org.vmorales.tetris.validator.impl.DefaultLandingCalculator;

public class ConcretePieceFactory implements PieceFactory {

	public static final ConcretePieceFactory INSTANCE = new ConcretePieceFactory();
	
	private int[] blocks = {0,-2,  0,-1,  0,0,  0,1,	//linea
							-1,0,  0,0,  1,0,  2,0,
							0,-2,  0,-1,  0,0,  0,1,
							-1,0,  0,0,  1,0,  2,0,
							
							-1,0,  0,-1, 0,0,  0,1,		//figura T
							-1,0,  0,0,  1,0,  0,1,
							1,0,  0,-1, 0,0,  0,1,
							-1,0,  0,0,  1,0,  0,-1,
							
							-1,0,  0,0,  1,0,  1,1,		//figura L
							1,-1,  0,-1, 0,0,  0,1,
							-1,0,  0,0,  1,0,  -1,-1,
							-1,1,  0,-1, 0,0,  0,1,
							
							-1,0,  0,0,  1,0,  1,-1,	//figura J
							-1,-1,  0,-1, 0,0,  0,1,
							-1,0,  0,0,  1,0,  -1,1,
							1,1,  0,-1, 0,0,  0,1,
							
							-1,0, -1,1, 0,-1, 0,0,		//figura S
							-1,-1, 0,-1, 0,0, 1,0,
							-1,0, -1,1, 0,-1, 0,0,
							-1,-1, 0,-1, 0,0, 1,0,
							
							-1,-1, -1,0, 0,0, 0,1,		//figura Z
							0,-1, 1,-1, 0,0, -1,0,
							-1,-1, -1,0, 0,0, 0,1,
							0,-1, 1,-1, 0,0, -1,0,
							
							0,-1, 0,0, 1,-1, 1,0,		//figura O
							0,-1, 0,0, 1,-1, 1,0,
							0,-1, 0,0, 1,-1, 1,0,
							0,-1, 0,0, 1,-1, 1,0};
	
	private ConcretePieceFactory(){}
	
	@Override
	public Piece createPiece() {
		Piece piece = new Piece();
		Point point = new Point();
		point.x = Stage.WIDTH / Stage.BLOCK_SIZE / 2;
		point.y = 1;
		piece.setPoint(point);
		piece.setCurrentRotationPos((int) (Math.random() * 4));
		piece.setType((int) (Math.random() * 7));
//		piece.setType(1);
		int[] mapGrid = getMapGridRotatedPiece(piece, piece.getCurrentRotationPos());
		piece.setMapGrid(mapGrid);
		piece.setLandingCalculator(new DefaultLandingCalculator());
		return piece;
	}

	@Override
	public Piece clonePieceWithOffsetY(Piece piece, int newYpos) {
		Piece newPiece = clonePiece(piece);
		newPiece.getPoint().y = newPiece.getPoint().y + newYpos;
		return newPiece;
	}
	
	private Piece clonePiece(Piece piece){
		Piece newPiece = new Piece();
		newPiece.setCurrentRotationPos(piece.getCurrentRotationPos());
		newPiece.setMapGrid(piece.getMapGrid());
		newPiece.setType(piece.getType());
		newPiece.setLandingCalculator(piece.getLandingCalculator());
		Point point = new Point();
		point.x = piece.getPoint().x;
		point.y = piece.getPoint().y;
		newPiece.setPoint(point);
		return newPiece;
	}
	
	@Override
	public Piece createRotatedPiece(Piece piece){
		Piece rotatedPiece = clonePiece(piece);
		int nextForm = rotatedPiece.getCurrentRotationPos() + 1;
		if(nextForm > 3) nextForm = 0;
		rotatedPiece.setCurrentRotationPos(nextForm);
		rotatedPiece.setMapGrid(getMapGridRotatedPiece(rotatedPiece, nextForm));
		return rotatedPiece;
	}
	
	private int[] getMapGridRotatedPiece(Piece piece, int rotation){
		int start = (int) ( 	( piece.getType() * (8 * 4) ) + (rotation * 8)	);
		int[] mapGrid = Arrays.copyOfRange(blocks, start, start + 8);
		return mapGrid;
	}
}
