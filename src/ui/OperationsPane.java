package ui;

import gui.Button;
import gui.FlowLayout;
import gui.Frame;
import gui.Widget;
import gui.WidgetListener;
import main.Globals;
import main.TerraformOperation;

public class OperationsPane extends Frame  {

	public OperationsPane() {
		super(160, 400);
		this.createButtons();
		this.setPosition(20, 20);
	}
	
	private void createButtons() {
		Button raiseTerrain = new Button("/gui/operations/raise.png");
		raiseTerrain.setName("Raise terrain");
		raiseTerrain.addListener(this.terraformListener);
		this.addChild(raiseTerrain);
		
		Button lowerTerrain = new Button("/gui/operations/lower.png");
		lowerTerrain.setName("Lower terrain");
		lowerTerrain.addListener(this.terraformListener);
		this.addChild(lowerTerrain);
		
		Button levelTerrain = new Button("/gui/operations/level.png");
		levelTerrain.setName("Level terrain");
		levelTerrain.addListener(this.terraformListener);
		this.addChild(levelTerrain);
		
		Button bulldozeTerrain = new Button("/gui/operations/bulldoze.png");
		bulldozeTerrain.setName("Bulldoze terrain");
		bulldozeTerrain.addListener(this.bulldozeListener);
		this.addChild(bulldozeTerrain);
		
		this.addChild(FlowLayout.lineBreak);
		
		Button roadsButton = new Button("/gui/infrastructure/road.png");
		this.addChild(roadsButton);
//		roadsButton.addListener(/*bulldozeListener*/)
	}

	private WidgetListener terraformListener = new WidgetListener() {
		@Override
		public void onClick(Widget widget) {
			Globals.modeManager.setMode(Globals.terraformMode);
			String name = widget.getName();
			if(name.equals("Raise terrain")) {
				Globals.terraformMode.setOperation(TerraformOperation.RAISE);
			}
			else if(name.equals("Lower terrain")) {
				Globals.terraformMode.setOperation(TerraformOperation.LOWER);
			}
			else if(name.equals("Level terrain")) {
				Globals.terraformMode.setOperation(TerraformOperation.LEVEL);
			}
		}
	};

	private WidgetListener bulldozeListener = new WidgetListener() {
		
		@Override
		public void onClick(Widget widget) {
			Globals.modeManager.setMode(Globals.bulldozeMode);
		}
	};
}
