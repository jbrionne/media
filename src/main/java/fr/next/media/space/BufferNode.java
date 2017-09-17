package fr.next.media.space;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

public class BufferNode {

	private Node t;
	private MyNode node;
	private CollisionShape collisionShape;
	private Geometry geometry;
	private Mesh mesh;
	
	public Node getT() {
		return t;
	}

	public void setT(Node t) {
		this.t = t;
	}

	public MyNode getNode() {
		return node;
	}

	public void setNode(MyNode node) {
		this.node = node;
	}

	public CollisionShape getCollisionShape() {
		return collisionShape;
	}

	public void setCollisionShape(CollisionShape collisionShape) {
		this.collisionShape = collisionShape;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
}
