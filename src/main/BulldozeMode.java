package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import iso.AnimatedIsoSprite;
import iso.IsoSprite;
import iso.PickResults;
import terrain.Tile;
import terrain.TilePick;
import terrain.TileSpriteSet;
import utils.Assets;
import utils.Callable;

public class BulldozeMode extends Mode implements MouseListener, MouseMotionListener{

	public BulldozeMode() {
		IsoSprite highlightSprite = new IsoSprite("/highlight.png");
		highlightSprite.setLayer(1);
		
		TileSpriteSet tileSet = new TileSpriteSet("/tiles/highlight_red/tile_");
		this.highlight = new AreaHighlight(tileSet);
	}
	
	AreaHighlight highlight;
	
	@Override
	public void enable() {
		Globals.canvas.addMouseListener(this);
		Globals.canvas.addMouseMotionListener(this);
		Globals.scene.addDrawableSet(this.highlight);
	}
	
	@Override
	public void disable() {
		Globals.canvas.removeMouseListener(this);
		Globals.canvas.removeMouseMotionListener(this);
		Globals.scene.removeDrawableSet(this.highlight);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		if(pick != null) {
			this.highlight.start(pick.tile);
		}	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		if(pick != null) {
			this.highlight.start(pick.tile);
			this.highlight.end(pick.tile);
		}
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		if(pick != null) {
			this.highlight.start(pick.tile);
		}	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ArrayList<Tile> tiles = this.highlight.getTiles();
		for (Tile tile : tiles) {
			tile.bulldoze();
			// Display explosion animation:
			AnimatedIsoSprite explosion = new AnimatedIsoSprite(Assets.loadImageAtlas("/explosion.png", 6, 1));
			explosion.setLocation(tile.getLocation());
			explosion.setLayer(2);
			explosion.playOnce(new Callable() {
				@Override
				public void call() {
					Globals.scene.removeDrawable(explosion);
				}
			});
			Globals.scene.addDrawable(explosion);
			
		}
		this.highlight.clear();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		if(pick != null) {
			this.highlight.end(pick.tile);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}

	
}
