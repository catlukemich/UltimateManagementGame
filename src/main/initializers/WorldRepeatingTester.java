package main.initializers;

import iso.IsoSprite;
import main.BulldozeMode;
import main.Globals;
import main.TerraformMode;
import main.TrackConstructionMode;
import terrain.Heightmap;
import terrain.Terrain;
import ui.OperationsPane;
import utils.Assets;
import utils.Updateable;
import utils.Updater;
import utils.Vector3;
import world.World;
import world.WorldVector3;

public class WorldRepeatingTester extends GameInitializerBase implements Updateable {
	
	
	private IsoSprite tree;

	@Override
	public void initialize() {
		OperationsPane operationsPane = new OperationsPane();
//		Globals.gui.addWidget(operationsPane);
		
		Heightmap heightmap = Heightmap.load("/hm2.txt", 20, 40);
		Terrain terrain = new Terrain(heightmap);
		
		Globals.world = new World(terrain);
		Globals.scene.addDrawableSet(Globals.world.terrain);
		Globals.viewport.setWorld(Globals.world);
		
		Globals.updater.addUpdateable(this);
		
		this.tree = new IsoSprite("/image.png");
		tree.setLayer(4);
		Globals.scene.addDrawable(this.tree);
				
		Globals.terraformMode = new TerraformMode();
		Globals.bulldozeMode = new BulldozeMode();
		Globals.trackMode = new TrackConstructionMode();
		Globals.modeManager.setMode(Globals.terraformMode);
//		Globals.modeManager.setMode(Globals.trackMode);
	}

	@Override
	public void update(float delta) {
		Vector3 location = tree.getLocation();
		WorldVector3 newLocation = Globals.world.createVector3(location.x + 0.1f, 10, 0.6f);
		tree.setLocation(newLocation);
	}

}
