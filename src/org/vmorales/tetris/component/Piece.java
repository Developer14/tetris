package org.vmorales.tetris.component;

import java.awt.Point;

import org.vmorales.tetris.validator.LandingCalculator;

public class Piece {

	private int type;
	private Point point;
	private int currentRotationPos;
	private int[] mapGrid;

	private LandingCalculator landingCalculator;
	
	public int calculateLandingPointY(int[][] gameGrid){
		return landingCalculator.calculateLandingYpos(this, gameGrid);
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCurrentRotationPos() {
		return currentRotationPos;
	}

	public void setCurrentRotationPos(int currentRotationPos) {
		this.currentRotationPos = currentRotationPos;
	}

	public int[] getMapGrid() {
		return mapGrid;
	}

	public void setMapGrid(int[] mapGrid) {
		this.mapGrid = mapGrid;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public LandingCalculator getLandingCalculator() {
		return landingCalculator;
	}

	public void setLandingCalculator(LandingCalculator landingCalculator) {
		this.landingCalculator = landingCalculator;
	}

}
