package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utils.Assets;

public class Button extends Widget {

	public Button(String image_path) {
		this(Assets.loadImage(image_path));
	}
	
	public Button(BufferedImage image) {
		super(new Rectangle(
				0, 0, 
				image.getWidth(), image.getHeight()));
		this.image = image;
	}

	BufferedImage image;
	Color background_color = null;
	

	@Override
	public void hoverStart() {
		this.background_color = Color.BLACK;
	}
	
	@Override
	public void hoverEnd() {
		this.background_color = null;
	}
	
	@Override
	public void pressed() {
		this.background_color = Color.RED;
	}
	
	@Override
	public void released() {
		this.background_color = null;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		if(this.background_color != null) {
			graphics.setColor(this.background_color);
			graphics.fillRect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
		}
		graphics.drawImage(image,this.bounds.x, this.bounds.y, null);
	}


}
