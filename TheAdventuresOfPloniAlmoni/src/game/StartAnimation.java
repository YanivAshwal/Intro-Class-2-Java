package game;

import java.awt.Color;
import java.awt.Graphics;

class StartAnimation extends Polygon implements GameObject {
	int x, y;
	static Point[] staticPoints = new Point[] {new Point(0,0), new Point(0,2000), new Point(2000,2000), new Point(2000,0)};
	public StartAnimation(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}
	
	public StartAnimation() {
		super(staticPoints, new Point(30,30), 0);
	}
	
	public void paint(Graphics brush) {
		int[][] arr = getGamePointsSeparateCoordinates(this.getPoints());
		brush.setColor(Color.BLACK);
		brush.fillPolygon(arr[0], arr[1], 4);
		if(position.y < 1200) {
			position = new Point(position.x, position.y + 40);
			rotate(3);
		}
	}
}