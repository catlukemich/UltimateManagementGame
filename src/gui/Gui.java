package gui;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Gui implements MouseMotionListener, MouseListener{

	public Gui(Canvas canvas) {
		this.canvas = canvas;
	}
	
	Canvas canvas;
	Widget hover;
	Widget active;
	
	
	public ArrayList<Widget> widgets = new ArrayList<>();

	
	public void enable() {
		this.canvas.addMouseMotionListener(this);
		this.canvas.addMouseListener(this);
	}
	
	public void addWidget(Widget widget) {
		this.widgets.add(widget);
	}
	
	public void removeWidget(Widget widget) {
		this.widgets.remove(widget);
	}
	
	public void draw(Graphics2D graphics) {
		for (Widget widget : widgets) {
			widget.draw(graphics);
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		Widget oldHover = this.hover;
		Point mouse = event.getPoint();
		Widget newHover = this.findHoverWidget(mouse);
		
		if(oldHover != newHover) {
			if(oldHover != null) oldHover.hoverEnd();
			if(newHover != null) newHover.hoverStart();
		}
		this.hover = newHover;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent event) {
		Point mouse = event.getPoint();
		Widget widget = this.findHoverWidget(mouse);
		if(widget != null) {
			widget.clicked();
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		Point mouse = event.getPoint();
		Widget widget = this.findHoverWidget(mouse);
		this.active = widget;
		if(widget != null) {
			widget.pressed();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.active != null) {
			this.active.released();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	private Widget findHoverWidget(Point mouse) {
		for(int i = this.widgets.size() - 1; i >= 0; i--) {
			Widget widget = this.widgets.get(i);
			Widget hover = widget.findHoverWidget(mouse);
			if(hover != null) return hover;
		}
		return null;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {}


}
