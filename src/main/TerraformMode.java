package main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import iso.IsoSprite;
import iso.PickResults;
import iso.sets.HashIsoDrawableSet;
import terrain.Heightmap;
import terrain.TilePick;
import utils.Vector3;

public class TerraformMode extends Mode implements MouseListener, MouseMotionListener{
	
	public TerraformMode() {
		this.cornerHighlight.setLayer(1);
	}
	
	private TerraformOperation operation = TerraformOperation.LEVEL;
	private IsoSprite cornerHighlight = new IsoSprite("/corner_highlight.png");
	
	private HashIsoDrawableSet levelingHighlight = new HashIsoDrawableSet();
	private boolean isLeveling;
	private int levelingStartX, levelingStartY;
	private int levelingEndX, levelingEndY;
	private byte levelingHeight;
	
	
	@Override
	public void enable() {
		Globals.canvas.addMouseListener(this);
		Globals.canvas.addMouseMotionListener(this);
		Globals.scene.addDrawableSet(this.levelingHighlight);
		Globals.scene.addDrawable(this.cornerHighlight);
	}
	
	@Override
	public void disable() {
		Globals.canvas.removeMouseListener(this);
		Globals.canvas.removeMouseMotionListener(this);
		Globals.scene.addDrawableSet(this.levelingHighlight);
		Globals.scene.removeDrawable(this.cornerHighlight);
	}
	
	public void setOperation(TerraformOperation operation) {
		this.operation = operation;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		
		if(pick != null) {
			this.cornerHighlight.setLocation(pick.corner.location);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() != 1) return;
		
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		
		if(pick != null) {
			if(this.operation == TerraformOperation.RAISE) {
				Globals.world.terrain.raise(pick.corner.hmX, pick.corner.hmY);
			}
			else if(this.operation == TerraformOperation.LOWER) {
				Globals.world.terrain.lower(pick.corner.hmX, pick.corner.hmY);
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() != 1) return;
		
		if(this.operation == TerraformOperation.LEVEL) {
			PickResults results = Globals.viewport.pick(e.getPoint());
			TilePick pick = results.getSingleByClass(TilePick.class);
			
			if(pick != null) {
				Heightmap heightmap = Globals.world.terrain.getHeightmap();
				this.levelingHeight = heightmap.getHeight(pick.corner.hmX, pick.corner.hmY, this.levelingHeight);
				
				this.levelingStartX = pick.corner.hmX;
				this.levelingStartY = pick.corner.hmY;
				this.isLeveling = true;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.isLeveling) {
			PickResults results = Globals.viewport.pick(e.getPoint());
			TilePick pick = (TilePick) results.getSingleByClass(TilePick.class);
			
			if(pick != null) {
				this.levelingEndX = pick.corner.hmX;
				this.levelingEndY = pick.corner.hmY;
				
				ArrayList<Point> coordinates = this.getLevelingPoints();
				
				this.levelingHighlight.clear();
				for(Point coordinate : coordinates) {
					Heightmap heightmap = Globals.world.terrain.getHeightmap();
					byte height = heightmap.getHeight(coordinate.x, coordinate.y, this.levelingHeight);
					
					Vector3 cornerLocation = new Vector3(coordinate.x, coordinate.y, height * 0.2f);
					IsoSprite cornerHighlight = new IsoSprite("/corner_highlight.png");
					cornerHighlight.setLayer(2);
					cornerHighlight.setLocation(cornerLocation);
					this.levelingHighlight.addDrawable(cornerHighlight);
				}
			}		
		}
	}
	
	private ArrayList<Point> getLevelingPoints() {
		int startX = Math.min(this.levelingStartX, levelingEndX);
		int startY = Math.min(this.levelingStartY, levelingEndY);
		
		int endX = Math.max(this.levelingStartX, levelingEndX);
		int endY = Math.max(this.levelingStartY, levelingEndY);
		
		ArrayList<Point> coordinates = new ArrayList<>();
		for(int y = startY; y <= endY; y++) {
			for(int x = startX; x <= endX; x++) { 
				Point coordinate = new Point(x, y);
				coordinates.add(coordinate);
			}
		}
		
		return coordinates;
	}

	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() != 1) return;
		
		if(this.operation == TerraformOperation.LEVEL && this.isLeveling) {
			PickResults results = Globals.viewport.pick(e.getPoint());
			TilePick pick = results.getSingleByClass(TilePick.class);
			
			if(pick != null) {
				this.levelingEndX = pick.corner.hmX;
				this.levelingEndY = pick.corner.hmY;
			}

			ArrayList<Point> coordinates = this.getLevelingPoints();
			for (Point point : coordinates) {
				Globals.world.terrain.setHeight(point.x, point.y, this.levelingHeight);				
			}
			
			this.levelingHighlight.clear();
			this.isLeveling = false;
		}
		if(this.isLeveling) {
			this.isLeveling = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



}