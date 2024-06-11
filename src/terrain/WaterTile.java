package terrain;

import java.awt.image.BufferedImage;
import java.util.Random;

import iso.interfaces.AnimatedIsoDrawable;
import main.Globals;
import utils.Assets;
import utils.Callable;
import utils.ImageAtlas;

public class WaterTile extends Tile implements Callable{
	
	private static ImageAtlas waterAtlas = Assets.loadImageAtlas("/tiles/water.png", 4, 1);
	

	public WaterTile(Terrain terrain, short x, short y) {
		super(terrain, x, y);
		this.frame = (byte) ((Math.random() * 4) % 4);
	}
	
	byte frame;
	
	
	@Override
	public void onScreenEnter() {
		Globals.updater.addInterval(this, 0.25f);
	}
	
	@Override
	public void onScreenLeave() {
		Globals.updater.removeInterval(this);
	}
	
	@Override
	public void call() {
		this.frame += 1;
		this.frame = (byte) (this.frame % waterAtlas.getLength());
	}
	
	@Override
	public BufferedImage getImage() {
		return waterAtlas.getSubImage(this.frame);
	}
}
