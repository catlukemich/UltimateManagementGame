package main;

public class ModeManager {

	Mode current_mode;
	
	public void setMode(Mode mode) {
		if(this.current_mode != null) this.current_mode.disable();
		this.current_mode = mode;
		if(this.current_mode != null) this.current_mode.enable();
	}
	
	public Mode getMode() {
		return this.current_mode;
	}
}
