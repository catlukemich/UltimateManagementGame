package iso.interfaces;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;

import iso.DrawHints;
import iso.IsoViewport;
import utils.Vector2;
import utils.Vector3;

public interface IsoDrawable {
	
	/**
	 * A map that maps from IsoDrawable to the number of viewport that IsoDrawable appears.
	 */
	static HashMap<IsoDrawable, Integer> drawablesNumViewports = new HashMap<>();
	
	public default Vector3 getLocation() {return new Vector3(); };
	public default void setLocation(Vector3 location) {};
	
	public BufferedImage getImage();
	public default void setImage(BufferedImage image) {};
	
	public default float getAlpha(){ return 1.0f; };
	public default void setAlpha(float alpha){};
	
	public default void setLayer(int layer) {};
	public default int getLayer() {return 0; };
	
	/**
	 * Fired when this drawable enters a viewport.
	 * @param viewport
	 */
	public default void onViewportEnter(IsoViewport viewport) {
		if(!IsoDrawable.drawablesNumViewports.containsKey(this)) {
			IsoDrawable.drawablesNumViewports.put(this, 1);
			this.onScreenEnter();
		}
		else {
			int numViewports = IsoDrawable.drawablesNumViewports.get(this);
			numViewports += 1;
			IsoDrawable.drawablesNumViewports.put(this, numViewports);
		}
	};
	
	/**
	 * Fired when this drawable leaves a viewport.
	 * @param viewport
	 */
	public default void onViewportLeave(IsoViewport viewport) {
		if(IsoDrawable.drawablesNumViewports.containsKey(this)) {
			int numViewports = IsoDrawable.drawablesNumViewports.get(this);
			numViewports -= 1;
			if(numViewports == 0) {
				IsoDrawable.drawablesNumViewports.remove(this);
				this.onScreenLeave();
			}
		}
	};
	
	/**
	 * Fired when drawable appears on screen - that is - it enters any viewport.
	 */
	public default void onScreenEnter() {
	};
	
	/**
	 * Fired when drawable leaves the screen - that is - it leaves all viewports.
	 */
	public default void onScreenLeave() {
	};
	
	
	public  default void draw(IsoViewport viewport, Graphics2D graphics, DrawHints hints) {
		Vector3 location = hints != null ? hints.location : this.getLocation();
		BufferedImage image = this.getImage();
		float zoom = viewport.getZoom();
		
		Vector2 position = viewport.project(location);
		int width  = (int) (image.getWidth() * zoom);
		int height = (int) (image.getHeight() * zoom);
		
		position.x -= width  / 2;
		position.y -= height / 2;
		
		float alpha = Math.min(this.getAlpha(), hints.maxAlpha);
		Composite old_composite = graphics.getComposite();
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		graphics.setComposite(composite);
		
		this.drawImage(graphics, image, position, (int) (width), (int)(height));

		graphics.setComposite(old_composite);
	}
	
	public default void drawImage(Graphics2D graphics, BufferedImage image, Vector2 position, int width, int height) {
		graphics.drawImage(image, 
				(int)position.x, (int)position.y, 
				(int) (width), (int)(height),  
				(ImageObserver)null);
	}
	
	
	public default Object handlePick(IsoViewport viewport, Point mouse) {
		BufferedImage image = this.getImage();
		Vector3 location = this.getLocation();
		float zoom = viewport.getZoom();
		
		Vector2 position = viewport.project(location); // Position of the top-left corner of the sprite's image on screen.
		int width  = (int) (image.getWidth() * zoom);
		int height = (int) (image.getHeight() * zoom);
		
		position.x -= width  / 2;
		position.y -= height / 2;
		
		Rectangle imageBounds = new Rectangle(
				(int)position.x, (int)position.y,
				width, height);
		
		if(imageBounds.contains(mouse)) {
			Point relative = new Point(
				(int) (mouse.x - position.x),
				(int) (mouse.y - position.y)
			);
			relative.x /= zoom;
			relative.y /= zoom;
					
			int rgb = image.getRGB(relative.x, relative.y);
			Color color = new Color(rgb, true);
			if(color.getAlpha() > 0) {
				return this;
			}
		}
		return null;
	}
}
