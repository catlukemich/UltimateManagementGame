package iso.interfaces;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import iso.DrawHints;
import iso.IsoViewport;
import utils.Vector3;

public interface IsoDrawableAdapter extends IsoDrawable {
	
	public IsoDrawable getDrawable();
	
	public default void draw(IsoViewport viewport, Graphics2D graphics, DrawHints hints) {
		IsoDrawable drawable = this.getDrawable();
		drawable.draw(viewport, graphics, hints);
	}
	
	public default void setLocation(Vector3 location) {
		IsoDrawable drawable = this.getDrawable();
		drawable.setLocation(location);
	}
	
	public default Vector3 getLocation() {
		IsoDrawable drawable = this.getDrawable();
		return drawable.getLocation();
	}
	
	public default BufferedImage getImage() {
		IsoDrawable drawable = this.getDrawable();
		return drawable.getImage();
	}
	
	public default void setImage(BufferedImage image) {
		IsoDrawable drawable = this.getDrawable();
		drawable.setImage(image);
	}
	
	public default float getAlpha() {
		IsoDrawable drawable = this.getDrawable();
		return drawable.getAlpha();
	}
	
	public default void setAlpha(float alpha) {
		IsoDrawable drawable = this.getDrawable();
		drawable.setAlpha(alpha);
	}
}
