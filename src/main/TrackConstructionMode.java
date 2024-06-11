package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import infrastructure.TrackType;
import iso.IsoSprite;
import iso.PickResults;
import iso.interfaces.IsoDrawable;
import iso.sets.HashIsoDrawableSet;
import terrain.Tile;
import terrain.TilePick;

public class TrackConstructionMode extends Mode implements MouseListener, MouseMotionListener {

	public TrackConstructionMode() {
		
	}
	
	Tile startTile;
	Tile endTile;
	HashIsoDrawableSet plan = new HashIsoDrawableSet();
	
	@Override
	public void enable() {
		Globals.canvas.addMouseListener(this);
		Globals.canvas.addMouseMotionListener(this);
		Globals.scene.addDrawableSet(this.plan);
	}
	
	@Override
	public void disable() {
		Globals.canvas.removeMouseListener(this);
		Globals.canvas.removeMouseMotionListener(this);
		Globals.scene.removeDrawableSet(this.plan);
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != 1) return;
		
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		
		if(pick != null) {
			this.startTile = pick.tile;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		PickResults results = Globals.viewport.pick(e.getPoint());
		TilePick pick = results.getSingleByClass(TilePick.class);
		
		if(pick != null) {
			this.endTile = pick.tile;
		}
		
		this.displayTrackPlan();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.startTile = null;
		this.endTile = null;
	}
	
	private void displayTrackPlan() {
		if(this.startTile == null || this.endTile == null) return;
		if(this.startTile == this.endTile) return;
		
		int deltaX = this.endTile.x - this.startTile.x;
		int deltaY = this.endTile.y - this.startTile.y;
		if(deltaX == 0 && deltaY == 0) return;
		
		int absDeltaX = Math.abs(deltaX);
		int absDeltaY = Math.abs(deltaY);
		
		int directionX;
		int directionY;
		
		if(deltaX == 0) {
			directionX = 0;
		}
		else {
			directionX = deltaX / absDeltaX;
		}
		
		if(deltaY == 0) {
			directionY = 0;
		}
		else {
			directionY = deltaY/ absDeltaY;
		}
				
		this.plan.clear();
		
		int excessX = absDeltaX - absDeltaY;
		int excessY = absDeltaY - absDeltaX;
		int numDiagonal = Math.max(excessX, excessY);
		
		int diagonalStepX;
		int diagonalStepY;
		
		if (absDeltaX == 0) {
			diagonalStepX = 0;
		}
		else {
			diagonalStepX = (this.endTile.x - this.startTile.x) / absDeltaX;
		}
		
		if (absDeltaY == 0) {
			diagonalStepY = 0;
		}
		else {
			diagonalStepY = (this.endTile.y - this.startTile.y) / absDeltaY;
		}
		
		
		TrackType startType = null;
		if(absDeltaX > absDeltaY) {
			startType = TrackType.getFromVector(directionX, 0);
		}
		else{
			System.out.println(directionY);
			startType = TrackType.getFromVector(0, directionY);
		}
		if(startType == null) {
			System.out.println("---------------------");
			System.out.println(directionX);
			System.out.println(directionY);
			return;
		}
		
		IsoSprite startPiece = new IsoSprite("/infrastructure/roads/road_" + startType.getFilenamePart() + ".png");
		startPiece.setLocation(this.startTile.getLocation());
		this.plan.addDrawable(startPiece);
	}
	

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}

	
	
}
