package game;

class TileWallBouncy extends TileWall {
	private int rot;
	
	public TileWallBouncy(int x, int y, int rot) {
		super(rot==0?11:(rot+21), x, y);
		
	}
	
	public void onCollision(Direction tempDirection) {
		
		//Inverting the direction
		if(tempDirection == Direction.LEFT) tempDirection = Direction.RIGHT;
		else if(tempDirection == Direction.RIGHT) tempDirection = Direction.LEFT;
		else if(tempDirection == Direction.UP) tempDirection = Direction.DOWN;
		else if(tempDirection == Direction.DOWN) tempDirection = Direction.UP;
		TheAdventuresOfPloniAlmoni.ploni.moveOne(tempDirection);
	}
	
	public Tile makeCopy(int x, int y) {
		return new TileWallBouncy(x, y, rot);
	}
}