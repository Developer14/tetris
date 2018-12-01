package org.vmorales.tetris.component;

import java.awt.Point;

public class GamePoint extends Point {

	private static final long serialVersionUID = 1L;

	public GamePoint(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean equals(Object point) {
		return ((Point) point).x == this.x;
	}

}