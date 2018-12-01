package org.vmorales.tetris.validator.impl;

import org.vmorales.tetris.component.Piece;
import org.vmorales.tetris.component.Stage;
import org.vmorales.tetris.controller.ActionPiece.Direction;
import static org.vmorales.tetris.util.TetrisUtils.*;
import org.vmorales.tetris.validator.Restriction;

public class WallCollisionCheck implements Restriction {

	private Direction direction;

	public WallCollisionCheck() {
	}

	@Override
	public boolean movementAllowed(Piece currentPiece) {
		int aux = 0;
		// System.out.println(Arrays.toString(currentPiece.getMapGrid()));
		int xPos = currentPiece.getPoint().x;
		// System.out.println("Loff: " + leftOff + " | Roff: " + rightOff +
		// " | xPos: " + xPos);
		int offset = (direction.equals(Direction.MOVE_LEFT)) ? calculateLeftOffset(currentPiece)
				: calculateRightOffset(currentPiece);
		aux = xPos + offset + direction.getDeltaX();

		if (aux >= 0 && aux < Stage.X_BLOCKS) {
			return true;
		}
		return false;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
