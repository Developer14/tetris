package org.vmorales.tetris.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.vmorales.tetris.component.GamePoint;
import org.vmorales.tetris.component.Piece;

public class TetrisUtils {

	public static int calculateLeftOffset(Piece piece){
		int min = 0;
		for (int i = 0; i < piece.getMapGrid().length; i+=2) {
			if(piece.getMapGrid()[i + 1]<min)
				min = piece.getMapGrid()[i + 1];
		}
		return min;
	}
	
	public static int calculateRightOffset(Piece piece){
		int max = 0;
		for (int i = 0; i < piece.getMapGrid().length; i+=2) {
			if(piece.getMapGrid()[i + 1] > max)
				max = piece.getMapGrid()[i + 1];
		}
		return max;
	}
	
	public static List<Point> calculateRealPoints(Piece piece){
		List<Point> realPoints = new ArrayList<Point>();
		int xPos = piece.getPoint().x;
		int yPos = piece.getPoint().y;
		// System.out.println("X: " + (xPos) + " | Y: " + (yPos));
		List<GamePoint> point = calculateLowerBlocks(piece);
		for (GamePoint p : point) {
			Point gp = new Point(xPos + p.x, yPos + p.y);
			realPoints.add(gp);
			
//			System.out.print("[X:" + gp.x + ", Y:" + gp.y + " => distancia Y: " + parcial + "]");
		}
//		System.out.println();
		return realPoints;
	}
	
	public static List<GamePoint> calculateLowerBlocks(Piece piece) {
		Map<Integer, GamePoint> pointMap = new LinkedHashMap<Integer, GamePoint>();
		int count = 0;
		GamePoint point = null;
		for (int i = 0; i < piece.getMapGrid().length; i += 2) {
			point = new GamePoint(piece.getMapGrid()[i + 1], piece.getMapGrid()[i]);
			pointMap.put(new Integer(count), point);
			count++;
		}
		return new ArrayList<GamePoint>(pointMap.values());
	}
}
