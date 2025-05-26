package game;

class TileWall extends Tile {
	public TileWall(int x, int y) {
		super(0, x, y);
	}
	
	public TileWall(int imgPath, int x, int y) {
		super(imgPath, x, y);
	}
	
	public void onCollision(Direction tempDirection) {
		TheAdventuresOfPloniAlmoni.ploni.stop();
	}
	
	public Tile makeCopy(int x, int y) {
		return new TileWall(x, y);
	}
}