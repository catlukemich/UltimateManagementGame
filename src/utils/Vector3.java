package utils;

public class Vector3 {

	public Vector3() {
		this(0, 0, 0);
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	final public float x, y, z; 
	
	
	@Override
	public String toString() {
		return String.format("Vector3(%.2f, %.2f, %2f", this.x, this.y, this.z);
	}
}
