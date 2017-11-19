package fr.next.media.neuron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeInt;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.Array2DMatrix3fImpl;
import fr.next.media.array.impl.ArrayFactory;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
import fr.next.media.draw.Draw;
import fr.next.media.process.History;
import fr.next.media.process.ResultStrategy;
import fr.next.media.process.Strategy;
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
	
	


}
