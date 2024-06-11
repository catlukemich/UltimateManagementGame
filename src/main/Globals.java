package main;

import java.awt.Canvas;

import javax.swing.JFrame;

import gui.Gui;
import iso.IsoScene;
import iso.IsoViewport;
import terrain.Terrain;
import utils.Updater;
import world.World;

public class Globals {

	public static JFrame window;
	public static Canvas canvas;
	
	public static Gui gui;
	public static IsoScene scene;
	public static IsoViewport viewport;
	public static Scroller scroller;
	
	public static Updater updater;
	
	public static ModeManager modeManager;
	public static TerraformMode terraformMode;
	public static BulldozeMode bulldozeMode;
	public static TrackConstructionMode trackMode;
	
	public static World world;
	
}
