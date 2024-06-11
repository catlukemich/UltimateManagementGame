package world;

import terrain.Terrain;

public class World {

	final public Terrain terrain;
	
	
	public World(Terrain terrain) {
		this.terrain = terrain;
	}
	
	
	public WorldVector3 createVector3(float x, float y, float z) {
		return WorldVector3.create(this.terrain, x, y, z);
	}
}
