package fr.next.media.space;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jme3.math.Transform;
import com.jme3.scene.Node;

import junit.framework.TestCase;

public class SpatialTest extends TestCase {

	private static  AtomicBoolean started = new AtomicBoolean(false);

	public void test3D() throws InterruptedException   {
		Space esp = new Space(started);
		ISpace mesp = new ManageEspace(esp);
		esp.start();
		while(!started.get()) {
			Thread.sleep(1000);
		}
		MyInstance s = new MyInstance("sphere1", ManageEspace.TSPHERE, false);
		mesp.imagineX(s);
		ReturnAddObject o = esp.addBoxToTransform("corps", s.getT(), 1f, 1f, 1f, false);
		display(s.getT());
		display(o.getT());
		
		mesp.moveAbsX(s, 1f, 3f, 4f);
		
		Thread.sleep(2000);
		
		display(s.getT());
		display(o.getT());
		Thread.sleep(30000);
	}

	private static void display(Node t) {
		System.out.println(t.getName());
		Transform transform = t.getLocalTransform();
		Transform wTransform = t.getWorldTransform();
		System.out.println("local transform");
		System.out.println(transform);
		System.out.println(transform.getRotation());
		System.out.println("world transform");
		System.out.println(wTransform);
	}
}
