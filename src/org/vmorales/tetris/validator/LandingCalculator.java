package org.vmorales.tetris.validator;

import org.vmorales.tetris.component.Piece;

public interface LandingCalculator {

	int calculateLandingYpos(Piece piece, int[][] gameGrid);
}
