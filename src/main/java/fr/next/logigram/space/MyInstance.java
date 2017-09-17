package fr.next.logigram.space;

import com.jme3.scene.Node;

public class MyInstance {

	private String id;
	private String type;
	private Node t;
	private boolean withCollision = false;
	
	public MyInstance(String id, String type, boolean withCollision) {
		super();
		this.id = id;
		this.type = type;
		this.withCollision = withCollision;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean isWithCollision() {
		return withCollision;
	}

	public void setWithCollision(boolean withCollision) {
		this.withCollision = withCollision;
	}

	public Node getT() {
		return t;
	}

	public void setT(Node t) {
		this.t = t;
	}
	
}
