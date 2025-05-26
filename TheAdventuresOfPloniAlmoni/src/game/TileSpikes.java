package game;

class TileSpikes extends Tile {
	public TileSpikes(int x, int y) {
		super(17, x, y);
	}
	
	public void onCollision(Direction tempDirection) {
		TheAdventuresOfPloniAlmoni.ploni.stop();
		TheAdventuresOfPloniAlmoni.game.resetLevel();
	}
	
	public Tile makeCopy(int x, int y) {
		return new TileSpikes(x, y);
	}
}