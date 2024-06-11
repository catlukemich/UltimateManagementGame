package main.initializers;

import main.BulldozeMode;
import main.Globals;
import main.TerraformMode;
import main.TrackConstructionMode;
import terrain.GameHeightmap;
import terrain.Heightmap;
import terrain.Terrain;
import ui.OperationsPane;
import world.World;


public class MainGameInitializer extends GameInitializerBase {
	
	@Override
	public void initialize() {
		OperationsPane operationsPane = new OperationsPane();
		Globals.gui.addWidget(operationsPane);
		
//		Heightmap hm = Heightmap.load("/hm2.txt", 20, 40);
//		hm.print();
//		Heightmap hm = new SimplexHeightmap(500, 500);
		Heightmap hm = new GameHeightmap(500, 500);
		Terrain terrain = new Terrain(hm);
		Globals.world = new World(terrain);
		Globals.scene.addDrawableSet(Globals.world.terrain);
		
		
		Globals.terraformMode = new TerraformMode();
		Globals.bulldozeMode = new BulldozeMode();
		Globals.trackMode = new TrackConstructionMode();
//		Globals.modeManager.setMode(Globals.terraformMode);
		Globals.modeManager.setMode(Globals.trackMode);
		
//		Memory.memoryStats();

	}
}
