package world;

import terrain.Heightmap;
import terrain.Terrain;
import utils.Vector3;

public class WorldVector3 extends Vector3{
	
	public static WorldVector3 create(Terrain terrain, float x, float y, float z) {
		int width = terrain.getWidth();
		int height = terrain.getHeight();
		
		x = x % width;
		y = y % height;
		
		if (x < 0) x = x + width;
		if (y < 0) y = y + height;
		
		return new WorldVector3(terrain, x, y, z);
	}

	
	Terrain terrain;
	
	private WorldVector3(Terrain terrain, float x, float y, float z) {
		super(x, y, z);
		this.terrain = terrain;
	}

	
	public float distance(Vector3 other) {
		return this.distance(other, false);
	}
	
	/**
	 * Get the distance to other vector. It returns the closest distance respecting the world repeating mechanics.
	 * This method assumes that the input vector is also in the same world.
	 * @param other
	 * @return
	 */
	public float distance(Vector3 other, boolean excludeZ) {
		int width = this.terrain.getWidth();
		int height = this.terrain.getHeight();
		
		float dx;
		if (other.x < 0) {
			dx = Math.abs(this.x - other.x % width);
		} 
		else if(other.x > width) {
			dx = Math.abs(width - this.x + other.x % width);
		}
		else {
			dx = Math.abs(this.x - other.x);
		}

		
		float dy;
		if (other.y < 0) {
			dy = Math.abs(this.y - other.y % height);
		} 
		else if(other.y > height) {
			dy = Math.abs(height - this.y + other.y % height);
		}
		else {
			dy = Math.abs(this.y - other.y);
		}
		
		float dz = this.z - other.z;
		if (excludeZ) dz = 0;

		float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		return distance;
	}
}
	