package org.vmorales.tetris.validator;

import org.vmorales.tetris.component.Piece;


public interface Restriction {

	boolean movementAllowed(Piece piece);

}
