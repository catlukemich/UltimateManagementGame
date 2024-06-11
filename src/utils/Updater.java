package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import terrain.Tile;

public class Updater {
	
	HashSet<Updateable> updateables = new HashSet<>();
	HashSet<Animated> animateds = new HashSet<>();
	HashMap<Callable, Float> callables = new HashMap<>();
	HashMap<Callable, Float> callablesTimes = new HashMap<>();
	HashMap<Callable, Float> intervals = new HashMap<>();
	HashMap<Callable, Float> intervalsTimes = new HashMap<>();
	
	
	long time = System.nanoTime() / 1_000_000;
	
	public void addUpdateable(Updateable updateable) {
		this.updateables.add(updateable);
	}
	
	public void addAnimated(Animated animated) {
		this.animateds.add(animated);
	}
	
	public void removeAnimated(Animated animated) {
		this.animateds.remove(animated);
	}
	
	public void removeUpdateable(Updateable updateable) {
		this.updateables.remove(updateable);
	}
	
	public void callLater(Callable callable, float time) {
		this.callables.put(callable, time);
		this.callablesTimes.put(callable, 0f);
	}
	
	public void cancelCall(Callable callable) {
		this.callables.remove(callable);
		this.callablesTimes.remove(callable);
	}
	
	public void addInterval(Callable interval, float time) {
		this.intervals.put(interval, time);
		this.intervalsTimes.put(interval, 0f);
	}
	
	public void removeInterval(Callable interval) {
		this.intervals.remove(interval);
		this.intervalsTimes.remove(interval);
	}
	
	public void update() {
		
		long time = System.nanoTime() / 1_000_000;
		float delta = (time - this.time) / 1000f;
		this.time = time;
		
		for (Updateable updateable : this.updateables) {
			updateable.update(delta);
		}
		
		for (Animated animated : this.animateds) {
			animated.animate(delta);
		}
		
		var forRemoval = new ArrayList<Callable>(); 
		
		for (Callable callable : this.callables.keySet()) {
			float timeout = this.callables.get(callable);
			float elapsed = this.callablesTimes.get(callable);
			elapsed += delta;
			
			if(elapsed > timeout) {
				callable.call();
				forRemoval.add(callable);
			}
			this.callablesTimes.put(callable, elapsed);
		}
		
		for (Callable toRemove : forRemoval) {
			this.callables.remove(toRemove);
			this.callablesTimes.remove(toRemove);
			
		}
		
		for (Callable interval : this.intervals.keySet()) {
			float timeout = this.intervals.get(interval);
			float elapsed = this.intervalsTimes.get(interval);
			elapsed += delta;
			this.intervalsTimes.put(interval, elapsed);
			
			if(elapsed > timeout) {
				interval.call();
				this.intervalsTimes.put(interval, elapsed - timeout);
			}
		}
		
	}


}
