package terrain;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Heightmap {
	
	/**
	 * Load heightmap from file in the classpath.
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Heightmap load(String path, int width, int height) {
		InputStream resource = Heightmap.class.getResourceAsStream(path);
		InputStreamReader reader = new InputStreamReader(resource);
		BufferedReader buffered = new BufferedReader(reader);
		
		byte[] heights = new byte[width * height];
		int index = 0;
		String line;
		try {
			while((line = buffered.readLine()) != null) {
				String[] split = line.split(" ");
				for (String elem : split) {
					byte value = Byte.parseByte(elem);
					heights[index] = value;
					index++;
				}
			}
			return new Heightmap(width, height, heights);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Create a flat heightmap with given width and height.
	 * @param width
	 * @param height
	 */
	public Heightmap(int width, int height) {
		this(width, height, new byte[width * height]);
	}
	
	/**
	 * Create a heightmap with given dimensions and heights array.
	 * @param width
	 * @param height
	 * @param heights
	 */
	public Heightmap(int width, int height, byte[] heights) {
		this.width = width;
		this.height = height;
		this.heights = heights;
		this.realign();
	}
	
	final public int width, height;
	public byte[] heights;
	
	
	public byte getHeight(int x, int y, byte fallback) {
		if(x < 0 || x > this.width - 1 || y < 0 || y > this.height - 1) 
			return fallback;
		int index = y * this.width + x;
		return heights[index];
	}
	
	void setHeight(int x, int y, byte value) {
		if(x < 0 || x > this.width - 1 || y < 0 || y > this.height - 1)
			return;
		int index = y * this.width + x;
		this.heights[index] = value;
	}
	
	public byte[] getHeights() {
		return this.heights;
	}
	
	private void realign() {
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				byte h_current = this.getHeight(x, y, (byte)0);
				
	            byte h_bl = this.getHeight(x - 1, y + 1, h_current);
	            byte h_b  = this.getHeight(x, y + 1, h_current);
	            byte h_br = this.getHeight(x + 1, y + 1, h_current);
	            byte h_r  = this.getHeight(x + 1, y , h_current);
	
	            h_bl = this.calcNewHeight(h_current, h_bl);
	            h_b  = this.calcNewHeight(h_current, h_b);
	            h_br = this.calcNewHeight(h_current, h_br);
	            h_r  = this.calcNewHeight(h_current, h_r);
	
	            this.setHeight(x - 1, y + 1, h_bl);
	            this.setHeight(x, y + 1, h_b);
	            this.setHeight(x + 1, y + 1, h_br);
	            this.setHeight(x + 1, y, h_r);
			}
		}
	}
	
	private byte calcNewHeight(byte h_current, byte h_other) {
	    byte ret_height = h_other;
	    if(h_other > h_current + 1) {
	        ret_height = (byte)(h_current + 1);
	    }
	    else if(h_other < h_current - 1) {
	        ret_height = (byte)(h_current - 1);
	    }
	    return ret_height;
	}
	
	public void print() {
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				System.out.print(this.getHeight(x, y, (byte)0)) ;
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
}
