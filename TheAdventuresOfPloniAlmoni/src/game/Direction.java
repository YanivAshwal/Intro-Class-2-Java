package game;

public enum Direction {
	LEFT (new Point(-1,0)),
	RIGHT (new Point(1,0)),
	UP (new Point(0,-1)),
	DOWN (new Point(0,1)),
	STOPPED (new Point(0,0));

	private Point point;
	
	Direction(Point point) {
		this.point = point;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public static Direction getDirection(int x, int y) {
		if(x != 0 && y != 0) {
			throw new IllegalArgumentException("x and y can't both be nonzero");
		}
		if(x < 0) {
			return LEFT;
		}
		if(x > 0) {
			return RIGHT;
		}
		if(y > 0) {
			return UP;
		}
		if(y < 0) {
			return DOWN;
		}
		return STOPPED;
	}
}