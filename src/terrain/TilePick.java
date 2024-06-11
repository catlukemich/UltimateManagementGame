package terrain;

public class TilePick {

	public TilePick(Tile tile, TileCorner corner) {
		this.tile = tile;
		this.corner = corner;
	}
	
	final public Tile tile;
	final public TileCorner corner; // The corner of the tile nearest to the mouse cursor.
}
