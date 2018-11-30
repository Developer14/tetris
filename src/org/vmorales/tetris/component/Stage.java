package org.vmorales.tetris.component;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.vmorales.tetris.controller.FullLinesListener;

public class Stage extends Canvas {

	private static final long serialVersionUID = 1L;

	public static final int X_BLOCKS = 10;
	public static final int Y_BLOCKS = 30;
	public static final int BLOCK_SIZE = 12;
	public static final int INFO_PANEL_WIDTH = 120;
	public static final int WIDTH = X_BLOCKS * BLOCK_SIZE;
	public static final int HEIGHT = Y_BLOCKS * BLOCK_SIZE;
	private static final Color BACKGROUND_COLOR = Color.black;
	
	private BufferedImage[] blockImages;

	public Stage() {
		blockImages = new BufferedImage[8];
		setBounds(0, 0, Stage.WIDTH + Stage.INFO_PANEL_WIDTH, Stage.HEIGHT);
		initComponent();
	}

	public void initComponent() {
		blockImages[0] = loadImage("images/red.png");
		blockImages[1] = loadImage("images/yellow.png");
		blockImages[2] = loadImage("images/green.png");
		blockImages[3] = loadImage("images/blue.png");
		blockImages[4] = loadImage("images/gray.png");
		blockImages[5] = loadImage("images/orange.png");
		blockImages[6] = loadImage("images/purple.png");
		blockImages[7] = loadImage("images/phantom.png");
	}

	public void drawStage(GameData gameData, Graphics2D g) {
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.fillRect(WIDTH, 0, INFO_PANEL_WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.fillRect(WIDTH, 0, 2, HEIGHT);
		g.drawRect(WIDTH + 5, 0, INFO_PANEL_WIDTH - 15, INFO_PANEL_WIDTH - 15);
		g.drawString("Score		: " + gameData.getScore(), WIDTH + 5, 120);
		g.drawString("Full Lines: " + gameData.getTotalFullLines(), WIDTH + 5, 140);
		g.drawString("Level		: " + gameData.getLevel(), WIDTH + 5, 160);
		
	}

	public void drawPhantomPiece(Piece currentPiece, Graphics2D g) {
		drawPiece(currentPiece.getPoint(), 7, currentPiece.getMapGrid(), g);
	}
	
	public void drawCurrentPiece(Piece piece, Graphics2D g) {
		drawPiece(piece.getPoint(), piece.getType(), piece.getMapGrid(), g);
	}
	
	public void drawNextPiece(Piece piece, Graphics2D g) {
		drawPiece(new Point(14, 4), piece.getType(), piece.getMapGrid(), g);
	}
	
	private void drawPiece(Point point, int imageIndex, int[] mapGrid, Graphics2D g){
		int px = 0, py = 0;
		for (int i = 0; i < 8; i += 2) {
			py = point.y * BLOCK_SIZE + (BLOCK_SIZE * mapGrid[i]);
			px = point.x * BLOCK_SIZE + (BLOCK_SIZE * mapGrid[i + 1]);
			g.drawImage(blockImages[imageIndex], px, py, BLOCK_SIZE, BLOCK_SIZE, this);
		}
	}
	
	public void drawStackOfBlocks(int[][] gameData, Graphics2D g) {
		for (int i = 0; i < Y_BLOCKS; i++) {
			for (int j = 0; j < X_BLOCKS; j++) {
				if (gameData[i][j] != 0) {
					g.drawImage(blockImages[gameData[i][j] - 1], j * BLOCK_SIZE, i * BLOCK_SIZE,
							BLOCK_SIZE, BLOCK_SIZE, this);
				}
			}
		}
	}
	
	public void performBlockExplodeAnimation(List<Integer> fullRowIndexList, Graphics2D g){
		System.out.println("explode animation on");
	}
	
	private BufferedImage loadImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

}
