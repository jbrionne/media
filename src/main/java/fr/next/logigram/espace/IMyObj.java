package fr.next.logigram.espace;

import com.jme3.math.Matrix4f;
import com.jme3.math.Transform;


public interface IMyObj {

	String getIdentifiant();
	
	Matrix4f getLocalToWorldMatrix(Matrix4f matrix);
	
	Transform getLocalTransform();
	
}
