package fr.next.media.space;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jme3.math.Transform;
import com.jme3.scene.Node;

import junit.framework.TestCase;

public class RotationTest extends TestCase {

	private static  AtomicBoolean started = new AtomicBoolean(false);

	public void test3D() throws InterruptedException   {
		Space esp = new Space(started);
		ISpace mesp = new ManageEspace(esp);
		esp.start();
		while(!started.get()) {
			Thread.sleep(1000);
		}
		
		Node t = esp.addToRootBranchGroupAndTransform();	
		Node dot = new Node("test");
		ReturnAddObject btHead = esp.addSphereToTransform("dot", dot, 0.2f, false);
		t.attachChild(dot);
		Node dot2 = new Node("test2");
		ReturnAddObject btHead2 = esp.addSphereToTransform("dot2", dot2, 0.1f, false);
		dot.attachChild(dot2);
		esp.translationAbsCoordWorld(btHead2.getT(), 1f, 0f, 0f);	
		Thread.sleep(5000);
		
		display(btHead2.getT());
		
		esp.rotationRelativeDegree(dot, 0, 0, 1, 90);
		Thread.sleep(5000);
		
		display(btHead2.getT());
		esp.rotationRelativeDegree(dot, 0, 1, 0, 180);
		
		
		Thread.sleep(5000);
		
		display(btHead2.getT());
		
//		mesp.rotateAbsX(i, 1, -1, 0, -90);
//		display(i.getT());
//		display(i.getT());
//		
//		Thread.sleep(2000);
//		
//		display(i.getT());
		Thread.sleep(30000);
	}

	private static void display(Node t) {
		System.out.println(t.getName());
		Transform transform = t.getLocalTransform();
		Transform wTransform = t.getWorldTransform();
		Node p = t.getParent();
		Transform transformp = p.getLocalTransform();
		Transform wTransformp = p.getWorldTransform();
		System.out.println("local transform");
		System.out.println(transform);
		System.out.println("world transform");
		System.out.println(wTransform);
		System.out.println("p local transform");
		System.out.println(transformp);
		System.out.println("p world transform");
		System.out.println(wTransformp);
	}
}
