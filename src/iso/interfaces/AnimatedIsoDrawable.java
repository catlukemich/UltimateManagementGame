package iso.interfaces;

import java.awt.image.BufferedImage;

import main.Globals;
import utils.Animated;
import utils.Callable;
import utils.ImageAtlas;

public interface AnimatedIsoDrawable extends IsoDrawable, Animated {

	public ImageAtlas getAtlas();
	
	@Override
	public default BufferedImage getImage() {
		ImageAtlas atlas = this.getAtlas();
		int currentFrame = this.getCurrentFrame();
		return atlas.getSubImage(currentFrame);
	}
	
	@Override
	public default void animate(float delta) {
		int fps = this.getFPS();
		int currentFrame = this.getCurrentFrame();
		float secondsPerFrame = 1 / (float)fps;
		
		float time = this.getTime();
		time += delta;
		if(time > secondsPerFrame) {
			time = time - secondsPerFrame;
			currentFrame += 1;
		}
		this.setTime(time);
		
		ImageAtlas atlas = this.getAtlas();
		currentFrame = currentFrame % atlas.getLength();
		this.setCurrentFrame(currentFrame);
	}
	
	public default void play() {
		Globals.updater.addAnimated(this);
	}
	
	public default void playOnce() {
		this.playOnce(null);
	}
	
	public default void playOnce(Callable callback) {
		Globals.updater.addAnimated(this);
		
		ImageAtlas atlas = this.getAtlas();
		int fps = this.getFPS();
		
		float secondsPerFrame = 1 / (float)fps;
		float time = secondsPerFrame * (atlas.getLength());
		float delay = time;
		Globals.updater.callLater(new Callable() {
			@Override
			public void call() {
				Globals.updater.removeAnimated(AnimatedIsoDrawable.this);
			}
		}, delay);
		if(callback != null) {
			Globals.updater.callLater(callback, delay);
		}
	}

	public int getCurrentFrame();
	public void setCurrentFrame(int frame);
	public float getTime();
	public void setTime(float time);
	
	public int getFPS();
	
}
