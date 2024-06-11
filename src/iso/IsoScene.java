package iso;

import java.util.HashSet;
import iso.interfaces.IsoDrawable;
import iso.interfaces.IsoDrawableSet;
import iso.sets.HashIsoDrawableSet;

public class IsoScene {

	public IsoScene() {
		this.drawableSets.add(this.defaultSprites);
	}
	
	public IsoDrawableSet defaultSprites = new HashIsoDrawableSet();
	public HashSet<IsoDrawableSet> drawableSets = new HashSet<>();
	
	public void addDrawable(IsoDrawable drawable) {
		this.defaultSprites.addDrawable(drawable);
	}
	
	public void removeDrawable(IsoDrawable drawable) {
		this.defaultSprites.removeDrawable(drawable);
	}
	
	public void addDrawableSet(IsoDrawableSet set) {
		this.drawableSets.add(set);
	}
	
	public void removeDrawableSet(IsoDrawableSet set) {
		this.drawableSets.remove(set); 
	}
	
	public HashSet<IsoDrawableSet> getDrawableSets() {
		return this.drawableSets;
	}
}
