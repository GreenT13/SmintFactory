package model;

public class State {
	Action action;
	boolean isButton1Used;
	
	State(Action action, boolean isButton1Used){
		this.action = action;
		this.isButton1Used = isButton1Used;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean isButton1Used() {
		return isButton1Used;
	}

	public void setButton1Used(boolean isButton1Used) {
		this.isButton1Used = isButton1Used;
	}
	
}
