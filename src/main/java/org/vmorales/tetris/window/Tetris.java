package org.vmorales.tetris.window;

import java.awt.Canvas;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.vmorales.tetris.component.GameGrid;
import org.vmorales.tetris.component.Stage;
import org.vmorales.tetris.controller.BoundsInspector;
import org.vmorales.tetris.controller.ControlsListener;
import org.vmorales.tetris.controller.Engine;
import org.vmorales.tetris.controller.GameController;
import org.vmorales.tetris.validator.Restriction;
import org.vmorales.tetris.validator.impl.PieceCollisionCheck;
import org.vmorales.tetris.validator.impl.WallCollisionCheck;

public class Tetris {
	
	private Stage stage;
	private ControlsListener controlsListener;
	
	public Tetris(){
		stage = new Stage();
		controlsListener = new ControlsListener();
		GameGrid gameGrid = new GameGrid();
		BoundsInspector boundsInspector = new BoundsInspector(gameGrid);
		GameController gameController = new GameController(stage, controlsListener, boundsInspector);
		Engine engine = new Engine(gameController);
		Restriction wallCollisionCheck = new WallCollisionCheck();
		Restriction pieceCollisionCheck = new PieceCollisionCheck();
		boundsInspector.setPhantomGenerator(gameController);
		boundsInspector.setWallCollisionCheck(wallCollisionCheck);
		boundsInspector.setPieceCollisionCheck(pieceCollisionCheck);
		gameGrid.addFullLinesListener(gameController);
		
		initWindow();
		gameController.initController();
		engine.startGraphics();
		gameController.startGame();
		
	}
	
	private void initWindow(){
		JFrame v = new JFrame("Ultimate Tetris");
		JPanel p = (JPanel) v.getContentPane();
		v.addKeyListener(controlsListener);
		p.setLayout(null);
		p.add((Canvas)stage);
		v.setBounds(0, 0, Stage.WIDTH + Stage.INFO_PANEL_WIDTH, Stage.HEIGHT + 32);
//		v.setLocation(500, 500);
		v.setLocationRelativeTo(null);
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setResizable(false);
		v.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Tetris();
	}
}
