package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class Assets {

	private static HashMap<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
	
	public static BufferedImage loadImage(String filepath) {
		if(Assets.imageCache.containsKey(filepath))
			return Assets.imageCache.get(filepath);
		
		InputStream resource = Assets.class.getResourceAsStream(filepath);
		BufferedImage image = null;
		try {
			image = ImageIO.read(resource);
		} catch (Exception e) {
			System.err.println("Can't load image: " + filepath);
			throw new RuntimeException(e);
		}
		Assets.imageCache.put(filepath, image);
		
		return image;
	}
	
	
	private static HashMap<String, ImageAtlas> atlasCache = new HashMap<>(); 

	public static ImageAtlas loadImageAtlas(String filepath, int cols, int rows) {
		String key = filepath + String.format("/%d-%d", cols, rows);
		if(Assets.atlasCache.containsKey(key)) {
			return Assets.atlasCache.get(key); 
		}
		
		BufferedImage image = Assets.loadImage(filepath);
		ImageAtlas atlas = new ImageAtlas(image, cols, rows);
		Assets.atlasCache.put(key, atlas);
		
		return atlas;
	}
}
