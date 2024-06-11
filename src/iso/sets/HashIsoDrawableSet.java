package iso.sets;

import java.util.HashSet;
import java.util.Set;

import iso.IsoViewport;
import iso.interfaces.IsoDrawable;
import iso.interfaces.IsoDrawableSet;

public class HashIsoDrawableSet implements IsoDrawableSet{

	private HashSet<IsoDrawable> drawables = new HashSet<>();
	
	@Override
	public void addDrawable(IsoDrawable drawable) {
		this.drawables.add(drawable);
	}

	@Override
	public void removeDrawable(IsoDrawable drawable) {
		this.drawables.remove(drawable);
	}

	@Override
	public Set<IsoDrawable> getDrawables() {
		return this.drawables;
	}

	public void clear() {
		this.drawables.clear();
	}

	@Override
	public HashSet<IsoDrawable> cull(IsoViewport viewport) {
		return this.drawables;
	}
}
