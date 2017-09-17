package fr.next.media.db;

import static fr.next.media.array.impl.ArrayFactory.mot;

import org.junit.AfterClass;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.db.ManageOrientDb;
import fr.next.media.db.SerialObject;
import junit.framework.TestCase;

public class DbTest extends TestCase {
	
	private static ManageOrientDb data = new ManageOrientDb();
	
	
	@AfterClass
	public void afterClass() {
		data.clean();
	}
	
	public void testDb() throws Exception {
		String id = "test";
		String classId = "test";
		String val = "test";
		data.save(id, val.getBytes(), classId);
	
		byte[] m = data.findAndGetContent(id, classId);
		assertEquals(val, new String(m));
	}
	
	public void testAxeDb() throws Exception {
		String val1 = "Test";
		String val2 = "Player1";
		String val3 = "Test";
		
		
		String id = "test";
		String classId = "player";
		
		AxeOrd<AxeValue<String>> players = new AxeOrd<AxeValue<String>>("joueurs");
		players.add(new AxeValue<String>(val1));
		players.add(new AxeValue<String>(val2));
		players.add(new AxeValue<String>(val3));
		
		
		data.save(id, SerialObject.serialize(players), classId);
		
		byte[] mg = data.findAndGetContent(id, classId);
		AxeOrd<AxeValue<String>> playersOut = (AxeOrd<AxeValue<String>>) SerialObject.deserialize(mg);
		
		assertEquals(val1, playersOut.getElements().get(0).getValue());
		assertEquals(val2, playersOut.getElements().get(1).getValue());
		assertEquals(val3, playersOut.getElements().get(2).getValue());
	}
	
	public void testAxeDb2() throws Exception {
		AxeOrd<AxeValue<AxeValue<Character>>> test = mot("test");
		byte[] serializeObject = SerialObject.serialize(test);
		System.out.println(serializeObject.length);
		assertTrue(serializeObject.length < 1000);
	}

}
