package fr.next.logigram.espace;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import fr.next.logigram.espace.Espace;
import fr.next.logigram.espace.IManageEspace;
import fr.next.logigram.espace.ManageEspace;
import fr.next.logigram.espace.MyInstance;
import fr.next.logigram.espace.ReturnAddObject;
import junit.framework.TestCase;

public class EspaceCompositionTest extends TestCase {

	private static  AtomicBoolean started = new AtomicBoolean(false);
	
	private float rap = 5.0f;

	public void test2D() throws InterruptedException   {
		
		Espace esp = new Espace(started);
		IManageEspace mesp = new ManageEspace(esp);
		esp.start();
		while(!started.get()) {
			Thread.sleep(1000);
		}
		
		MyInstance s = new MyInstance("a", "a", false);

		Node t = esp.addToRootBranchGroupAndTransform();	
		Node tA = new Node("test");
		s.setT(tA);
		
		Vector3f p1 = new Vector3f(-0.1f*rap, -0.1f*rap, 0f*rap);
		Vector3f p2 = new Vector3f(0f*rap, 0.1f*rap, 0f*rap);
		Vector3f p3 = new Vector3f(0.1f*rap, -0.1f*rap, 0f*rap);
		Vector3f p4 = new Vector3f(-0.05f*rap, 0f*rap, 0f*rap);
		Vector3f p5 = new Vector3f(0.05f*rap, 0f*rap, 0f*rap);
		
		ReturnAddObject btLine1 = esp.addLineToTransform("", tA, p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ());
		ReturnAddObject btLine2 = esp.addLineToTransform("", tA, p2.getX(), p2.getY(), p2.getZ(), p3.getX(), p3.getY(), p3.getZ());
		ReturnAddObject btLine3 = esp.addLineToTransform("", tA, p4.getX(), p4.getY(), p4.getZ(), p5.getX(), p5.getY(), p5.getZ());
						
		SixDofJoint s1 = esp.join(btLine1.getT(), btLine3.getT(), p4, new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0));
		SixDofJoint s2 = esp.join(btLine2.getT(), btLine3.getT(), p5, new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0));
		SixDofJoint s3 = esp.join(btLine1.getT(), btLine2.getT(), p2, new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0), new Vector3f(0f, 0f, 0));
		t.attachChild(tA);
		
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
