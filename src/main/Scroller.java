package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import utils.Vector3;
import world.WorldVector3;

public class Scroller implements MouseListener, MouseMotionListener, MouseWheelListener{

	private boolean dragging = false;
	private int mouse_x, mouse_y;
	
	
	public void enable() {
		Globals.canvas.addMouseListener(this);
		Globals.canvas.addMouseMotionListener(this);
		Globals.canvas.addMouseWheelListener(this);
	}
	
	public void disable() {
		Globals.canvas.removeMouseListener(this);
		Globals.canvas.removeMouseMotionListener(this);
		Globals.canvas.removeMouseWheelListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 3) {
			this.mouse_x = e.getX();
			this.mouse_y = e.getY();
			this.dragging = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 3)
			this.dragging = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(!this.dragging) return;
		
		int dx = e.getX() - this.mouse_x;
		int dy = e.getY() - this.mouse_y;
		
		Vector3 center = Globals.viewport.getCenter();
		float x = (float) (center.x + dx / 16.0 + dy / 16.0);
		float y = (float) (center.y - dx / 16.0 + dy / 16.0);
		WorldVector3 newCenter = Globals.world.createVector3(x, y, 0);
		Globals.viewport.setCenter(newCenter);
		
		this.mouse_x = e.getX();
		this.mouse_y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		if(rotation == -1) {
			Globals.viewport.zoomIn();
		}
		if(rotation == 1) {
			Globals.viewport.zoomOut();
		}
		
	}

}
