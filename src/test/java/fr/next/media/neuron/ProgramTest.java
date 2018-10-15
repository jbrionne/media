package fr.next.media.neuron;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;
import fr.next.media.memory.Media;
import fr.next.media.memory.Memory;
import junit.framework.TestCase;

public class ProgramTest extends TestCase {

	public void testProgram() {
		String line1 = "a=1";
		String line2 = "b=3";
		String line3 = "c=a+b";
		// c ?

		Axe<AxeValue<String>> nameToReF = new AxeOrd<>("nameToReF");
		nameToReF.add(new AxeValue<String>("a"));
		nameToReF.add(new AxeValue<String>("b"));
		nameToReF.add(new AxeValue<String>("c"));

		ArrayXDOrd<Integer, String, Axe<AxeValue<String>>> worldAxes = new MapXDWithEmptyValueGenericImpl<>(
				Integer.class, 0, nameToReF);
		worldAxes.setValue(1, "a");
		worldAxes.setValue(3, "b");

		worldAxes.setValue(worldAxes.getValue("a") + worldAxes.getValue("b"), "c");

		Assert.assertTrue(4 == worldAxes.getValue("c"));

	}

	{

		// Assert.assertEquals(null, worldAxes.getValue("c"));
		// Assert.assertEquals(null, worldAxes.getValue("c"));
	}
	
	public void testLinkB() throws InterruptedException {
		Media m = new Media();
		ExecutorService exec = Executors.newFixedThreadPool(1);
		exec.execute(m);
		
		m.in("0");
		m.in(Media.EXEC_REC);
		
	}
	

	public void testLink() throws InterruptedException {
		// associer groupe et opérations sur le groupe
		// la structure ne devrait jamais être indépendante d'une série
		// d'opérations "comprise par le système".
		Media m = new Media();
		ExecutorService exec = Executors.newFixedThreadPool(1);
		exec.execute(m);
		
		
		m.in("CREER");
		m.in("A");
		m.in("CREER");
		m.in("a");
		m.in("CREER");
		m.in("aP");
		
		m.in("CREER");
		m.in("_A");
		m.in("ADD");
		m.in("_A");
		m.in("A");
		m.in("ADD");
		m.in("_A");
		m.in("a");
		m.in("ADD");
		m.in("_A");
		m.in("aP");
		
		m.in("CREER");
		m.in(Memory.ALPHA_MAJ_ID);
		m.in("ADD");
		m.in(Memory.ALPHA_MAJ_ID);
		m.in("_A");
		m.in("ADD");
		m.in("_1");
		m.in(Memory.ALPHA_MAJ_ID);
		
		m.in("CREER");
		m.in(Memory.LETTRES);
		m.in("ADD");
		m.in(Memory.LETTRES);
		m.in("_A");
		m.in("ADD");
		m.in("_A");
		m.in(Memory.LETTRES);
		
		//
		
		m.in("GET");
		m.in(Memory.ALPHA_MAJ_ID);
	
		
		m.in("GET");
		m.in(Memory.LETTRES);
		
		m.in("GET");
		m.in("_A");
		
		
		{
			Axe<AxeValue> majs = (Axe<AxeValue>) m.nextOut();
			System.out.println("r : " + majs.getName());
			for (int i = 0; i < majs.size(); i++) {
				System.out.println(majs.getElements().get(i).getValue());
			}
		}
		
		while(!m.isFinish()) {
			Thread.sleep(1000);
		}
		
		exec.shutdown();
	}

}
