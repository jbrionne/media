package fr.next.logigram.space;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.next.logigram.space.Space;
import fr.next.logigram.space.ISpace;
import fr.next.logigram.space.ManageEspace;
import fr.next.logigram.space.MyInstance;
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
