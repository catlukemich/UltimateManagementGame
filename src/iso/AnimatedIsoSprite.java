package iso;

import java.awt.image.BufferedImage;

import iso.interfaces.AnimatedIsoDrawable;
import utils.ImageAtlas;

public class AnimatedIsoSprite extends IsoSprite implements AnimatedIsoDrawable {

	public AnimatedIsoSprite(ImageAtlas atlas) {
		super(atlas.image);
		this.atlas = atlas;
	}

	ImageAtlas atlas;
	float time = 0;
	byte fps = 10;
	int currentFrame = 0;
	
	@Override
	public ImageAtlas getAtlas() {
		return this.atlas;
	}
	
	@Override
	public BufferedImage getImage() {
		return AnimatedIsoDrawable.super.getImage();
	}
	
	@Override
	public int getCurrentFrame() {
		return this.currentFrame;
	}
	
	@Override
	public void setCurrentFrame(int frame) {
		this.currentFrame = frame;
	}
	
	@Override
	public float getTime() {
		return this.time;
	}
	@Override
	public void setTime(float time) {
		this.time = time;
	}

	@Override
	public int getFPS() {
		return this.fps;
	}
	
	
}
