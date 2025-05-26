package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

class Ploni implements GameObject {
	int TILE_DIMENSION = TheAdventuresOfPloniAlmoni.TILE_DIMENSION;
	int stars;
	static int savedStars;
	static Point[] staticPoints = new Point[] {new Point(0,0), new Point(0,60), new Point(60,60), new Point(60,0)};
	private Point[] points = new Point[4];
	private int animationFrame = 0;
	
	private Image img0, img1, img2, img3;
	private Image[] sprites;
	
	public int x, y, startx, starty;
	private Direction moveDirection;
	private boolean stopping = false;

	public Ploni(int x, int y) {
		this.x = x;
		this.y = y;
		this.startx = x;
		this.starty = y;
		moveDirection = Direction.STOPPED;
		stars = 0;
		for(int i = 0; i < staticPoints.length; i++) {
			points[i] = new Point(staticPoints[i].getX() + TILE_DIMENSION*x,
								  staticPoints[i].getY() + TILE_DIMENSION*y);
		}
		
		File file0 = new File("src/game/images/sprite_18.png/");
		File file1 = new File("src/game/images/sprite_19.png/");
		File file2 = new File("src/game/images/sprite_20.png/");
		File file3 = new File("src/game/images/sprite_21.png/");
		try {
			img0 = ImageIO.read(file0);
            img1 = ImageIO.read(file1);
            img2 = ImageIO.read(file2);
            img3 = ImageIO.read(file3);
            sprites = new Image[] {img0, img1, img2, img3};
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void move(Direction direction, boolean stoppedRequired) {
		if (!stoppedRequired || moveDirection.equals(Direction.STOPPED)) {
			moveDirection = direction;
			animationFrame = (animationFrame/4)*4;
		}
	}
	
	public void moveOne(Direction direction) {
		moveDirection = direction;
		stopping = true;
	}

	public void stop() {
		moveDirection = Direction.STOPPED;
	}
	
	public void addStar() {
		stars += 1;
	}
	
	public void save() {
		startx = x + (int) moveDirection.getPoint().getX();
		starty = y + (int) moveDirection.getPoint().getY();
		savedStars = stars;
	}
		
	public void reset() {
		x = startx;
		y = starty;
		stars = savedStars;
	}
	
	public void paint(Graphics brush) {
		if(/*!TheAdventuresOfPloniAlmoni.moving*/true) {
			Tile tempTile;
			tempTile = TheAdventuresOfPloniAlmoni.getTile(
					x + (int) moveDirection.getPoint().x,
					y + (int) moveDirection.getPoint().y);
			
			if(tempTile != null && moveDirection != Direction.STOPPED) {
				tempTile.onCollision(moveDirection);
			}
			
			int[][] arr = getGamePointsSeparateCoordinates(points);
			if (animationFrame == 3 || animationFrame == 7) {
				paint3(brush);
			}
			
			//This is the animation for the sprite
			Arrays.setAll(arr[0], i -> arr[0][i] + 15*(animationFrame%4)*(int)moveDirection.getPoint().getX());
			Arrays.setAll(arr[1], i -> arr[1][i] + 15*(animationFrame%4)*(int)moveDirection.getPoint().getY());
			animationFrame++;
			if(animationFrame == 8) {
				animationFrame = 0;
			}
			if(stopping && animationFrame%4 == 0) {
				stop();
				stopping = false;
			}
			addOffset(arr);
			brush.drawImage(sprites[(moveDirection != Direction.STOPPED)?animationFrame/2:0], arr[0][0], arr[1][0], TILE_DIMENSION, TILE_DIMENSION, null);
			}
		}
	
	private void paint3(Graphics brush) {
		x += moveDirection.getPoint().x;
		y += moveDirection.getPoint().y;
		
		for(int i = 0; i < staticPoints.length; i++) {
			points[i] = new Point(
					staticPoints[i].getX() + TILE_DIMENSION*x,
					staticPoints[i].getY() + TILE_DIMENSION*y);
		}
	}
}