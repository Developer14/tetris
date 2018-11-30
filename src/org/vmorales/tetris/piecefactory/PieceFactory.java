package org.vmorales.tetris.piecefactory;

import org.vmorales.tetris.component.Piece;

public interface PieceFactory {

	public Piece createPiece();
	public Piece clonePieceWithOffsetY(Piece piece, int newYpos);
	Piece createRotatedPiece(Piece piece);
}
