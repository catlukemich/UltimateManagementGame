package terrain;
import java.util.HashMap;
import iso.IsoSprite;

/**
 * A sprite set that contains 15 sprites for each tile type.
 */
final public class TileSpriteSet {

	public TileSpriteSet(String pathPrefix) {
		TileType[] allTypes = TileType.getAllTypes();
		for (TileType tileType : allTypes) {
			String part = tileType.getFilenamePart();
			String path = pathPrefix + part + ".png";
			IsoSprite tileSprite = new IsoSprite(path);
			this.typeMap.put(tileType, tileSprite);
		}
	}
		
	private HashMap<TileType, IsoSprite> typeMap = new HashMap<>();
	
	
	public IsoSprite getByTileType(TileType type) {
		return typeMap.get(type);
	}
}
