package org.vmorales.tetris.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.List;

import org.vmorales.tetris.component.GameData;
import org.vmorales.tetris.component.Piece;
import org.vmorales.tetris.component.Stage;
import org.vmorales.tetris.piecefactory.ConcretePieceFactory;

public class GameController implements ActionGame, PhantomGenerator, FullLinesListener {

	private ControlsListener controlsListener;
	private Stage stage;
	private BoundsInspector boundsInspector;
	private GameData gameData;
	private boolean paused;
	private boolean gameInProgress;
	private Piece currentPiece;
	private Piece phantomPiece;
	private Piece nextPiece;
	private BufferStrategy bufferStrategy;
	private Graphics2D graphics;
	
	public GameController(Stage stage, ControlsListener controlsListener, BoundsInspector boundsInspector){
		this.stage = stage;
		this.controlsListener = controlsListener;
		this.boundsInspector = boundsInspector;
		this.controlsListener.setActionPiece(this.boundsInspector);
		this.controlsListener.setActionGame(this);
		gameData = new GameData();
	}
	
	public void initController(){
		this.stage.createBufferStrategy(4);
		bufferStrategy = stage.getBufferStrategy();
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		System.out.println("se instancia GameController");
	}
	
	public void startGame(){
		nextPiece = ConcretePieceFactory.INSTANCE.createPiece();
		newPiece();
		setGameInProgress(true);
		paused = false;
	}
	
	private void newPiece(){
		currentPiece = nextPiece;
		boundsInspector.generatePhantomPiece(currentPiece);
		((ControlsListener)controlsListener).setCurrentPiece(currentPiece);
//		System.out.println("type		: " + currentPiece.getType());
//		System.out.println("curRotationPos	: " + currentPiece.getCurrentRotationPos());
//		System.out.println(Arrays.toString(currentPiece.getMapGrid()));
		nextPiece = ConcretePieceFactory.INSTANCE.createPiece();
	}
	

	@Override
	public void switchToRotatedPiece(Piece piece) {
		currentPiece.setCurrentRotationPos(piece.getCurrentRotationPos());
		currentPiece.setMapGrid(piece.getMapGrid());
	}
	
	@Override
	public void createPhantomPiece(int offsetY) {
		phantomPiece = ConcretePieceFactory.INSTANCE.clonePieceWithOffsetY(currentPiece, offsetY);
	}
	
	public void controlGame(){
		stage.drawStage(gameData, graphics);
		if(isGameInProgress() && !paused){
			
			gameData.increaseCycleCounter();
			stage.drawStackOfBlocks(boundsInspector.getGameGrid(), graphics);
//			stage.drawPhantomPiece(phantomPiece, graphics);
			stage.drawCurrentPiece(currentPiece, graphics);
			stage.drawNextPiece(nextPiece, graphics);
			if(	(gameData.getCycleCounter() % gameData.getSpeed()) == 0	){
				performFall();
			}
		}
		
		bufferStrategy.show();
	}

	private void performFall(){
		if(phantomPiece == null || (phantomPiece != currentPiece && currentPiece.getPoint().y != phantomPiece.getPoint().y)){
			currentPiece.getPoint().y = currentPiece.getPoint().y + 1;
		}else{
			boundsInspector.fillGameGrid(phantomPiece);
			newPiece();
		}
	}
	
	@Override
	public void update(List<Integer> fullRowIndexList) {
		if(!fullRowIndexList.isEmpty()){
			stage.performBlockExplodeAnimation(fullRowIndexList, graphics);
		}
		gameData.setTotalFullLines(gameData.getTotalFullLines() + fullRowIndexList.size());
	}

	@Override
	public void performFallAcceleration(boolean accelerate) {
		gameData.toggleSpeedUp(accelerate);
	}

	@Override
	public void togglePauseResumeGame() {
		paused = !paused;
	}
	
	@Override
	public boolean areControlsEnabled() {
		return isGameInProgress() && !paused;
	}
	
	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		this.gameInProgress = gameInProgress;
	}

	public Piece getCurrentPiece() {
		return currentPiece;
	}

}
