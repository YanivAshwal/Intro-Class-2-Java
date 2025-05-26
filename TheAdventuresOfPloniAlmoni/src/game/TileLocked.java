package game;

class TileLocked extends Tile {
	int n;
	
	public TileLocked(int x, int y, int n) {
		super(n+1, x, y);
		this.n = n;
	}
	
	public void onCollision(Direction direction) {
		if(TheAdventuresOfPloniAlmoni.ploni.stars >= n) {
			TheAdventuresOfPloniAlmoni.setTileEmpty(this.x, this.y);
		}
		else {
			TheAdventuresOfPloniAlmoni.ploni.stop();
		}
	} 
	
	public Tile makeCopy(int x, int y) {
		return new TileLocked(x, y, n);
	}
}