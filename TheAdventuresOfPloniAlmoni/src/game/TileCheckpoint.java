package game;

class TileCheckpoint extends Tile {
	boolean used = false;
	
	public TileCheckpoint(int x, int y) {
		super(14, x, y);
	}
	
	public TileCheckpoint(int x, int y, boolean used) {
		super(used?15:14, x, y);
		this.used = used;
	}
	
	public void onCollision(Direction direction) {
		if(!used) {
			TheAdventuresOfPloniAlmoni.ploni.save();
			TheAdventuresOfPloniAlmoni.game.saveLevel();
			used = true;
		}
	}
	
	public Tile makeCopy(int x, int y) {
		return new TileCheckpoint(x, y, used);
	}
}