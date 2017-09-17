package fr.next.media.space;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.next.media.space.ISpace;
import fr.next.media.space.ManageEspace;
import fr.next.media.space.MyInstance;
import fr.next.media.space.Space;
import junit.framework.TestCase;

public class SpaceTest extends TestCase {

	private static  AtomicBoolean started = new AtomicBoolean(false);

	public void test2D() throws InterruptedException   {
		
		Space esp = new Space(started);
		ISpace mesp = new ManageEspace(esp);
		esp.start();
		while(!started.get()) {
			Thread.sleep(1000);
		}
		
		MyInstance s = new MyInstance("sphere1", ManageEspace.TSPHERE, false);
		mesp.imagineX(s);

		MyInstance b = new MyInstance("box1", ManageEspace.TBOX, false);
		mesp.imagineXADroiteY(b, s);
		
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
