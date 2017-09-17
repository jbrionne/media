package fr.next.logigram.espace;


import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

public class ManageEspace implements IManageEspace{

	private Espace esp;
	private final static float value = 5f;
	
	public static String TSPHERE = "TSPHERE";
	public static String TBOX = "TBOX";
	public static String TLIGNE = "TLIGNE";
	public static String TLIGNEBEZIER = "TLIGNEBEZIER";
	
	public Espace getEspace(){
		return esp;
	}
	
	
	public ManageEspace(Espace esp){
		this.esp = esp;	
	}
	
	
	@Override
	public Node imagineX(MyInstance i) {
		
		String id = i.getId();
		String type = i.getType();
		boolean withCollision = i.isWithCollision();
		
		if(i.getT() != null){			
			return i.getT() ;			
		}
		
		com.jme3.scene.Node t = null;
		if(type.equals(TSPHERE)){
			t = esp.addToRootBranchGroupAndTransform();
			ReturnAddObject r = esp.addSphereToTransform(id, t, 1f, withCollision);
			i.setT(r.getT());
			return r.getT();
		}else if(type.equals(TBOX)){
			t = esp.addToRootBranchGroupAndTransform();
			ReturnAddObject r = esp.addBoxToTransform(id, t, 1f, 1f, 1f, withCollision);
			i.setT(r.getT());
			return r.getT();
		} else if(type.equals(TLIGNE)){
			t = esp.addToRootBranchGroupAndTransform();			
			ReturnAddObject r = esp.addLineToTransform(id, t, -1.0f, 0, 0, +1.0f, 0, 0);
			i.setT(r.getT());
			return r.getT();
		}else if(type.equals(TLIGNEBEZIER)){
			t = esp.addToRootBranchGroupAndTransform();	
			ReturnAddObject r = esp.addLineBezierToTransform(id, t, -0.8f , -0.6f, -0.2f,0.2f, 0.2f,0.3f, 0.8f,-0.5f);
			i.setT(r.getT());
			return r.getT();
		}
		return null;
	}

	
	private void imagineX_A_Y(MyInstance box, MyInstance i, float x, float y, float z) {
		String id = i.getId();
		
		Node o = null;
		if(i.getT() != null){
			o = i.getT();
		}else{
			o = (Node) esp.addToRootBranchGroupAndTransform().getChild(id);
		}
		
		Node t = imagineX(box);	
		esp.setLocalTranslation(o, t);		
		esp.translationRelativeCoordWorld(t, x, y, z);		
	}
		
	private void imagineX_ALa_Y(MyInstance box, MyInstance i, float x, float y, float z) {			
		String id = i.getId();
		
		Node o = null;
		if(i.getT() != null){
			o = i.getT();
		}else{
			o = (Node) esp.addToRootBranchGroupAndTransform().getChild(id);
		}
		
		Node t = imagineX(box);			
		esp.setLocal(o, t);		
		esp.translationRelative(t, x, y, z);
	}	
	
	private boolean q_X_A_DeY(float x1, float x2){			
		if(x1 < x2){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public void imagineXAGaucheY(MyInstance box, MyInstance i) {	
		imagineX_A_Y(box, i, -value, 0, 0);		
	}
	
	@Override
	public void imagineXADroiteY(MyInstance box, MyInstance i) {
		imagineX_A_Y(box, i, value, 0, 0);
	}

	@Override
	public void imagineXEnHautY(MyInstance box, MyInstance i) {
		imagineX_A_Y(box, i, 0, value, 0);
	}

	@Override
	public void imagineXEnBasY(MyInstance box, MyInstance i) {
		imagineX_A_Y(box, i, 0, -value, 0);
	}

	@Override
	public void imagineXDevantY(MyInstance box, MyInstance i) {
		imagineX_A_Y(box, i, 0, 0, value);
	}

	@Override
	public void imagineXDerriereY(MyInstance box, MyInstance i) {
		imagineX_A_Y(box, i, 0, 0, -value);
	}
	
	/////////////////////////////////////////////////////////////////

	@Override
	public void imagineXALaDroiteY(MyInstance box, MyInstance i) {
		imagineX_ALa_Y(box, i, value, 0, 0);		
	}	
	
	@Override
	public void imagineXALaGaucheY(MyInstance box, MyInstance i) {
		imagineX_ALa_Y(box, i, -value, 0, 0);
	}

	@Override
	public void imagineXAuDessusY(MyInstance box, MyInstance i) {
		imagineX_ALa_Y(box, i, 0, value, 0);
	}

	@Override
	public void imagineXEnDessousY(MyInstance box, MyInstance i) {
		imagineX_ALa_Y(box, i, 0, -value, 0);
	}

	@Override
	public void imagineXAuDevantY(MyInstance box, MyInstance i) {
		imagineX_ALa_Y(box, i, 0, 0, -value);
	}

	@Override
	public void imagineXAuDerriereY(MyInstance box, MyInstance i) {
		imagineX_ALa_Y(box, i, 0, 0, value);
	}

	
	public boolean q_XAGaucheDeY(MyInstance b, MyInstance id){	
		Vector3f point1 = esp.getAbsoluteCoordinateByObjetId(id);		
		Vector3f point2 = esp.getAbsoluteCoordinateByObjetId(b);		
		return q_X_A_DeY(point1.getX(), point2.getX());
	}

	@Override
	public boolean q_XALaGaucheDeY(MyInstance b, MyInstance id) {		
		Vector3f point1 = esp.getRelativeToCamCoordinateByObjetId(id);		
		Vector3f point2 = esp.getRelativeToCamCoordinateByObjetId(b);		
		return q_X_A_DeY(point1.getX(), point2.getX());		
	}
	
	@Override
	public boolean q_XADroiteDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getAbsoluteCoordinateByObjetId(id);		
		Vector3f point2 = esp.getAbsoluteCoordinateByObjetId(b);		
		return q_X_A_DeY(point2.getX(), point1.getX());
	}

	@Override
	public boolean q_XALaDroiteDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getRelativeToCamCoordinateByObjetId(id);		
		Vector3f point2 = esp.getRelativeToCamCoordinateByObjetId(b);		
		return q_X_A_DeY(point2.getX(), point1.getX());	
	}

	@Override
	public boolean q_XABasDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getAbsoluteCoordinateByObjetId(id);		
		Vector3f point2 = esp.getAbsoluteCoordinateByObjetId(b);		
		return q_X_A_DeY(point1.getY(), point2.getY());
	}

	@Override
	public boolean q_XALaBasDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getRelativeToCamCoordinateByObjetId(id);		
		Vector3f point2 = esp.getRelativeToCamCoordinateByObjetId(b);		
		return q_X_A_DeY(point1.getY(), point2.getY());	
	}

	@Override
	public boolean q_XAHautDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getAbsoluteCoordinateByObjetId(id);		
		Vector3f point2 = esp.getAbsoluteCoordinateByObjetId(b);		
		return q_X_A_DeY(point2.getY(), point1.getY());
	}

	@Override
	public boolean q_XALaHautDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getRelativeToCamCoordinateByObjetId(id);		
		Vector3f point2 = esp.getRelativeToCamCoordinateByObjetId(b);		
		return q_X_A_DeY(point2.getY(), point1.getY());	
	}

	@Override
	public boolean q_XADevantDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getAbsoluteCoordinateByObjetId(id);		
		Vector3f point2 = esp.getAbsoluteCoordinateByObjetId(b);		
		return q_X_A_DeY(point1.getZ(), point2.getZ());
	}

	@Override
	public boolean q_XALaDevantDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getRelativeToCamCoordinateByObjetId(id);		
		Vector3f point2 = esp.getRelativeToCamCoordinateByObjetId(b);		
		return q_X_A_DeY(point1.getZ(), point2.getZ());	
	}

	@Override
	public boolean q_XADerriereDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getAbsoluteCoordinateByObjetId(id);		
		Vector3f point2 = esp.getAbsoluteCoordinateByObjetId(b);		
		return q_X_A_DeY(point2.getY(), point1.getY());
	}

	@Override
	public boolean q_XALaDerriereDeY(MyInstance b, MyInstance id) {
		Vector3f point1 = esp.getRelativeToCamCoordinateByObjetId(id);		
		Vector3f point2 = esp.getRelativeToCamCoordinateByObjetId(b);		
		return q_X_A_DeY(point2.getZ(), point1.getZ());	
	}

	@Override
	public void moveDroite(MyInstance i, Vector3f start, Vector3f end) {
		Node o = null;
		if(i.getT() != null){
			o = i.getT();
		}else{
			o = (Node) esp.addToRootBranchGroupAndTransform().getChild(i.getId());
		}
		
		 
	    Line pla = (Line) ((Geometry) o.getChild("GEO" + i.getId())).getMesh();
	    pla.updatePoints(start, end);
	}

	@Override
	public void moveCamera(float eyex, float eyey, float eyez, float centerx, float centery, float centerz,  float upx, float upy, float upz){	
		esp.moveCamera(eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);
	}

	@Override
	public void rotateAbsX(MyInstance b, float xz, float yz, float xy, float angleDeg) {
		esp.rotationAbsCoordWorld(b.getT(), xz, yz, xy, angleDeg);
	}


	@Override
	public void moveAbsX(MyInstance b, float x, float y, float z) {
		esp.translationAbsCoordWorld(b.getT(), x, y, z);
	}


	@Override
	public void echelleAbsX(MyInstance b, float x, float y, float z) {
		esp.echelleAbsCoordWorld(b.getT(), x, y, z);
	}


	@Override
	public void rotateRelativeX(MyInstance b, float x, float y, float z, float angleDeg) {
		esp.rotationRelativeDegreeCoordWorld(b.getT(), x, y, z, angleDeg);
	}


	@Override
	public void moveRelativeX(MyInstance b, float x, float y, float z) {
		esp.translationRelativeCoordWorld(b.getT(), x, y, z);	
	}


	@Override
	public void echelleRelativeX(MyInstance b, float x, float y, float z) {
		esp.echelleRelativeCoordWorld(b.getT(), x, y, z);
	}

}
