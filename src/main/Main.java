package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.Timer;

import gui.Gui;
import iso.IsoScene;
import iso.IsoViewport;
import main.initializers.MainGameInitializer;
import main.initializers.WorldRepeatingTester;
import main.initializers.GameInitializerBase;
import utils.Updater;


public class Main implements ActionListener, WindowListener, WindowStateListener{
	
	public static void main(String[] args) {
		Main main = new Main();
//		GameInitializerBase initializer = new MainGameInitializer();
		GameInitializerBase initializer = new WorldRepeatingTester();
		main.start(initializer);
	}
	
	public Main() {
		Globals.window = new JFrame("Nation");
		Globals.canvas = new Canvas();
	}
	
	private BufferStrategy strategy;
	
	/**
	 * Start the game.
	 * @param initializer An initializer to allow testing further down the road.
	 */
	public void start(GameInitializerBase initializer) {
		Globals.window.addWindowListener(this);
		Globals.window.addWindowStateListener(this);
		Globals.canvas.setPreferredSize(new Dimension(800, 600));
		Globals.window.add(Globals.canvas);
		Globals.window.pack();
		Globals.window.setVisible(true);
		
		Globals.canvas.createBufferStrategy(2);
		this.strategy = Globals.canvas.getBufferStrategy();
		
		Globals.gui = new Gui(Globals.canvas);
		Globals.scene = new IsoScene();
		Globals.viewport = new MainViewport();
		Globals.viewport.setScene(Globals.scene);
		Globals.scroller = new Scroller();
		
		Globals.scroller.enable();
		Globals.gui.enable();
		
		Globals.updater = new Updater();
		Globals.modeManager = new ModeManager();
		
		initializer.initialize();
		
		
		System.out.println(IsoViewport.V_STEP);
		
		Timer timer = new Timer(30, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Graphics2D graphics = (Graphics2D) this.strategy.getDrawGraphics();
		graphics.setColor(Color.BLACK);
		Rectangle bounds = Globals.window.getBounds();
		graphics.fillRect(0, 0, bounds.width, bounds.height);
		Globals.updater.update();
		Globals.viewport.draw(graphics);
		Globals.gui.draw(graphics);
		this.strategy.show();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}


	@Override
	public void windowStateChanged(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Window state changed");
	}
	
}
