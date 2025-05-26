package game;

class Star extends Tile {

	public Star(int x, int y) {
		super(16, x, y);
	}

	@Override
	public void onCollision(Direction tempDirection) {
		TheAdventuresOfPloniAlmoni.ploni.addStar();
		TheAdventuresOfPloniAlmoni.setTileEmpty(this.x, this.y);
	}
	
	public Tile makeCopy(int x, int y) {
		return new Star(x, y);
	}
}