package fr.next.logigram.espace;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public interface IManageEspace {

	Espace getEspace();
	
	Node imagineX(MyInstance i);	
	
	void imagineXAGaucheY(MyInstance box, MyInstance i);
	void imagineXADroiteY(MyInstance box, MyInstance i);
	void imagineXEnHautY(MyInstance box, MyInstance i);
	void imagineXEnBasY(MyInstance box, MyInstance i);
	void imagineXDevantY(MyInstance box, MyInstance i);
	void imagineXDerriereY(MyInstance box, MyInstance i);
	
	
	void imagineXALaDroiteY(MyInstance box, MyInstance i);
	void imagineXALaGaucheY(MyInstance box, MyInstance i);
	void imagineXAuDessusY(MyInstance box, MyInstance i);
	void imagineXEnDessousY(MyInstance box, MyInstance i);
	void imagineXAuDevantY(MyInstance box, MyInstance i);
	void imagineXAuDerriereY(MyInstance box, MyInstance i);
	
	
	boolean q_XAGaucheDeY(MyInstance b, MyInstance id);
	boolean q_XALaGaucheDeY(MyInstance b, MyInstance id);
	boolean q_XADroiteDeY(MyInstance b, MyInstance id);
	boolean q_XALaDroiteDeY(MyInstance b, MyInstance id);
	boolean q_XABasDeY(MyInstance b, MyInstance id);
	boolean q_XALaBasDeY(MyInstance b, MyInstance id);
	boolean q_XAHautDeY(MyInstance b, MyInstance id);
	boolean q_XALaHautDeY(MyInstance b, MyInstance id);
	boolean q_XADevantDeY(MyInstance b, MyInstance id);
	boolean q_XALaDevantDeY(MyInstance b, MyInstance id);
	boolean q_XADerriereDeY(MyInstance b, MyInstance id);
	boolean q_XALaDerriereDeY(MyInstance b, MyInstance id);
	
	void moveDroite(MyInstance i, Vector3f start, Vector3f end);

	void moveCamera(float eyex, float eyey, float eyez, float centerx, float centery, float centerz,  float upx, float upy, float upz);
	
	void rotateAbsX(MyInstance b, float vx1, float vy1, float vz1, float angleDeg);
	void moveAbsX(MyInstance b, float vx1, float vy1, float vz1);
	void echelleAbsX(MyInstance b, float vx1, float vy1, float vz1);
	void rotateRelativeX(MyInstance b, float vx1, float vy1, float vz1, float angleDeg);
	void moveRelativeX(MyInstance b, float vx1, float vy1, float vz1);
	void echelleRelativeX(MyInstance b, float vx1, float vy1, float vz1);


}
