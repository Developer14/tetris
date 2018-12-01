package cl.tetris;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.IOException;

public class Tetris extends Canvas implements KeyListener {
	private BufferStrategy strategy;
	private static int xBlocks = 10;
	private static int yBlocks = 20;
	private static int blockSize = 20;
	private static int pLateral = 120;
	public static final int WIDTH = xBlocks * blockSize;
	public static final int HEIGHT = yBlocks * blockSize;
	public static final int SPEED = 25;
	public static final int SLEEP_TIME = 25;
	private int sleep = SLEEP_TIME;
	private int figura, dx, dy, rotacion, obx, obRot;
	private int nextFigure, nextRotation;
	private int objectRot, leftRef, rightRef, bottomRef, speed, curSpeed, score, level, totLines;
	private long cicle;
	private boolean reachedBottom, keyLeft, keyRight, keyRot, pause;
	private boolean isPlaying;
	private int[][] data = new int[yBlocks][xBlocks];
	private int delay;
	private static final int TIME_DELAY = 60;
	private boolean showIntro;
	private Font fLarge = new Font("Arial", Font.BOLD, 40);
	private Font fSmall = new Font("Arial", Font.BOLD, 12);

	private BufferedImage[] block = new BufferedImage[7];
	private int[] blocks = {0,-2,  0,-1,  0,0,  0,1,	//linea
							-1,0,  0,0,  1,0,  2,0,
							0,-2,  0,-1,  0,0,  0,1,
							-1,0,  0,0,  1,0,  2,0,
							
							-1,0,  0,-1, 0,0,  0,1,		//figura T
							-1,0,  0,0,  1,0,  0,1,
							1,0,  0,-1, 0,0,  0,1,
							-1,0,  0,0,  1,0,  0,-1,
							
							-1,0,  0,0,  1,0,  1,1,		//figura L
							1,-1,  0,-1, 0,0,  0,1,
							-1,0,  0,0,  1,0,  -1,-1,
							-1,1,  0,-1, 0,0,  0,1,
							
							-1,0,  0,0,  1,0,  1,-1,	//figura J
							-1,-1,  0,-1, 0,0,  0,1,
							-1,0,  0,0,  1,0,  -1,1,
							1,1,  0,-1, 0,0,  0,1,
							
							-1,0, -1,1, 0,-1, 0,0,		//figura S
							-1,-1, 0,-1, 0,0, 1,0,
							-1,0, -1,1, 0,-1, 0,0,
							-1,-1, 0,-1, 0,0, 1,0,
							
							-1,-1, -1,0, 0,0, 0,1,		//figura Z
							0,-1, 1,-1, 0,0, -1,0,
							-1,-1, -1,0, 0,0, 0,1,
							0,-1, 1,-1, 0,0, -1,0,
							
							0,-1, 0,0, 1,-1, 1,0,		//figura O
							0,-1, 0,0, 1,-1, 1,0,
							0,-1, 0,0, 1,-1, 1,0,
							0,-1, 0,0, 1,-1, 1,0  };

	public Tetris() {
		JFrame v = new JFrame("Ultimate Tetris");
		JPanel p = (JPanel) v.getContentPane();
		setBounds(0, 0, WIDTH + pLateral, HEIGHT);
		p.setLayout(null);
		p.add(this);
		v.setBounds(0, 0, WIDTH + pLateral, HEIGHT + 32);
		v.setLocationRelativeTo(null);
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setResizable(false);
		v.setVisible(true);
		createBufferStrategy(4);
		strategy = getBufferStrategy();

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					cicle++;
					gameControl();
					refresh();
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException ie) {
					}
				}
			}
		}).start();

		v.addKeyListener(this);
		block[0] = loadImage("images/red.png");
		block[1] = loadImage("images/yellow.png");
		block[2] = loadImage("images/green.png");
		block[3] = loadImage("images/blue.png");
		block[4] = loadImage("images/gray.png");
		block[5] = loadImage("images/orange.png");
		block[6] = loadImage("images/purple.png");
	}

	private void iniciar() {
		cicle = 0;
		pause = false;
		level = 1;
		score = 0;
		speed = SPEED - (level * 3);
		curSpeed = speed;
		nextFigure = (int) (Math.random() * 7);
		nextFigure *= 8 * 4;
		nextRotation = (int) (Math.random() * 4);

		delay = TIME_DELAY;
		showIntro = true;
		newFigure();
		data = new int[yBlocks][xBlocks];

	}

	private void newFigure() {
		reachedBottom = false;
		dx = WIDTH / blockSize / 2;
		dy = 1;
		objectRot = 0;

		figura = nextFigure;
		rotacion = nextRotation;

		nextFigure = (int) (Math.random() * 7);
		nextFigure *= 8 * 4;
		nextRotation = (int) (Math.random() * 4);
	}

	private void gameControl() {

		if (isPlaying) {
			if (rotacion > 3) {
				rotacion = 0;
			}
			screenBounds();
			// movimiento vertical de la figura
			if (cicle % curSpeed == 0 && !reachedBottom && !pause) {
				dy += 1;
				checkMovement(1);
			}

			if (keyLeft && leftRef > 0 && !checkMovement(2)) {
				dx += obx;
				obx = 0;
			}

			if (keyRight && rightRef < xBlocks - 1 && !checkMovement(3)) {
				dx += obx;
				obx = 0;
			}

			if (keyRot && !checkRotation()) {
				rotacion += obRot;
				obRot = 0;
			}

			if (bottomRef == yBlocks) {
				dy -= 1;
				reachedBottom = true;
			}
			if (leftRef < 0) {
				dx = dx + 1;
			}
			if (rightRef > xBlocks - 1) {
				dx = dx - 1;
			}

			if (reachedBottom) {
				fillData();
				checkFull();
				newFigure();
			}
			if (data[1][5] != 0) {
				isPlaying = false;
			}
			// actualizar nivel y velocidad
			if (totLines >= 15 + level) {
				totLines = 0;
				level += 1;
				speed = SPEED - (level * 3);
			}
		}
	}

	private void screenBounds() {
		leftRef = 0;
		rightRef = 0;
		bottomRef = 0;
		int auxR = 0;
		int auxL = 0;
		int auxB = 0;
		
		for (int i = 0; i < 8; i += 2) {
			if (blocks[figura + objectRot + i + 1] <= auxL) {
				auxL = blocks[figura + objectRot + i + 1];
				leftRef = dx + auxL;
			}
			if (blocks[figura + objectRot + i + 1] >= auxR) {
				auxR = blocks[figura + objectRot + i + 1];
				rightRef = dx + auxR;
			}
			if (blocks[figura + objectRot + i] >= auxB) {
				auxB = blocks[figura + objectRot + i];
				bottomRef = dy + auxB;
			}
		}
	}

	private boolean checkMovement(int mov) {
		int px = 0, py = 0, refX = 0, refY = 0;
		boolean b = false;
		for (int i = 0; i < 8; i += 2) {
			py = (dy * blockSize + (blockSize * blocks[figura + objectRot + i])) / blockSize;
			px = (dx * blockSize + (blockSize * blocks[figura + objectRot + i + 1])) / blockSize;
			if (px < 0)
				px = 0;
			if (px > xBlocks - 1)
				px = xBlocks - 1;
			if (py > yBlocks - 1)
				py = yBlocks - 1;

			if (mov == 1) {
				refX = 0;
				refY = 1;
			}
			if (mov == 2)
				refX = -1;
			refY = 0;
			if (mov == 3) {
				refX = 1;
				refY = 0;
			}
			px += refX;
			py += refY;

			if (px < 0)
				px = 0;
			if (px > xBlocks - 1)
				px = xBlocks - 1;

			if (data[py][px] != 0) {
				switch (mov) {
				case 1:
					dy -= 1;
					reachedBottom = true;
					break;
				}
				b = true;
				break;
			} else {
				b = false;
			}
		}
		return b;
	}

	private boolean checkRotation() {
		int py = 0, px = 0, checkRot = 0, x = 0;
		boolean b = false, increment = false;
		checkRot = rotacion + 1;
		if (checkRot > 3)
			checkRot = 0;
		checkRot *= 8;
		if (dx == xBlocks - 1) {
			dx -= 1;
			x = 1;
			increment = true;
		}
		if (dx == 0) {
			dx += 1;
			x = -1;
			increment = true;
		}
		for (int i = 0; i < 8; i += 2) {
			py = (dy * blockSize + (blockSize * blocks[figura + checkRot + i])) / blockSize;
			px = (dx * blockSize + (blockSize * blocks[figura + checkRot + i + 1])) / blockSize;
			if (data[py][px] != 0) {
				if (increment)
					dx += x;

				b = true;
				break;
			}
		}
		return b;
	}

	private void checkFull() {
		boolean full;
		int lines = 0, val = 0;
		for (int i = 0; i < yBlocks; i++) {
			full = true;
			for (int j = 0; j < xBlocks; j++) {
				if (data[i][j] == 0)
					full = false;
			}
			if (full) {
				lines++;
				for (int y = i; y >= 0; y--) {
					for (int x = 0; x < xBlocks; x++) {
						val = 0;
						if (y - 1 >= 0) {
							val = data[y - 1][x];
						}
						data[y][x] = val;
					}
				}
			}
		}
		score += lines * (level * 100);
		totLines += lines;
	}

	private void fillData() {
		int px = 0, py = 0;

		for (int i = 0; i < 8; i += 2) {
			py = (dy * blockSize + (blockSize * blocks[figura + objectRot + i])) / blockSize;
			px = (dx * blockSize + (blockSize * blocks[figura + objectRot + i + 1])) / blockSize;
			data[py][px] = (figura / 8 / 4) + 1;
		}
	}

	private void drawFigure(int type, Graphics2D g) {
		int px = 0, py = 0, x = 0, y = 0, figura = 0, rotacion = 0;
		switch (type) {
		case 0:
			x = dx;
			y = dy;
			figura = this.figura;
			rotacion = this.rotacion;
			break; // dibujar figura actual
		case 1:
			x = WIDTH / 20 + 2;
			y = 1;
			figura = this.nextFigure;
			rotacion = this.nextRotation; // dibujar figura siguiente
		}
		objectRot = rotacion * 8;
		for (int i = 0; i < 8; i += 2) {
			py = y * blockSize + (blockSize * blocks[figura + objectRot + i]);
			px = x * blockSize + (blockSize * blocks[figura + objectRot + i + 1]);
			g.drawImage(block[figura / 8 / 4], px, py, blockSize, blockSize, this);
		}
	}

	private void drawBlocks(Graphics2D g) {
		for (int i = 0; i < yBlocks; i++) {
			for (int j = 0; j < xBlocks; j++) {
				if (data[i][j] != 0) {
					g.drawImage(block[data[i][j] - 1], j * blockSize, i * blockSize, blockSize,
							blockSize, this);
				}
			}
		}
	}

	private void refresh() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.fillRect(WIDTH, 0, pLateral, HEIGHT);
		g.setColor(Color.white);
		g.fillRect(WIDTH, 0, 2, HEIGHT);
		g.drawRect(WIDTH + 5, 0, pLateral - 15, pLateral - 15);
		g.drawString("Score " + score, WIDTH + 5, 120);
		g.drawString("Level " + level, WIDTH + 5, 140);

		if (isPlaying && !pause) {
			// dibujar figura siguiente
			drawFigure(1, g);

			// dibujar figura actual
			drawFigure(0, g);

			// dibujar bloques
			drawBlocks(g);
		} else if (pause) {
			g.drawString("Paused", 80, 150);
		} else {
			showIntro(g);
		}

		strategy.show();
	}

	void showIntro(Graphics2D g) {
		String s;
		if (showIntro) {
			s = "Ultimate";
			g.setFont(fLarge);
			g.setColor(Color.blue);
			g.drawString(s, 16, HEIGHT / 2 - 20);
			g.setColor(Color.white);
			g.drawString(s, 18, HEIGHT / 2 - 20 + 2);
			s = "Tetris";
			g.setColor(Color.blue);
			g.drawString(s, 40, HEIGHT / 2 - 20 + 33);
			g.setColor(Color.white);
			g.drawString(s, 42, HEIGHT / 2 + 2 - 20 + 33);
		}
		delay--;
		if (delay <= 0) {
			delay = TIME_DELAY;
			showIntro = !showIntro;
		}
	}

	public void keyPressed(KeyEvent a) {
		if (!pause) {
			if (a.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!isPlaying) {
					iniciar();
					isPlaying = true;
				}
			}
			if (a.getKeyCode() == KeyEvent.VK_UP) {
				obRot = 1;
				keyRot = true;
			} else if (a.getKeyCode() == KeyEvent.VK_LEFT) {
				obx = -1;
				keyLeft = true;
			} else if (a.getKeyCode() == KeyEvent.VK_RIGHT) {
				obx = 1;
				keyRight = true;
			} else if (a.getKeyCode() == KeyEvent.VK_DOWN) {
				sleep = 5;
				curSpeed = 2;
			}
		}
		if (a.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (pause) {
				pause = false;
			} else {
				pause = true;
			}
		}
	}

	public void keyReleased(KeyEvent a) {
		if (!pause) {
			if (a.getKeyCode() == KeyEvent.VK_LEFT) {
				keyLeft = false;
			} else if (a.getKeyCode() == KeyEvent.VK_RIGHT) {
				keyRight = false;
			} else if (a.getKeyCode() == KeyEvent.VK_DOWN) {
				sleep = SLEEP_TIME;
				curSpeed = speed;
			} else if (a.getKeyCode() == KeyEvent.VK_UP) {
				obRot = 0;
				keyRot = false;
			}
		}
	}

	public void keyTyped(KeyEvent a) {
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

	public static void main(String[] args) {
		new Tetris();
	}
}