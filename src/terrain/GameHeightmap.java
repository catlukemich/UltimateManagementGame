package terrain;

public class GameHeightmap extends Heightmap {

	private static byte[] makeHeights(int width, int height) {
		SimplexHeightmap base_hm = new SimplexHeightmap(width, height, 100, 1);
		SimplexHeightmap mountains_hm = new SimplexHeightmap(width, height, 30, 20);
		byte[] base_heights = base_hm.getHeights();
		byte[] mountains_heights = mountains_hm.getHeights();
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int index = y * width + x;
				byte base = base_heights[index];
				if(base_heights[index] == 0) {
					continue;  // Continue when on the water level.
				}
				int cutoff = 12;
				if(mountains_heights[index] > cutoff) {
					// Else "place" the mountain.
					base += mountains_heights[index] - cutoff;
					base_heights[index] = base;
				}
			}
		}
		return base_heights;
	}
	
	
	public GameHeightmap(int width, int height) {
		super(width, height,
			GameHeightmap.makeHeights(width, height)
		);
	}

}
