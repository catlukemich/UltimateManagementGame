package main;

import java.util.ArrayList;

import iso.IsoSprite;
import iso.sets.HashIsoDrawableSet;
import terrain.Tile;
import terrain.TileSpriteSet;
import terrain.TileType;
import utils.Vector3;

public class AreaHighlight extends HashIsoDrawableSet {

	public AreaHighlight(TileSpriteSet tileSpriteSet) {
		this.tileSpriteSet = tileSpriteSet;
	}
	
	TileSpriteSet tileSpriteSet;
	Tile startTile;
	ArrayList<Tile> tiles = new ArrayList<>(); // List of tiles covered when highlighting.
	
	public void start(Tile startTile) {
		this.startTile = startTile;
	}
	
	public void end(Tile endTile) {
		int startX = Math.min(this.startTile.x, endTile.x);
		int endX = Math.max(this.startTile.x, endTile.x);
		int startY = Math.min(this.startTile.y, endTile.y);
		int endY = Math.max(this.startTile.y, endTile.y);
		
		ArrayList<Tile> coveredTiles = new ArrayList<Tile>();
		for(int x = startX; x <= endX; x++) {
			for(int y = startY; y <= endY; y++) {
				Tile tile = Globals.world.terrain.getTile(x, y);
				coveredTiles.add(tile);
			}
		}
		
		this.tiles = coveredTiles;
		
		this.clear();
		for (Tile tile : coveredTiles) {
			Vector3 tileLocation = tile.getLocation();
			TileType tileType = tile.getTileType();

			IsoSprite singleHighlightSprte = this.tileSpriteSet.getByTileType(tileType);
			IsoSprite singleHighlight = new IsoSprite(singleHighlightSprte);
			singleHighlight.setLayer(2);
			singleHighlight.setLocation(tileLocation);
			this.addDrawable(singleHighlight);
		}
	}
	
	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}
	
}
