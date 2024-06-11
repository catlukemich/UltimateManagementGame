package iso;

import java.util.ArrayList;

public class PickResults {

	private ArrayList<Object> results = new ArrayList<Object>();
	
	void add(Object result) {
		this.results.add(result);
	}
	
	public <T> T getSingleByClass(Class<T> cls) {
		for (Object object : this.results) {
			if(cls.isAssignableFrom(object.getClass())) {
				return (T)object;
			}
		}
		return null;
	}
	
	public <T> T[] getByClass(Class<T> cls) {
		ArrayList<Object> retArray = new ArrayList<>();
		for (Object object : this.results) {
			if(cls.isAssignableFrom(object.getClass())) {
				retArray.add(object);
			}
		}
		return (T[]) retArray.toArray();
	}
}
