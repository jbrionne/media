package fr.next.logigram.space;

import com.jme3.scene.Node;

public class ReturnAddObject {

	private Node t;
	private IMyObj o;
		
	public IMyObj getO() {
		return o;
	}
	public void setO(IMyObj o) {
		this.o = o;
	}
	public Node getT() {
		return t;
	}
	public void setT(Node t) {
		this.t = t;
	}
	
}
