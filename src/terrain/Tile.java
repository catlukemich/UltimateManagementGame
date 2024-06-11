package terrain;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;

import iso.IsoSprite;
import iso.IsoViewport;
import iso.interfaces.IsoDrawable;
import iso.interfaces.IsoDrawableAdapter;
import main.Globals;
import utils.Assets;
import utils.Callable;
import utils.Vector2;
import utils.Vector3;
import world.WorldVector3;

public class Tile implements IsoDrawable{
	
	private static float TILES_VERTICAL_DIFF = 0.2f;
	
	
	public Tile(Terrain terrain, short x, short y) {
		this.terrain = terrain;
		this.x = x;
		this.y = y;
		this.image = this.loadTileImage(false);
	}

	private Terrain terrain;
	public final short x, y;
	
	private BufferedImage image;
	private HashSet<Object> objects; // Set of objects on this tile. If no objects are present it is null to save memory.

	
	protected byte calculateHeight() {
		Heightmap heightmap = this.terrain.getHeightmap();
		byte hTL = (byte) (heightmap.getHeight(this.x, this.y, (byte) 0) );
		byte hTR = (byte) (heightmap.getHeight(this.x + 1, this.y, (byte) 0));
		byte hBL = (byte) (heightmap.getHeight(this.x, this.y + 1, (byte) 0));
		byte hBR = (byte) (heightmap.getHeight(this.x + 1, this.y + 1, (byte) 0));
		byte height = (byte)Math.min(
			Math.min(hTL, hTR),
			Math.min(hBL, hBR)
		);
		return height;
	}
	
	/**
	 * Load a tile image depending on the snow level and the tile base height
	 * @return {@link BufferedImage} image
	 */
	private BufferedImage loadTileImage(boolean bulldozed) {
		byte snowLevel = this.terrain.getSnowLevel();
		
		String filename; // Filename of the image to load.
		BufferedImage image; // Image for the tile sprite.
		
		TileType tileType = this.getTileType();
		float mean = tileType.getMean();
		
		byte height = this.calculateHeight();
		float meanHeight = (height + mean) / 2;
		
		// Determine the correct filename for the tile image:
		if(meanHeight == 0) {
			filename = "/tiles/water.png";
		}
		else {
			String prefix = "/tiles/grass/";
			if(bulldozed) {
				prefix = "/tiles/bare/";
			}
			else if(meanHeight > snowLevel) {
				prefix = "/tiles/snow/";
			}	
			String part = tileType.getFilenamePart();
			filename = String.format(prefix + "tile_" + part + ".png");
		}
		
		image = Assets.loadImage(filename);
		return image;
	}
	
	public TileType getTileType() {
		Heightmap heightmap = this.terrain.getHeightmap();
		byte height = this.calculateHeight();
		byte hTL = (byte) (heightmap.getHeight(this.x, this.y, height) - height);
		byte hTR = (byte) (heightmap.getHeight(this.x + 1, this.y, height) - height);
		byte hBL = (byte) (heightmap.getHeight(this.x, this.y + 1, height) - height);
		byte hBR = (byte) (heightmap.getHeight(this.x + 1, this.y + 1, height) - height);
		return TileType.getByHeights(hTL, hTR, hBL, hBR);
	}
	
	@Override
	public Vector3 getLocation() {
		float height = this.calculateHeight();
		return WorldVector3.create(this.terrain, this.x + 0.5f, this.y + 0.5f, height * TILES_VERTICAL_DIFF);
	}
	
	public void addObject(Object object) {
		if(this.objects == null) {
			this.objects = new HashSet<Object>();
		}
		this.objects.add(object);
	}
	
	public void removeObject(Object object) {
		if(this.objects != null) {
			this.objects.remove(object);
		}
		if(this.objects.size() == 0) {
			this.objects = null;
		}
	}

	@Override
	public BufferedImage getImage() {
		return this.image;
	}

	@Override
	public Object handlePick(IsoViewport viewport, Point mouse) {
		Object result = IsoDrawable.super.handlePick(viewport, mouse);
		if(result == this) {
			// Find the nearest corner:
			Heightmap hm = this.terrain.heightmap;
			byte height = this.calculateHeight();
			byte hTL = hm.getHeight(this.x, this.y, height);
			byte hTR = hm.getHeight(this.x + 1, this.y, height);
			byte hBL = hm.getHeight(this.x, this.y + 1, height);
			byte hBR = hm.getHeight(this.x + 1, this.y + 1, height);
			TileCorner corner_tl = new TileCorner(this.x, this.y, new Vector3(this.x , this.y , hTL * TILES_VERTICAL_DIFF));
			TileCorner corner_tr = new TileCorner(this.x + 1, this.y, new Vector3(this.x + 1f, this.y, hTR * TILES_VERTICAL_DIFF));
			TileCorner corner_bl = new TileCorner(this.x, this.y + 1, new Vector3(this.x , this.y + 1, hBL * TILES_VERTICAL_DIFF));
			TileCorner corner_br = new TileCorner(this.x + 1, this.y + 1, new Vector3(this.x + 1, this.y + 1, hBR * TILES_VERTICAL_DIFF));
			
			TileCorner[] corners = {
					corner_tl, corner_tr, corner_bl, corner_br
			};
				
			float minDist = 1000;
			TileCorner nearest = corner_bl;
			for (TileCorner corner : corners) {
				Vector2 cornerPosition = viewport.project(corner.location);
				float dx = cornerPosition.x - mouse.x;
				float dy = cornerPosition.y - mouse.y;
				float distance = (float) Math.sqrt(dx * dx + dy * dy);
				if(distance < minDist) {
					minDist = distance;
					nearest = corner;
				}
			}
			
			return new TilePick(this, nearest);
		}
		return null;
	}

	public void bulldoze() {
		this.image = this.loadTileImage(true);
		double randomDuration = Math.random() * 5;
		Globals.updater.callLater(new Callable() {
			@Override
			public void call() {
				Tile.this.image = Tile.this.loadTileImage(false);
			}
		}, (float) (1 + randomDuration));
	}
	
}
