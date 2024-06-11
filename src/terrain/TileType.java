package terrain;

import java.util.Collection;
import java.util.HashMap;

public class TileType {

	private static HashMap<String, TileType> allTypes = new HashMap<>();
	
	static {
		for(byte hTL = 0; hTL <= 1; hTL++) {
			for(byte hTR = 0; hTR <= 1; hTR++) {
				for(byte hBL = 0; hBL <= 1; hBL++) {
					for(byte hBR = 0; hBR <= 1; hBR++) {
						String key = String.format("%d%d%d%d", hTL, hTR, hBL, hBR);
						TileType type = new TileType(hTL, hTR, hBL, hBR); 
						TileType.allTypes.put(key, type);
					}
				}
			}
		}
		TileType.allTypes.remove("1111"); // Remove this invalid tile type.
	}
	
	public static TileType[] getAllTypes() {
		TileType[] typesArray = new TileType[15];
		Collection<TileType> values = TileType.allTypes.values();
		typesArray = values.toArray(typesArray);
		return typesArray;
	}
	
	public static TileType getByHeights(byte hTL, byte hTR, byte hBL, byte hBR) {
		String key = String.format("%d%d%d%d", hTL, hTR, hBL, hBR);
		return TileType.allTypes.get(key);
	}
	
	
	byte hTL, hTR, hBL, hBR;

	private TileType(byte hTL, byte hTR, byte hBL, byte hBR) {
		this.hTL = hTL;
		this.hTR = hTR;
		this.hBL = hBL;
		this.hBR = hBR;
	}	
	
	public String getFilenamePart() {
		return String.format("%d%d%d%d",  hTL, hTR, hBL, hBR);
	}
	
	public float getMean() {
		return (this.hTL + this.hTR + this.hBL + this.hBR) / (float)4;
	}
}
