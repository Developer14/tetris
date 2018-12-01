package org.vmorales.tetris.controller;

import org.vmorales.tetris.component.Piece;

public interface PhantomGenerator {

	void switchToRotatedPiece(Piece piece);
	void createPhantomPiece(int offsetY);

}
