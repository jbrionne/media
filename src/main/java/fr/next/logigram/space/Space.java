package fr.next.logigram.space;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.vecmath.Point4f;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SimplexCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.bullet.joints.motors.RotationalLimitMotor;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix4f;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Curve;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;

public class Space extends SimpleApplication implements PhysicsCollisionListener {

	private Thread mainThread;
	private AtomicBoolean started;
	private BulletAppState bulletAppState = new BulletAppState();

	private static Material materialRed;
	private static Material materialShadow;

	public Space(AtomicBoolean started) {
		this.started = started;
	}

	public Node addToRootBranchGroupAndTransform() {
		return rootNode;
	}

	private BufferNode subAddBoxToTransform(final String id, final Node t, final float largeur, final float hauteur,
			final float profondeur) {
		BufferNode bnode = new BufferNode();
		bnode.setNode(new MyNode(id));
		bnode.getNode().setLocalTranslation(Vector3f.ZERO);
		bnode.setMesh(new Box(largeur, hauteur, profondeur));
		bnode.setGeometry(new Geometry("GEO" + id, bnode.getMesh()));
		bnode.setT(t);
		bnode.setCollisionShape(new BoxCollisionShape(new Vector3f(largeur, hauteur, profondeur)));
		bnode.getGeometry().setMaterial(materialShadow);
		return bnode;
	}

	public ReturnAddObject addBoxToTransform(final String id, final Node t, final float largeur, final float hauteur,
			final float profondeur, boolean withCollision) {
		final BufferNode bnode = subAddBoxToTransform(id, t, largeur, hauteur, profondeur);
		if (Thread.currentThread().equals(mainThread)) {
			return appAccesAdd(bnode);
		} else {
			Future<ReturnAddObject> retour = this.enqueue(new Callable<ReturnAddObject>() {

				public ReturnAddObject call() throws Exception {
					return appAccesAdd(bnode);
				}
			});
			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	private BufferNode subAddSphereToTransform(final String id, final Node t, final float f) {
		BufferNode bnode = new BufferNode();
		bnode.setNode(new MyNode(id));
		bnode.getNode().setLocalTranslation(Vector3f.ZERO);
		bnode.setMesh(new Sphere(16, 16, f));
		bnode.setGeometry(new Geometry("GEO" + id, bnode.getMesh()));
		bnode.setT(t);
		bnode.setCollisionShape(new CapsuleCollisionShape(f, 0));
		bnode.getGeometry().setMaterial(materialShadow);
		return bnode;
	}

	public ReturnAddObject addSphereToTransform(final String id, final Node t, final float f, boolean withCollision) {
		System.out.println("addSphereToTransform");
		if (Thread.currentThread().equals(mainThread)) {
			BufferNode bnode = subAddSphereToTransform(id, t, f);
			return appAccesAdd(bnode);
		} else {
			final BufferNode bnode = subAddSphereToTransform(id, t, f);
			Future<ReturnAddObject> retour = this.enqueue(new Callable<ReturnAddObject>() {
				public ReturnAddObject call() throws Exception {
					System.out.println("addSphereToTransform call");
					return appAccesAdd(bnode);
				}
			});
			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public Vector3f getAbsoluteCoordinateByObjetId(final MyInstance id) {
		if (Thread.currentThread().equals(mainThread)) {
			return id.getT().getLocalTranslation();
		} else {
			Future<Vector3f> retour = this.enqueue(new Callable<Vector3f>() {
				public Vector3f call() throws Exception {
					return id.getT().getLocalTranslation();
				}
			});
			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	private ReturnAddObject appAccesAdd(BufferNode bnode) {
		bnode.getNode().attachChild(bnode.getGeometry());
		addBufferNode(bnode);
		ReturnAddObject r = new ReturnAddObject();
		r.setT(bnode.getNode());
		return r;
	}

	private void addBufferNode(BufferNode bnode) {
		CollisionShape shape = bnode.getCollisionShape();
		RigidBodyControl rigidBodyControl = new RigidBodyControl(shape, 0);
		rigidBodyControl.setKinematic(true);
		bnode.getNode().addControl(rigidBodyControl);
		ReturnAddObject r = new ReturnAddObject();
		r.setO(bnode.getNode());
		bnode.getT().attachChild(bnode.getNode());
		bulletAppState.getPhysicsSpace().add(bnode.getNode());
	}

	public Vector3f getRelativeToCamCoordinateByObjetId(MyInstance id) {

		if (Thread.currentThread().equals(mainThread)) {
			return cam.getLocation();
		} else {
			Future<Vector3f> retour = this.enqueue(new Callable<Vector3f>() {

				public Vector3f call() throws Exception {
					return cam.getLocation();
				}

			});
			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	public Vector3f getAbsoluteCoordinateByObjetId(Vector3f localTranslation) {
		if (Thread.currentThread().equals(mainThread)) {
			return localTranslation;
		} else {
			Future<Vector3f> retour = this.enqueue(new Callable<Vector3f>() {
				public Vector3f call() throws Exception {
					return localTranslation;
				}
			});
			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public Vector3f getRelativeToCamCoordinate() {
		if (Thread.currentThread().equals(mainThread)) {
			return cam.getLocation();
		} else {
			Future<Vector3f> retour = this.enqueue(new Callable<Vector3f>() {

				public Vector3f call() throws Exception {
					return cam.getLocation();
				}

			});
			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	public void translationAbsCoordWorld(final Node t, final float x, final float y, final float z) {
		if (Thread.currentThread().equals(mainThread)) {
			t.setLocalTranslation(x, y, z);
		} else {
			this.enqueue(new Callable<Void>() {

				public Void call() throws Exception {
					t.setLocalTranslation(x, y, z);
					return null;
				}

			});
		}
	}

	private Matrix4f subTranslationRelative(final Node t, final float x, final float y, final float z) {
		Transform tr = t.getLocalTransform();
		Transform trV = new Transform(new Vector3f(x, y, z));
		Matrix4f m = toMatrix(tr);
		return m.mult(toMatrix(trV));
	}

	public void translationRelative(final Node t, final float x, final float y, final float z) {
		final Matrix4f res = subTranslationRelative(t, x, y, z);
		if (Thread.currentThread().equals(mainThread)) {
			t.setLocalTransform(toTransform(res));
		} else {
			this.enqueue(new Callable<Void>() {
				public Void call() throws Exception {
					t.setLocalTransform(toTransform(res));
					return null;
				}
			});
		}
	}

	public void translationRelativeCoordWorld(final Node t, final float x, final float y, final float z) {
		if (Thread.currentThread().equals(mainThread)) {
			t.move(x, y, z);
		} else {
			this.enqueue(new Callable<Void>() {

				public Void call() throws Exception {
					t.move(x, y, z);
					return null;
				}

			});
		}
	}

	private Quaternion subRotationAbsCoordWorld(final Node t, final float xz, final float yz, final float xy,
			final float angleDeg) {
		Quaternion q = new Quaternion();
		q.fromAngleAxis(angleDeg * FastMath.DEG_TO_RAD, new Vector3f(xz, yz, xy));
		return q;
	}

	public void rotationAbsCoordWorld(final Node t, final float xz, final float yz, final float xy,
			final float angleDeg) {
		final Quaternion q = subRotationAbsCoordWorld(t, xz, yz, xy, angleDeg);
		if (Thread.currentThread().equals(mainThread)) {
			t.setLocalRotation(q);
		} else {
			this.enqueue(new Callable<Void>() {
				public Void call() throws Exception {
					t.setLocalRotation(q);
					return null;
				}
			});
		}
	}

	private Matrix4f subRotationRelativeDegree(final Node t, final float x, final float y, final float z,
			final float angleDeg) {
		Quaternion q = new Quaternion();
		q.fromAngleAxis(angleDeg * FastMath.DEG_TO_RAD, new Vector3f(x, y, z));
		Transform trV = new Transform(q);
		Transform tr = t.getLocalTransform();
		Matrix4f m = toMatrix(tr);
		Matrix4f res = m.mult(toMatrix(trV));
		t.setLocalTransform(toTransform(res));
		return res;
	}

	public void rotationRelativeDegree(final Node t, final float x, final float y, final float z,
			final float angleDeg) {
		final Matrix4f res = subRotationRelativeDegree(t, x, y, z, angleDeg);
		if (Thread.currentThread().equals(mainThread)) {
			t.setLocalTransform(toTransform(res));
		} else {
			this.enqueue(new Callable<Void>() {
				public Void call() throws Exception {
					t.setLocalTransform(toTransform(res));
					return null;
				}
			});
		}
	}

	private Quaternion subRotationRelativeDegreeCoordWorld(final Node t, final float x, final float y, final float z,
			final float angleDeg) {
		Quaternion q = new Quaternion();
		q.fromAngleAxis(angleDeg * FastMath.DEG_TO_RAD, new Vector3f(x, y, z));
		return q;
	}

	public void rotationRelativeDegreeCoordWorld(final Node t, final float x, final float y, final float z,
			final float angleDeg) {
		final Quaternion q = subRotationRelativeDegreeCoordWorld(t, x, y, z, angleDeg);
		if (Thread.currentThread().equals(mainThread)) {
			t.rotate(q);
		} else {
			this.enqueue(new Callable<Void>() {
				public Void call() throws Exception {
					t.rotate(q);
					return null;
				}
			});
		}
	}

	public void echelleAbsCoordWorld(final Node t, final float x, final float y, final float z) {
		if (Thread.currentThread().equals(mainThread)) {
			t.setLocalScale(x, y, z);
		} else {
			this.enqueue(new Callable<Void>() {

				public Void call() throws Exception {
					t.setLocalScale(x, y, z);
					return null;
				}
			});
		}
	}

	public ReturnAddObject addLineToTransform(final String id, final Node t, final float vx1, final float vy1,
			final float vz1, final float vx2, final float vy2, final float vz2) {
		final BufferNode bnode = suBbeforeAddLineToTransform(id, t, vx1, vy1, vz1, vx2, vy2, vz2);
		if (Thread.currentThread().equals(mainThread)) {
			return appAccesAdd(bnode);
		} else {
			Future<ReturnAddObject> retour = this.enqueue(new Callable<ReturnAddObject>() {
				public ReturnAddObject call() throws Exception {
					return appAccesAdd(bnode);
				}
			});
			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	private BufferNode suBbeforeAddLineToTransform(final String id, final Node t, final float vx1, final float vy1,
			final float vz1, final float vx2, final float vy2, final float vz2) {
		BufferNode bnode = new BufferNode();
		bnode.setNode(new MyNode(id));
		bnode.getNode().setLocalTranslation(Vector3f.ZERO);
		bnode.setMesh(new Line(new Vector3f(vx1, vy1, vz1), new Vector3f(vx2, vy2, vz2)));
		bnode.setGeometry(new Geometry("GEO" + id, bnode.getMesh()));
		bnode.setT(t);
		bnode.setCollisionShape(new SimplexCollisionShape(new Vector3f(vx1, vy1, vz1), new Vector3f(vx2, vy2, vz2)));
		bnode.getGeometry().setMaterial(materialShadow);
		return bnode;
	}

	private BufferNode subAddLineBezierToTransform(final String id, final Node t, final float p1x, final float p1y,
			final float p2x, final float p2y, final float p3x, final float p3y, final float p4x, final float p4y) {
		Point4f[] ctrl = new Point4f[4];
		ctrl[0] = new Point4f(p1x, p1y, 0, 1);
		ctrl[1] = new Point4f(p2x, p2y, 0, 1);
		ctrl[2] = new Point4f(p3x, p3y, 0, 1);
		ctrl[3] = new Point4f(p4x, p4y, 0, 1);

		Vector3f[] curv = getCurve(ctrl, 100);

		BufferNode bnode = new BufferNode();
		bnode.setNode(new MyNode(id));
		bnode.getNode().setLocalTranslation(Vector3f.ZERO);
		bnode.setMesh(new Curve(curv, 10));
		bnode.setGeometry(new Geometry("GEO" + id, bnode.getMesh()));
		bnode.setT(t);
		bnode.setCollisionShape(new SimplexCollisionShape(new Vector3f(p1x, p1y, 0), new Vector3f(p4x, p4y, 0)));
		bnode.getGeometry().setMaterial(materialShadow);
		return bnode;
	}

	public ReturnAddObject addLineBezierToTransform(final String id, final Node t, final float p1x, final float p1y,
			final float p2x, final float p2y, final float p3x, final float p3y, final float p4x, final float p4y) {
		final BufferNode bnode = subAddLineBezierToTransform(id, t, p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y);
		if (Thread.currentThread().equals(mainThread)) {
			return appAccesAdd(bnode);
		} else {
			Future<ReturnAddObject> retour = this.enqueue(new Callable<ReturnAddObject>() {
				public ReturnAddObject call() throws Exception {
					return appAccesAdd(bnode);
				}
			});

			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	public Vector3f[] getCurve(Point4f[] ctrl, int nt) {
		float[][] M = { { 1, 0, 0, 0 }, { -3, 3, 0, 0 }, { 3, -6, 3, 0 }, { -1, 3, -3, 1 } };

		float[] t = new float[nt];
		float div = (float) (1.0 / (nt - 1));
		int i;
		for (i = 0; i < nt; i++) {
			t[i] = i * div;
		}
		// ctrl 2 float[][]
		float[][] pset = new float[4][4];
		for (i = 0; i < 4; i++) {
			ctrl[i].get(pset[i]);
		}
		Vector3f[] curv = new Vector3f[nt];
		for (i = 0; i < nt; i++) {
			float[] tt = { 1, t[i], t[i] * t[i], t[i] * t[i] * t[i] };
			float[] tmp = mymul(tt, M);
			tmp = mymul(tmp, pset);

			curv[i] = new Vector3f(tmp[0] / tmp[3], tmp[1] / tmp[3], tmp[2] / tmp[3]);
		}
		return curv;
	}
	
	private float[] mymul(float[] tt, float[][] M) {
		int i, j, k;
		float[] ret = new float[4];
		for (i = 0; i < 4; i++) {
			ret[i] = 0;
			for (j = 0; j < 4; j++) {
				ret[i] += tt[j] * M[j][i];
			}
		}
		return ret;
	}
	
	public SixDofJoint join(final Node A, final Node B, final Vector3f connectionPoint, final Vector3f linearUpper, final Vector3f linearLower, final Vector3f angularUpper, final Vector3f angularLower) {

		if(Thread.currentThread().equals(mainThread)){
			return subJoin(A, B, connectionPoint, linearUpper,
					linearLower, angularUpper, angularLower);
		}else{
			Future<SixDofJoint> retour = this.enqueue(new Callable<SixDofJoint>() {

				public SixDofJoint call() throws Exception {

					return subJoin(A, B, connectionPoint, linearUpper,
							linearLower, angularUpper, angularLower);
				}				

			});

			try {
				return retour.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	
	private SixDofJoint subJoin(final Node A, final Node B,
			final Vector3f connectionPoint,
			final Vector3f linearUpper, final Vector3f linearLower,
			final Vector3f angularUpper, final Vector3f angularLower) {

		Vector3f pivotA = A.worldToLocal(connectionPoint, new Vector3f());
		Vector3f pivotB = B.worldToLocal(connectionPoint, new Vector3f());
		SixDofJoint joint = new SixDofJoint(A.getControl(RigidBodyControl.class), B.getControl(RigidBodyControl.class), pivotA, pivotB, true);

		joint.setLinearLowerLimit(linearLower);
		joint.setLinearUpperLimit(linearUpper);
		joint.setAngularLowerLimit(angularLower);
		joint.setAngularUpperLimit(angularUpper);


		RotationalLimitMotor pitchMotor = joint.getRotationalLimitMotor(0);
		RotationalLimitMotor yawMotor = joint.getRotationalLimitMotor(1);
		RotationalLimitMotor rollMotor = joint.getRotationalLimitMotor(2);
		pitchMotor.setEnableMotor(true);
		yawMotor.setEnableMotor(true);
		rollMotor.setEnableMotor(true);
		pitchMotor.setMaxMotorForce(10.0f);
		yawMotor.setMaxMotorForce(10.0f);
		rollMotor.setMaxMotorForce(10.0f);

		return joint;
	}

	private Matrix4f subEchelleRelative(final Node t, final float x, final float y, final float z) {
		Transform tr = t.getLocalTransform();
		Transform trV = new Transform();
		trV.setScale(new Vector3f(x, y, z));
		Matrix4f m = toMatrix(tr);
		return m.mult(toMatrix(trV));
	}

	public void echelleRelative(final Node t, final float x, final float y, final float z) {
		final Matrix4f res = subEchelleRelative(t, x, y, z);
		if (Thread.currentThread().equals(mainThread)) {
			t.setLocalTransform(toTransform(res));
		} else {
			this.enqueue(new Callable<Void>() {
				public Void call() throws Exception {
					t.setLocalTransform(toTransform(res));
					return null;
				}
			});
		}
	}

	public void echelleRelativeCoordWorld(final Node t, final float x, final float y, final float z) {
		if (Thread.currentThread().equals(mainThread)) {
			t.scale(x, y, z);
		} else {
			this.enqueue(new Callable<Void>() {

				public Void call() throws Exception {
					t.scale(x, y, z);
					return null;
				}
			});
		}
	}

	private Void subMoveCamera(final float eyex, final float eyey, final float eyez, final float centerx,
			final float centery, final float centerz, final float upx, final float upy, final float upz) {
		Vector3f eye = new Vector3f(eyex, eyey, eyez);
		Vector3f center = new Vector3f(centerx, centery, centerz);
		Vector3f up = new Vector3f(upx, upy, upz);
		positionCamera(eye, center, up);
		return null;
	}

	public void moveCamera(final float eyex, final float eyey, final float eyez, final float centerx,
			final float centery, final float centerz, final float upx, final float upy, final float upz) {
		if (Thread.currentThread().equals(mainThread)) {
			subMoveCamera(eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);
		} else {
			this.enqueue(new Callable<Void>() {

				public Void call() throws Exception {
					return subMoveCamera(eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);
				}
			});
		}
	}

	public void positionCamera(final Vector3f eye, final Vector3f center, final Vector3f up) {
		if (Thread.currentThread().equals(mainThread)) {
			cam.setLocation(center);
			cam.lookAt(eye, up);
			cam.update();
		} else {
			this.enqueue(new Callable<Void>() {

				public Void call() throws Exception {
					cam.setLocation(center);
					cam.lookAt(eye, up);
					cam.update();
					return null;
				}
			});
		}
	}

	public void setLocalTranslation(final Node o, final Node t) {
		if (Thread.currentThread().equals(mainThread)) {
			t.setLocalTranslation(o.getLocalTranslation());
		} else {
			this.enqueue(new Callable<Void>() {

				public Void call() throws Exception {
					t.setLocalTranslation(o.getLocalTranslation());
					return null;
				}
			});
		}
	}

	private Void subSetLocal(final Node o, final Node t) {
		t.setLocalRotation(o.getLocalRotation());
		t.setLocalTranslation(o.getLocalTranslation());
		t.setLocalScale(o.getLocalScale());
		return null;
	}

	public void setLocal(final Node o, final Node t) {
		if (Thread.currentThread().equals(mainThread)) {
			subSetLocal(o, t);
		} else {
			this.enqueue(new Callable<Void>() {
				public Void call() throws Exception {
					return subSetLocal(o, t);
				}
			});
		}
	}

	/**
	 * Converts given transform to the matrix.
	 * 
	 * @param transform
	 *            the transform to be converted
	 * @return 4x4 matri that represents the given transform
	 */
	private Matrix4f toMatrix(Transform transform) {
		Matrix4f result = Matrix4f.IDENTITY;
		if (transform != null) {
			result = new Matrix4f();
			result.setTranslation(transform.getTranslation());
			result.setRotationQuaternion(transform.getRotation());
			result.setScale(transform.getScale());
		}
		return result;
	}

	private Transform toTransform(Matrix4f matrix4f) {
		Transform result = Transform.IDENTITY;
		if (matrix4f != null) {
			result = new Transform();
			Vector3f vector = matrix4f.toTranslationVector();
			result.setTranslation(vector);
			Quaternion quaternion = matrix4f.toRotationQuat();
			result.setRotation(quaternion);
			Vector3f scale = getScale(matrix4f);
			result.setScale(scale);
		}
		return result;
	}

	public Vector3f getScale(Matrix4f matrix) {
		float scaleX = (float) Math.sqrt(matrix.m00 * matrix.m00 + matrix.m10 * matrix.m10 + matrix.m20 * matrix.m20);
		float scaleY = (float) Math.sqrt(matrix.m01 * matrix.m01 + matrix.m11 * matrix.m11 + matrix.m21 * matrix.m21);
		float scaleZ = (float) Math.sqrt(matrix.m02 * matrix.m02 + matrix.m12 * matrix.m12 + matrix.m22 * matrix.m22);
		return new Vector3f(scaleX, scaleY, scaleZ);
	}

	@Override
	public void simpleInitApp() {
		System.out.println("simpleInitApp");
		mainThread = Thread.currentThread();
		stateManager.attach(bulletAppState);

		setPauseOnLostFocus(false);
		createWorld(rootNode, assetManager, bulletAppState.getPhysicsSpace());
		getPhysicsSpace().addCollisionListener(this);

		started.set(true);
	}

	private PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}

	public void createWorld(Node rootNode, AssetManager assetManager, PhysicsSpace space) {
		materialRed = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		materialRed.getAdditionalRenderState().setWireframe(true);
		materialRed.setColor("Color", ColorRGBA.Red);

		materialShadow = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		materialShadow.getAdditionalRenderState().setWireframe(false);

		Box floorBox = new Box(140, 0.25f, 140);
		Geometry floorGeometry = new Geometry("Floor", floorBox);
		floorGeometry.setMaterial(materialRed);
		floorGeometry.setLocalTranslation(0, -5, 0);

		floorGeometry.addControl(new RigidBodyControl(0));
		rootNode.attachChild(floorGeometry);
		space.add(floorGeometry);

		DirectionalLight dl = new DirectionalLight();
		dl.setColor(new ColorRGBA(1.0f, 0.94f, 0.8f, 1f).multLocal(1.3f));
		dl.setDirection(new Vector3f(-0.5f, -0.3f, -0.3f).normalizeLocal());
		rootNode.addLight(dl);

		Vector3f lightDir2 = new Vector3f(0.70518064f, 0.5902297f, -0.39287305f);
		DirectionalLight dl2 = new DirectionalLight();
		dl2.setColor(new ColorRGBA(0.7f, 0.85f, 1.0f, 1f));
		dl2.setDirection(lightDir2);
		rootNode.addLight(dl2);

		cam.setFrustum(1.0f, 15000.0f, -0.55f, 0.55f, 0.4125f, -0.4125f);
		cam.update();
		// flyCam.setEnabled(false);
	}

	@Override
	public void collision(PhysicsCollisionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
