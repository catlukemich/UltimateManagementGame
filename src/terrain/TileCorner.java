package terrain;

import utils.Vector3;

/**
 * The corner of a tile and it's properties.
 */
public class TileCorner {

	public TileCorner(int hm_x, int hm_y, Vector3 location) {
		this.hmX = hm_x;
		this.hmY = hm_y;
		this.location = location;
	}
	
	public final int hmX, hmY; // The heightmap x and y coordinates, this corner points to.
	public final Vector3 location;
	
}
