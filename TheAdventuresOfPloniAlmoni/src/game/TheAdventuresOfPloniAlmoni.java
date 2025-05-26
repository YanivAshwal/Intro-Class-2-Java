package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("serial")
class TheAdventuresOfPloniAlmoni extends Game implements KeyListener {
	public final static int TILE_DIMENSION = 60;
	public final static int GAME_SIZE = 30;
	private final static int MOVE_SPEED = 30;
	public static Ploni ploni = new Ploni(1,1);
	
	public static Direction movingDir = Direction.STOPPED;
	public static boolean moving = false;
	public static int offsetx = 0;
	public static int offsety = 0;
	
	protected static TheAdventuresOfPloniAlmoni game;
	private static String gameStr = null;
	
	public static Tile[][] board = new Tile[30][30];
	public GameState saveState = new GameState(board, offsetx, offsety);
	
	StartAnimation s = new StartAnimation();
	static Point[] staticPoints = new Point[] {new Point(0,0), new Point(0,60), new Point(60,60), new Point(60,0)};

	public TheAdventuresOfPloniAlmoni() {
		super("The Adventures of Ploni Almoni",614,630);
		this.setFocusable(true);
		this.requestFocus();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		try {
            gameStr = Files.readString(Path.of("src/game/gameFile.txt/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		board = gameToBoard(gameStr);
	}
	
	public void paint(Graphics brush) {
		if(movingDir == Direction.STOPPED) {
			if (ploni.y + offsety/60 == -1) {
				movingDir = Direction.DOWN;
			} 
			else if (ploni.y + offsety/60 == 10) {
				movingDir = Direction.UP;
			}
			else if (ploni.x + offsetx/60 == -1) {
				movingDir = Direction.RIGHT;
			}
			else if (ploni.x + offsetx/60 == 10) {
				movingDir = Direction.LEFT;
			}
		}
		else {
			moveView(movingDir);
			moving = true;
			if(offsetx % 600 == 0 && offsety % 600 == 0) {
				movingDir = Direction.STOPPED;
				moving = false;
			}
		}
		
		for (int row = 0; row < GAME_SIZE; row++) {
			for (int col = 0; col < GAME_SIZE; col++) {
				if(board[col][row] != null) {
					board[col][row].paint(brush);
				}
			}
		}
		ploni.paint(brush);
		super.repaint();
		
		if(ploni.stars == 9) {
			win();
		}
		s.paint(brush);
	}

	public static void main(String[] args) {
		game = new TheAdventuresOfPloniAlmoni();
		game.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			ploni.move(Direction.RIGHT, true);
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			ploni.move(Direction.DOWN, true);
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			ploni.move(Direction.LEFT, true);
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			ploni.move(Direction.UP, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public static void setTileEmpty(int x, int y) {
		board[y][x] = new TileEmpty(x,y);
	}
	
	public static Tile getTile(int x, int y) {
		return board[y][x];
	}
	
	private void moveView(Direction direction) {
		offsetx += MOVE_SPEED*direction.getPoint().getX();
		offsety += MOVE_SPEED*direction.getPoint().getY();
	}

	class GameState {
		private Tile[][] board = new Tile[30][30];
		private int offsetx = TheAdventuresOfPloniAlmoni.offsetx;
		private int offsety = TheAdventuresOfPloniAlmoni.offsety;
		
		public GameState(Tile[][] board, int offsetx, int offsety) {
			this.board = board;
			this.offsetx = offsetx;
			this.offsety = offsety;
		}
		
		public Tile[][] getBoard() {
			return board;
		}
	}
	
	public void resetLevel() {
		board = saveState.getBoard();
		
		offsetx = saveState.offsetx;
		offsety = saveState.offsety;
		ploni.reset();
	}
	
	public void saveLevel() {
		board[ploni.starty][ploni.startx] = new TileCheckpoint(ploni.startx, ploni.starty, true);
		Tile[][] saveBoard = new Tile[GAME_SIZE][GAME_SIZE];
		for (int row = 0; row < GAME_SIZE; row++) {
			for (int col = 0; col < GAME_SIZE; col++) {
				saveBoard[row][col] = board[row][col].makeCopy(col, row);
			}
		}
		saveState = new GameState(saveBoard, offsetx, offsety);
	}

	public static Tile[][] gameToBoard(String gameStr) {
		gameStr = gameStr.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");
		Tile[][] gameBoard = new Tile[30][30];
		for(int col = 0; col < 30; col++) {
			for(int row = 0; row < 30; row++) {
				Tile tempTile = null;
				char chr = gameStr.charAt(col + 30*row);
				switch(chr) {
					case '.':
						tempTile = new TileEmpty(col,row);
						break;
					case 'W':
						tempTile = new TileWall(col,row);
						break;
					case 'w':
						tempTile = new TileWallBouncy(col,row,1);
						break;
					case 'a':
						tempTile = new TileWallBouncy(col,row,0);
						break;
					case 'd':
						tempTile = new TileWallBouncy(col,row,2);
						break;
					case 's':
						tempTile = new TileWallBouncy(col,row,3);
						break;
					case 'T':
						tempTile = new TileSticky(col,row);
						break;
					case 'S':
						tempTile = new TileSpikes(col,row);
						break;
					case 'C':
						tempTile = new TileCheckpoint(col,row);
						break;
					case '*':
						tempTile = new Star(col,row);
						break;
					case '+':
						tempTile = new TileLocked(col,row,6) {
							public void onCollision(Direction direction) {
								if(TheAdventuresOfPloniAlmoni.ploni.stars >= n) {
									board[this.y][this.x] = new TileCheckpoint(this.x,this.y);
								}
								else {
									TheAdventuresOfPloniAlmoni.ploni.stop();
								}
							}
						};
						break;
				}
				if(Character.isDigit(chr)) {
					tempTile = new TileLocked(col,row,Character.getNumericValue(chr));
				}
				gameBoard[row][col] = tempTile;
			}
		}
		return gameBoard;
	}
	
	static public void win() {
		System.out.println("YOU WIN!");
	}
}