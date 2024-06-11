package iso.interfaces;

import java.util.HashSet;
import iso.IsoViewport;

public interface IsoDrawableSet {

	public Iterable<IsoDrawable> getDrawables();
	public default void addDrawable(IsoDrawable drawable){};
	public default void removeDrawable(IsoDrawable drawable){};

	public HashSet<IsoDrawable> cull(IsoViewport viewport);
}
