package game;

class TileSticky extends Tile {
	public TileSticky(int x, int y) {
		super(13, x, y);
	}
	
	public void onCollision(Direction direction) {
		TheAdventuresOfPloniAlmoni.ploni.moveOne(direction);
	}
	
	public Tile makeCopy(int x, int y) {
		return new TileSticky(x, y);
	}
}