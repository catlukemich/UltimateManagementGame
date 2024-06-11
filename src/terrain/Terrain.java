package terrain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import iso.IsoSprite;
import iso.IsoViewport;
import iso.interfaces.IsoDrawable;
import iso.interfaces.IsoDrawableSet;
import utils.Vector3;

public class Terrain implements IsoDrawableSet{

	public Terrain(Heightmap heightmap) {
		this.heightmap = heightmap;
		this.width = (short) (heightmap.width - 1);
		this.height = (short) (heightmap.height - 1);
		this.create();
	}
	
	final Heightmap heightmap;
	private Tile[] tiles;
	short width, height;
	
	private byte snowLevel = 5;
	
	
	private void create() {
		int size = (this.heightmap.width - 1) * (this.heightmap.height - 1);
		this.tiles = new Tile[size];
		Heightmap hm = this.heightmap;
		
		int index = 0;
		for(int y = 0; y < hm.height - 1; y++) {
			for(int x = 0; x < hm.width - 1; x++) {
				Tile newTile = this.createTile(x, y);
				this.tiles[index] = newTile;
				index++;
			}
		}
	}
	
	public Heightmap getHeightmap() {
		return this.heightmap;
	}
	
	
	public int getWidth() {
		return this.heightmap.width - 1;
	}
	
	public int getHeight() {
		return this.heightmap.height - 1;
	}
	
	/**
	 * Create tile for a given coordinate.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return A new Tile
	 */
	private Tile createTile(int x, int y) {
		
		byte hTL = (byte) (heightmap.getHeight(x, y, (byte) 0) );
		byte hTR = (byte) (heightmap.getHeight(x + 1, y, (byte) 0));
		byte hBL = (byte) (heightmap.getHeight(x, y + 1, (byte) 0));
		byte hBR = (byte) (heightmap.getHeight(x + 1, y + 1, (byte) 0));
		
		Tile newTile;
		if(hTL == 0 && hTR == 0 && hBL == 0 && hBR == 0) {
			newTile = new WaterTile(this, (short) x, (short) y);
		}
		else {
			newTile = new Tile(this, (short) x, (short) y);
		}
		
		return newTile;
	}


	public Tile getTile(int x, int y) {
		if(x < 0 || x >= this.width || y < 0 || y >= this.height) return null;
		int index = y * this.width + x;
		return this.tiles[index];
	}
	
	private void setTile(int x, int y, Tile tile) {
		if(x < 0 || x >= this.width || y < 0 || y >= this.height) return;
		int index = y * this.width + x;
		this.tiles[index] = tile;
	}
	
	@Override
	public Iterable<IsoDrawable> getDrawables() {
		return Arrays.asList(this.tiles);
	}
	
	public byte getSnowLevel() {
		return this.snowLevel;
	}
	
	/**
	 * Raise the terrain at heightmap x and y coordinate.
	 * @param hmX Heightmap x coordinate
	 * @param hmY Heightmap y coordinate
	 */
	public void raise(int hmX, int hmY) {
		ArrayList<Point> terraformedPoints = this.terraform(hmX, hmY, 1);
		this.replaceTiles(terraformedPoints);
	}
	
	/**
	 * Lower the terrain at heightmap x and y coordinate.
	 * @param hm_x Heightmap x coordinate
	 * @param hm_y Heightmap y coordinate
	 */
	public void lower(int hmX, int hmY) {
		ArrayList<Point> terraformedPoints = this.terraform(hmX, hmY, -1);
		this.replaceTiles(terraformedPoints);
	}
	
	public ArrayList<Point> terraform(int hmX, int hmY, int delta) {
		var terraformedPoints = new ArrayList<Point>();
		byte currentHeight = (byte) (this.heightmap.getHeight(hmX, hmY, (byte)0) + delta) ;
		int currenDistance = 0;
		
		boolean done = false;
		while(!done) {
			int y, x;
			
			// The top (north) row:
			boolean topDone = true;
			y = hmY - currenDistance;
			for(x = hmX - currenDistance; x <= hmX + currenDistance; x++) {
				byte existingHeight = this.heightmap.getHeight(x, y, (byte) currentHeight);
				if((delta == 1 && existingHeight < currentHeight) || (delta == -1 && existingHeight > currentHeight)) {
					byte newHeight = currentHeight;
					this.heightmap.setHeight(x, y, newHeight);
					terraformedPoints.add(new Point(x, y));
					topDone = false;
				}
			}
			
			// The bottom (south) row:
			boolean bottomDone = true;
			y = hmY + currenDistance;
			for(x = hmX - currenDistance; x <= hmX + currenDistance; x++) {
				byte existingHeight = this.heightmap.getHeight(x, y, (byte) currentHeight);
				if((delta == 1 && existingHeight < currentHeight) || (delta == -1 && existingHeight > currentHeight)) {
					byte newHeight = currentHeight;
					this.heightmap.setHeight(x, y, newHeight);
					terraformedPoints.add(new Point(x, y));
					bottomDone = false;
				}
			}
			
			// The left (west) row:
			boolean leftDone = true;
			x = hmX - currenDistance;
			for(y = hmY - currenDistance; y < hmY + currenDistance; y++) {
				byte existingHeight = this.heightmap.getHeight(x, y, (byte) currentHeight);
				if((delta == 1 && existingHeight < currentHeight) || (delta == -1 && existingHeight > currentHeight)) {
					byte newHeight = currentHeight;
					this.heightmap.setHeight(x, y, newHeight);
					terraformedPoints.add(new Point(x, y));
					leftDone = false;
				}
			}
			
			// The right (east) row:
			boolean rightDone = true;
			x = hmX + currenDistance;
			for(y = hmY - currenDistance; y < hmY + currenDistance; y++) {
				byte existingHeight = this.heightmap.getHeight(x, y, (byte) currentHeight);
				if((delta == 1 && existingHeight < currentHeight) || (delta == -1 && existingHeight > currentHeight)) {
					byte newHeight = currentHeight;
					this.heightmap.setHeight(x, y, newHeight);
					terraformedPoints.add(new Point(x, y));
					rightDone = false;
				}
			}

			
			if(topDone && bottomDone && leftDone && rightDone) {
				done = true;
			}
			
			if (delta == 1) currentHeight--;
			else currentHeight++;
			currenDistance++;
		}
		
		return terraformedPoints;
	}
	
	private void replaceTiles(ArrayList<Point> terraformedPoints) {
		for(Point point : terraformedPoints) {
			for(int x = point.x - 1; x <= point.x; x++) {
				for(int y = point.y -1; y <= point.y; y++) {
					Tile oldTile = this.getTile(x, y);
					if(oldTile != null) {
						Tile newTile = this.createTile(x, y);
						newTile.bulldoze();
						this.setTile(x, y, newTile);
					}
				}
			}
		}
	}

	public void setHeight(int hmX, int hmY, byte targetHeight) {
		byte height = this.heightmap.getHeight(hmX, hmY, targetHeight);
		
		if (targetHeight > height) {
			while(this.heightmap.getHeight(hmX, hmY, targetHeight) < targetHeight) {
				this.raise(hmX, hmY);
			}
		}
		if (targetHeight < height) { 
			while(this.heightmap.getHeight(hmX, hmY, targetHeight) > targetHeight) {
				this.lower(hmX, hmY);
			}
		}
	}

	@Override
	public HashSet<IsoDrawable> cull(IsoViewport viewport) {
		Vector3 center = viewport.getCenter();
		int distance = viewport.getDrawDistance() + 1;
		
		int startX = (int) (center.x - distance);
		int endX = (int) (center.x + distance);
		int startY = (int) (center.y - distance);
		int endY = (int) (center.y + distance);
		
		int width = this.heightmap.width - 1;
		int height = this.heightmap.height - 1;
		
		HashSet<IsoDrawable> culled = new HashSet<>();
		for(int x = startX; x <= endX; x++) {
			for(int y = startY; y <= endY; y++) {
				int tileX = x;
				int tileY = y;
				if (tileX < 0) tileX = width + x;
				if (tileY < 0) tileY = height + y;
				tileX = tileX % width;
				tileY = tileY % height;	
				
				Tile tile = this.getTile(tileX, tileY);
				if(tile != null) {
					culled.add(tile);
				}
			}
		}
		return culled;
		
	}

}