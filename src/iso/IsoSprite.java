package iso;

import java.awt.image.BufferedImage;
import iso.interfaces.IsoDrawable;
import utils.Assets;
import utils.Vector3;

public class IsoSprite implements IsoDrawable {

	/**
	 * Copy constructor.
	 * @param other
	 */
	public IsoSprite(IsoSprite other) {
		this(other.image);
		this.setLayer(other.layer);
	}
	
	public IsoSprite(String imagePath) {
		this(Assets.loadImage(imagePath));
	}
	
	public IsoSprite(BufferedImage image) {
		this.image = image;
	}
	
	Vector3 location = new Vector3(0, 0, 0);
	BufferedImage image;
	private float alpha = 1.0f;
	private int layer = 0;
	
	public void setLocation(Vector3 location) {
		this.location = location;
	}
	
	public Vector3 getLocation() {
		return this.location;
	}

	@Override
	public BufferedImage getImage() {
		return this.image;
	}
	
	@Override
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	public float getAlpha() {
		return this.alpha;
	}
	
	@Override
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public int getLayer() {
		return this.layer;
	}
}

