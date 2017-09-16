package fr.next.logigram.espace;

import com.jme3.math.Matrix4f;
import com.jme3.scene.Node;


public class MyNode extends Node implements IMyObj {

	private String identifiant;

	public MyNode(String id) {
		this.identifiant = id;		
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	@Override
	public Matrix4f getLocalToWorldMatrix(Matrix4f matrix) {
		return this.getLocalToWorldMatrix(matrix);
	}
	
}
