package main;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import iso.IsoViewport;

public class MainViewport extends IsoViewport implements ComponentListener {

	public MainViewport() {
		super(Globals.canvas.getBounds());
		Globals.window.addComponentListener(this);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.setBounds(Globals.canvas.getBounds());
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

}
