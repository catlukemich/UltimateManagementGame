package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Frame extends Widget {

	public Frame(int width, int height) {
		super(new Rectangle(0, 0, width, height));
	}

	Color color = Color.WHITE;
	private ArrayList<Widget> children = new ArrayList<Widget>();
	private Layout layout = new FlowLayout(15, 5);
	
	
	public ArrayList<Widget> getChildren() {
		return this.children;
	}
	
	public void addChild(Widget child) {
		this.children.add(child);
		this.layout.layoutElements(this);
	}
	
	public void removeChild(Widget child) {
		this.children.remove(child);
		this.layout.layoutElements(this);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setPosition(int x, int y) {
		int dx = x - this.bounds.x;
		int dy = y - this.bounds.y;
		
		for (Widget child : this.children) {
			Point position = child.getPosition();
			var newPosition = new Point(position);
			newPosition.x += dx;
			newPosition.y += dy;
			child.setPosition(newPosition.x, newPosition.y);
		}
		this.bounds.x = x;
		this.bounds.y = y;
	}
	
	public void draw(Graphics2D graphics) {
		this.drawSelf(graphics);
		
		for (Widget widget : this.children) {
			widget.draw(graphics);
		}
	};
	
	public void drawSelf(Graphics2D graphics) {
		graphics.setColor(this.color);
		graphics.fillRect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
	}
	
	@Override
	Widget findHoverWidget(Point mouse) {
		Widget hover = null;
		for (Widget child : this.children) {
			hover = child.findHoverWidget(mouse);
			if(hover != null) return hover;
		}
		if(this.containsPoint(mouse)) return this;
		return null;
	}

}
