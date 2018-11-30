package org.vmorales.tetris.validator.impl;

import java.awt.Point;
import java.util.List;

import org.vmorales.tetris.component.Piece;
import org.vmorales.tetris.util.TetrisUtils;
import org.vmorales.tetris.validator.LandingCalculator;

public class DefaultLandingCalculator implements LandingCalculator {

	public DefaultLandingCalculator(){}
	
	@Override
	public int calculateLandingYpos(Piece piece, int[][] gameGrid) {
		List<Point> points = TetrisUtils.calculateRealPoints(piece);
		int deltaY = gameGrid.length;
		int yPos = piece.getPoint().y;
		int yRefMin = yPos;
		int tot = 0;
		int arr[] = new int[points.size()];
		for (int i = 0; i < arr.length; i++) arr[i] = -1;
		
		for_principal:for (int z = 0; z < points.size() ; z++) {
			Point point = points.get(z);
			if(point.y > yRefMin) yRefMin = point.y;
			yPos = point.y;
			for (int i = point.y; i < gameGrid.length; i++) {
				if(gameGrid[i][point.x] != 0){
					arr[z] = i - yPos - 1;
					continue for_principal;
				}
				if(i == gameGrid.length - 1)
					tot++;
			}
		}
		if(tot == points.size()){ 
			deltaY = gameGrid.length - yRefMin - 1;
		}else{
			int m = gameGrid.length;
			for (int i : arr) {
				if(i != -1 && i < m) m = i;
			}
			deltaY = m;
		}
		return deltaY;
	}

}
