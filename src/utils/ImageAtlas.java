package utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageAtlas {

	public ImageAtlas(String path, int cols, int rows) {
		this(Assets.loadImage(path), rows, cols);
	}
	
	public ImageAtlas(BufferedImage image, int cols, int rows) {
		this.image = image;
		this.cols = cols;
		this.rows = rows;
		this.spriteWidth = image.getWidth() / cols;
		this.spriteHeight = image.getHeight() / rows;
		this.length = cols * rows;
		
		this.subImages = new ArrayList<BufferedImage>();
		for(int i = 0; i < this.length; i++) {
			Rectangle rect = this.getBoundsForIndex(i);
			BufferedImage subimage = this.image.getSubimage(rect.x, rect.y, rect.width, rect.height);
			this.subImages.add(subimage);
		}
	}
	
	public final BufferedImage image;
	public final ArrayList<BufferedImage> subImages;
	public final int cols;
	public final int rows;
	private int spriteWidth;
	private int spriteHeight;
	private int length;
	
	private Rectangle getBoundsForIndex(int index) {
		int col = index % this.cols;
		int row = index / this.cols;
		
		int startX = col * this.spriteWidth;
		int startY = row * this.spriteHeight;
		
		var bounds = new Rectangle(startX, startY, this.spriteWidth, this.spriteHeight);
		return bounds;
	}
	
	public BufferedImage getSubImage(int index) {
		return this.subImages.get(index);
	}
	
	public int getLength() {
		return this.length;
	}
}
