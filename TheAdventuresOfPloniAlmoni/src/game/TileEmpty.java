package game;

class TileEmpty extends Tile {
	public TileEmpty(int x, int y) {
		super(12, x, y);
	}
	
	public void onCollision(Direction tempDirection) {
		//Do nothing
	}
	
	public Tile makeCopy(int x, int y) {
		return new TileEmpty(x, y);
	}
}