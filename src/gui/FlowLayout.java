package gui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class FlowLayout extends Layout {
	
	private static class LineBreak extends Widget{
		public LineBreak() {
			super(new Rectangle(0, 0));
		}

		@Override
		public void draw(Graphics2D graphics) {}
	}
	
	final public static Widget lineBreak = new FlowLayout.LineBreak();
	

	public FlowLayout(int spacingX, int spacingY) {
		this.spacingX = spacingX;
		this.spacingY = spacingY;
	}
	
	final int spacingX;
	final int spacingY;
	
	
	@Override
	protected void layoutElements(Frame frame) {
		ArrayList<Widget> children = frame.getChildren();
		if(children.size() == 0) return;
		
		Rectangle bounds = frame.getBounds();
		
		int rowHeight = children.get(0).getHeight();
		for (Widget child : children) {
			int height = child.getHeight();
			if(height > rowHeight) {
				rowHeight = height;
			}
		}
		
		int x = bounds.x + this.spacingX;
		int y = bounds.y + this.spacingY;
		for(int i = 0; i < children.size(); i++) {
			Widget child = children.get(i);
			child.setPosition(x, y);
			int width = child.getWidth();
			x += child.getWidth();
			x += this.spacingX;
			
			if (x + width + this.spacingX > bounds.width  || child instanceof FlowLayout.LineBreak) {
				y += rowHeight;
				y += this.spacingY;
				x = this.spacingX;
			}
		}
		
		
	}

}
