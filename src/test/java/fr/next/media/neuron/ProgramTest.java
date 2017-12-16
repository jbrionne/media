package fr.next.media.neuron;

import org.junit.Assert;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeInt;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXD;
import fr.next.media.array.CoordinatesXDSpaceByIndices;
import fr.next.media.array.MedBinary;
import fr.next.media.array.impl.MapXDGenericImpl;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;
import fr.next.media.memory.InternalCmds;
import junit.framework.TestCase;

public class ProgramTest extends TestCase {

	
	
	public void testProgram() {
		String line1 = "a=1";
		String line2 = "b=3";
		String line3 = "c=a+b";
		//c ?
		
		Axe<AxeValue<String>> nameToReF = new AxeOrd<>("nameToReF");
		nameToReF.add(new AxeValue<String>("a"));
		nameToReF.add(new AxeValue<String>("b"));
		nameToReF.add(new AxeValue<String>("c"));
		
		ArrayXDOrd<Integer, String, Axe<AxeValue<String>>> worldAxes = new MapXDWithEmptyValueGenericImpl<>(Integer.class, 0, nameToReF);
		worldAxes.setValue(1, "a");
		worldAxes.setValue(3, "b");
		
		worldAxes.setValue(worldAxes.getValue("a") + worldAxes.getValue("b") , "c");
		
		Assert.assertTrue(4 == worldAxes.getValue("c"));
		
	}
	
	{
		
//		Assert.assertEquals(null, worldAxes.getValue("c"));
//		Assert.assertEquals(null, worldAxes.getValue("c"));
	}
	
	
	public void testLink() {
		
		Axe<AxeValue<String>> nameToReF = new AxeOrd<>("nameToReF");
		nameToReF.add(new AxeValue<String>("a"));
		nameToReF.add(new AxeValue<String>("b"));
		nameToReF.add(new AxeValue<String>("c"));
		
		Axe<AxeValue<Axe>> human = new AxeOrd<>("human");
		
		MedBinary classifierHead = new MedBinary("classifierHead");
		
		Axe<AxeValue<Axe>> head = new AxeOrd<>("head");
		Axe<AxeValue<Axe>> arm = new AxeOrd<>("arm");
		Axe<AxeValue<Axe>> body = new AxeOrd<>("body");
		Axe<AxeValue<Axe>> leg = new AxeOrd<>("leg");
		
		classifierHead.setLeft(human);
		classifierHead.setRight(head);
		
		human.add(new AxeValue(classifierHead));
		head.add(new AxeValue(classifierHead));
		
		//arm right
		human.add(new AxeValue(arm));
		//arm left
		human.add(new AxeValue(arm));
		human.add(new AxeValue(body));
		//leg right
		human.add(new AxeValue(leg));
		//leg left
		human.add(new AxeValue(leg));
		
		//array not sufficient to expose relation between component
		
		
	}
	
	
	public void testSimple() {
		
		AxeInt cmds = new AxeInt("cmds", 100);
		ArrayXDOrd a = new MapXDGenericImpl<>(String.class, cmds);
		
		//cases mémoire permanente
		
		//données
		
		//cases mémoire temporaire
		//chargement des paramètres avant execution
		//et pour les données de sorties
		
		//cases mémoire pour le process (cmds ?)
		
		a.setValue("CREER ", 0);
		a.setValue("XXX ", 0);
		
		for(int i = 0; i < a.getAxe(0).size(); i++) {
			System.out.println(a.getValue(i));
		}
		
		
		InternalCmds internalComds = new InternalCmds();
		int cmd = 0;
		System.out.println(cmd++ + ": " + internalComds.doCmd("CREER head"));
		System.out.println(cmd++ + ": " + internalComds.doCmd("GET head"));
		
	}


}
