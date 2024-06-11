package gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Widget {
	
	public Widget(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	private String name;
	protected Rectangle bounds;

	private ArrayList<WidgetListener> listeners = new ArrayList<>();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public void setPosition(int x, int y) {
		this.bounds.x = x;
		this.bounds.y = y;
	}
	
	public Point getPosition() {
		return new Point(this.bounds.x, this.bounds.y);
	}

	public int getWidth() {
		return this.bounds.width;
	}
	
	public int getHeight() {
		return this.bounds.height;
	}
	
	public void addListener(WidgetListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(WidgetListener listener) {
		this.listeners.remove(listener);
	}
	
	public boolean containsPoint(Point point) {
		return this.bounds.contains(point);
	}
	
	Widget findHoverWidget(Point mouse) {
		if(this.containsPoint(mouse)) return this;
		return null;
	}
	
	public void hoverStart(){};
	public void hoverEnd(){};
	
	public void pressed(){};
	
	public void released(){};
	final public void clicked(){
		for (WidgetListener listener : this.listeners) {
			listener.onClick(this);
		}
	};
	
	public abstract void draw(Graphics2D graphics);
	
}
