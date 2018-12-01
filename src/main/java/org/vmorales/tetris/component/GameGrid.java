package org.vmorales.tetris.component;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.vmorales.tetris.controller.FullLinesListener;

public class GameGrid {

	private int[][] grid;
	private List<FullLinesListener> listeners;
	
	public GameGrid(){
		grid = new int[Stage.Y_BLOCKS][Stage.X_BLOCKS];
		listeners = new ArrayList<FullLinesListener>();
	}
	
	public void fillGrid(List<Point> realPoints, int pieceId){
		for (Point p : realPoints) {
			grid[p.y][p.x] = pieceId + 1;
		}
		checkForFullRows();
	}
	
	private void checkForFullRows() {
		List<Integer> fullRowIndexList = new ArrayList<Integer>();
		boolean full;
		int totFullLines = 0, val = 0;
		for (int i = 0; i < Stage.Y_BLOCKS; i++) {
			full = true;
			for (int j = 0; j < Stage.X_BLOCKS; j++) {
				if (grid[i][j] == 0)
					full = false;
			}
			
			if (full) {
				fullRowIndexList.add(new Integer(i));
				totFullLines++;
				for (int y = i; y >= 0; y--) {
					for (int x = 0; x < Stage.X_BLOCKS; x++) {
						val = 0;
						if (y - 1 >= 0) {
							val = grid[y - 1][x];
						}
						grid[y][x] = val;
					}
				}
			}
		}
		notifyListeners(fullRowIndexList);
	}
	
	public int[][] getGrid() {
		return grid;
	}

	private void notifyListeners(List<Integer> fullRowIndexList) {
		for (FullLinesListener listener : listeners) {
			listener.update(fullRowIndexList);
		}
	}
	
	public void addFullLinesListener(FullLinesListener listener){
		listeners.add(listener);
	}

}
