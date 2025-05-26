package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;

public abstract class Tile extends Polygon implements GameObject {
	private Image img = null;
	int x, y;
	static int TILE_DIMENSION = TheAdventuresOfPloniAlmoni.TILE_DIMENSION;
	static Point[] staticPoints = new Point[] {new Point(0,0), new Point(0,TILE_DIMENSION), new Point(TILE_DIMENSION,TILE_DIMENSION), new Point(TILE_DIMENSION,0)};
	private Point[] points = new Point[4];
	int[][] arr;
	
	public Tile(int imgPath, int x, int y) {
		super(staticPoints, new Point(0,0), 0);
		this.x = x;
		this.y = y;
		for(int i = 0; i < staticPoints.length; i++) {
			points[i] = new Point(staticPoints[i].getX() + TILE_DIMENSION*x,
			staticPoints[i].getY() + TILE_DIMENSION*y);
		}
		
		File file = new File("src/game/images/sprite_" +(imgPath<10?"0":"") + imgPath +".png/");
		try { img = ImageIO.read(file); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public abstract Tile makeCopy(int x, int y);
	
	public void paint(Graphics brush) {
		arr = getGamePointsSeparateCoordinates(points);
		addOffset(arr);
		if(img != null) {
			brush.drawImage(img, arr[0][0], arr[1][0], TILE_DIMENSION, TILE_DIMENSION, null);
		}
	}
	
	public abstract void onCollision(Direction tempDirection);

}