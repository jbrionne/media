package fr.next.logigram.espace;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.next.logigram.espace.Espace;
import fr.next.logigram.espace.IManageEspace;
import fr.next.logigram.espace.ManageEspace;
import fr.next.logigram.espace.MyInstance;
import junit.framework.TestCase;

public class EspaceTest extends TestCase {

	private static  AtomicBoolean started = new AtomicBoolean(false);

	public void test2D() throws InterruptedException   {
		
		Espace esp = new Espace(started);
		IManageEspace mesp = new ManageEspace(esp);
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
