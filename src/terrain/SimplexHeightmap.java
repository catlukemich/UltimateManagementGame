package terrain;

import utils.SimplexNoise;

public class SimplexHeightmap  extends Heightmap{

	/**
	 * Make heights array for a simplex noise based heightmap.
	 * @param width
	 * @param height
	 * @param scale
	 * @param span
	 * @return
	 */
	private static byte[] makeHeights(int width, int height, float scale, float span) {
		int random_offset = (int) (Math.random() * 1_000);
		byte[] heights = new byte[width * height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int index = y * width + x;
				heights[index] = (byte) Math.round((SimplexNoise.noise(x / scale + random_offset, y / scale + random_offset) + 1) / 2  * span);
			}
		}
		return heights;
	}
	
	
	/**
	 * Create a simplex noise based heightmap with given dimensions
	 * and some default features.
	 * @param width
	 * @param height
	 */
	public SimplexHeightmap(int width, int height) {
		this(width, height, 80.0f, 6);
	}
	
	/**
	 * Create a simplex noise based heightmap, with a scale and a vertical span.
	 * @param width
	 * @param height
	 * @param scale
	 * @param span
	 */
	public SimplexHeightmap(int width, int height, float scale, float span) {
		super(width, height, 
				SimplexHeightmap.makeHeights(width, height, scale, span)
		);
		// TODO Auto-generated constructor stub
	}

}
