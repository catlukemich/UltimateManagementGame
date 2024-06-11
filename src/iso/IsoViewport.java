package iso;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import iso.interfaces.IsoDrawable;
import iso.interfaces.IsoDrawableSet;
import terrain.Heightmap;
import utils.Vector2;
import utils.Vector3;
import world.World;
import world.WorldVector3;

public class IsoViewport{

	public static int HALF_TILE_WIDTH = 48;
	public static int HALF_TILE_HEIGHT = 24;
	public static int V_STEP = (int) ((float)HALF_TILE_HEIGHT * Math.sqrt(6));	 
	
	private static class DisplayUnit {
		
		DisplayUnit(Vector3 location, IsoDrawable drawable) {
			this.location = location;
			this.drawable = drawable;
		}
		
		final public Vector3 location;
		final public IsoDrawable drawable;
	}
	
	
	public IsoViewport(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	private Rectangle bounds;
	private IsoScene scene;
	private World world; // The world this viewport is associated with.
	private WorldVector3 center;
	private float zoom_level = 1.0f;
	private HashSet<DisplayUnit> culled = new HashSet<>();
	
	int drawDistance = 15;
	int fadeoutDistance = 4;
	
	/**
	 * Set bounds when the display (screen/viewport) size has changed.
	 * @param bounds
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	/**
	 * Get the bounds this viewport takes on the screen.
	 * @return
	 */
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	/**
	 * Set the scene this viewport will use for rendering.
	 * @param scene
	 */
	public void setScene(IsoScene scene) {
		this.scene = scene;
	}
	
	/**
	 * Get the scene this viewport is using for drawing.
	 * @return
	 */
	public IsoScene getScene() {
		return this.scene;
	}
	
	
	public void setWorld(World world) {
		this.world = world;
		this.center = world.createVector3(0, 0, 0);
	}
	
	
	/**
	 * Set the center point (location of the scene) of the viewport.
	 * @param center
	 */
	public void setCenter(WorldVector3 center) {
		this.center = center;
		System.out.println(String.format("%.4f %.4f %.4f ", center.x , center.y, center.z));
	}
	
	/**
	 * Get the current center point (location of the scene) of the viewport.
	 * @return
	 */
	public Vector3 getCenter() {
		return this.center;
	}
	
	/**
	 * Zoom the viewport in.
	 */
	public void zoomIn() {
		this.zoom_level = 2.0f;
	}
	
	/**
	 * Zoom the viewport out.
	 */
	public void zoomOut() {
		this.zoom_level = 1.0f;
	}
	
	/**
	 * Get current zoom level of the viewport.
	 * @return
	 */
	public float getZoom() {
		return this.zoom_level;
	}
	
	
	/**
	 * Get draw distance - the distance from the center point of the viewport that sprites are drawn within.
	 * @return
	 */
	public int getDrawDistance() {
		return this.drawDistance;
	}
	
	
	/**
	 * Project from the scene location to the viewport screen point.
	 * @param input
	 * @return
	 */
	public Vector2 project(Vector3 input) {
		float x = (input.x - this.center.x - input.y + this.center.y) * HALF_TILE_WIDTH * this.zoom_level;
		float y = (input.x - this.center.x + input.y - this.center.y) * HALF_TILE_HEIGHT * this.zoom_level
				- (input.z - this.center.z) * V_STEP * this.zoom_level;
		
		x += this.bounds.width / 2;
		y += this.bounds.height / 2;
		
		return new Vector2(x, y);
	}
	
	/**
	 * Draw the viewport.
	 * @param graphics
	 */
	public void draw(Graphics2D graphics) {
		// Cull the sprites:
		HashSet<IsoDrawableSet> sets = this.scene.getDrawableSets();
		HashSet<DisplayUnit> culled = new HashSet<>(50 * 50);
		HashMap<DisplayUnit, Float> distances = new HashMap<>();
		
		for (IsoDrawableSet set: sets) { 
			for (IsoDrawable drawable : set.cull(this)) {
				// There is need to somehow force the repeating of sprites on the x an y axis:
				Vector3 inputLocation = drawable.getLocation();
				float worldWidth = this.world.terrain.getWidth();
				float worldHeight = this.world.terrain.getHeight();
				
				// "Create" additional, virtual world planes on all the surrounding sides of the real world plane
				// and use if for culling:  
				for (int ix = -1; ix < 2; ix++) {
					for (int iy = -1; iy < 2; iy++) {
						float x = ix * worldWidth + inputLocation.x;
						float y = iy * worldHeight + inputLocation.y;
						float z = inputLocation.z;
						
						Vector3 displayLocation = new Vector3(x, y, z);
						
						float distance = this.center.distance(displayLocation, true);
						
						if(distance < drawDistance / this.zoom_level) {
							DisplayUnit displayUnit = new DisplayUnit(displayLocation, drawable);
							culled.add(displayUnit);
							distances.put(displayUnit, distance);
						}
					}
				}
				
			}
		}

		HashSet<DisplayUnit> entering = new HashSet<>(culled);
		entering.removeAll(this.culled);
		for (DisplayUnit unit : entering) {
			unit.drawable.onViewportEnter(this);
		}
		
		HashSet<DisplayUnit> leaving = new HashSet<>(this.culled);
		leaving.removeAll(culled);
		for (DisplayUnit unit : leaving) {
			unit.drawable.onViewportLeave(this);
		}
		
		this.culled = culled;
		
		ArrayList<DisplayUnit> sorted = new ArrayList<DisplayUnit>(culled);
				
		sorted.sort(new Comparator<DisplayUnit>() {
			@Override
			public int compare(DisplayUnit o1, DisplayUnit o2) {
				IsoDrawable d1 = o1.drawable;
				IsoDrawable d2 = o2.drawable;
				// TODO: Handle sorting properly.
				int layer1 = d1.getLayer();
				int layer2 = d2.getLayer();
				
				if (layer1 != layer2) return layer1 - layer2;
				
				float nearness1 = o1.location.x + o1.location.y + o1.location.z;
				float nearness2 = o2.location.x + o2.location.y + o2.location.z;
				if (nearness1 < nearness2) return 1;
				else if (nearness2 < nearness1) return -1;
				return 0;
			}
		});
		
		
		for(DisplayUnit unit : sorted) {
			float distance = distances.get(unit);

			DrawHints hints = new DrawHints(1.0f, unit.location);
			if(distance > (drawDistance - fadeoutDistance) / this.zoom_level && distance < drawDistance / this.zoom_level) {
				float maxAlpha = 1 - (distance - (drawDistance - fadeoutDistance) / this.zoom_level) / ((float)fadeoutDistance / this.zoom_level);
				hints = new DrawHints(maxAlpha, unit.location);
			}
			unit.drawable.draw(this, graphics, hints);
		}
	}

	public PickResults pick(Point mouse) {
		PickResults results = new PickResults();
		for (DisplayUnit unit: this.culled) {
			Object result = unit.drawable.handlePick(this, mouse);
			if(result != null) {
				results.add(result);
			}
		}
		return results;
	}


}
