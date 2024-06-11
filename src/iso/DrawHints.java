package iso;

import utils.Vector3;

public class DrawHints {
	
	public DrawHints(float maxAlpha, Vector3 location) {
		this.maxAlpha = maxAlpha;
		this.location = location;
	}
	
	final public float maxAlpha;
	final public Vector3 location;
}
 