package fr.next.media.memory;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.ArrayFactory;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
import fr.next.media.human.MedString;
import junit.framework.TestCase;

public class MemoryTest extends TestCase {

	public static final String CLASSE = "Classe";
	public static final String DUREE = "Durée";
	public static final String CACHETTE = "Cachette";
	private static final String PIERRE = "Pierre";
	private static final String MUR = "Mur";
	private static final String FEUILLES = "Feuilles";
	private static final String BRANCHES = "Branches";
	private static final String ARBRE = "Arbre";
	public static final String ENFANT = "Enfant";
	private static final String LOICK = "Loïck";
	private static final String FLORENT = "Florent";
	private static final String CELIA = "Célia";
	private static final String BLANDINE = "Blandine";
	private static final String ASMA = "Asma";
	
	private Memory memory = Memory.getInstance();
	

	public void testMemory() {
		
		AxeOrd<AxeValue<AxeValue<Character>>> m = new MedString("test");
		memory.save("test", m);
		AxeOrd<AxeValue<AxeValue<Character>>> exM = (AxeOrd<AxeValue<AxeValue<Character>>>) memory.findAndGetContent("test");
		assertEquals(m.getName(), exM.getName());
		for(int i = 0 ; i < m.getElements().size(); i++) {
			assertEquals(m.getElements().get(i).getValue().getValue(), exM.getElements().get(i).getValue().getValue());
		}
	}
	
	

	public void test2D() {
		Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>(ASMA));
		domPersons.add(new AxeValue<String>(BLANDINE));
		domPersons.add(new AxeValue<String>(CELIA));
		domPersons.add(new AxeValue<String>(FLORENT));
		domPersons.add(new AxeValue<String>(LOICK));

		Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>(ARBRE));
		domCaches.add(new AxeValue<String>(BRANCHES));
		domCaches.add(new AxeValue<String>(FEUILLES));
		domCaches.add(new AxeValue<String>(MUR));
		domCaches.add(new AxeValue<String>(PIERRE));

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArrayLogigramValue(domPersons, domCaches);
		array.setValue(ArrayLogigramValue.NEG, BLANDINE, ARBRE);
		array.setValue(ArrayLogigramValue.NEG, ASMA, ARBRE);
		System.out.println(array.getValuesForAnAxe(0, 1));
		System.out.println(array.getValuesForAnAxe(1, 0));
		assertEquals("[NEG, EMPTY, EMPTY, EMPTY, EMPTY]", array.getValuesForAnAxe(0, 1).toString());
		assertEquals("[NEG, NEG, EMPTY, EMPTY, EMPTY]", array.getValuesForAnAxe(1, 0).toString());

		memory.save(ENFANT, domPersons);
		memory.save(CACHETTE, domCaches);
		memory.save("array", array);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> newArray = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) memory.findAndGetContent("array");
		assertEquals("[NEG, EMPTY, EMPTY, EMPTY, EMPTY]", newArray.getValuesForAnAxe(0, 1).toString());
		assertEquals("[NEG, NEG, EMPTY, EMPTY, EMPTY]", newArray.getValuesForAnAxe(1, 0).toString());

		System.out.println(array.getAll());
	}
	
}
