package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeInt;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.CoordinatesXDByIndices;
import fr.next.logigram.array.impl.ArrayFactory;
import fr.next.logigram.array.impl.logigram.ArrayLogigramValue;
import junit.framework.TestCase;

public class ArrayTest extends TestCase {

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

		AxeInt axeLine = new AxeInt<>("worldLine", 1);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		int[] indices = new int[] { 0, 0 };
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArrayLogigramValue(domPersons, domCaches, new CoordinatesXDByIndices(axes, indices));
		array.setValue(ArrayLogigramValue.NEG, BLANDINE, ARBRE);
		array.setValue(ArrayLogigramValue.NEG, ASMA, ARBRE);
		System.out.println(array.getValuesForAnAxe(0, 1));
		System.out.println(array.getValuesForAnAxe(1, 0));
		assertEquals("[NEG, EMPTY, EMPTY, EMPTY, EMPTY]", array.getValuesForAnAxe(0, 1).toString());
		assertEquals("[NEG, NEG, EMPTY, EMPTY, EMPTY]", array.getValuesForAnAxe(1, 0).toString());

		System.out.println(array.getAll());
	}

	public void test2DX() {
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

		AxeInt axeLine = new AxeInt<>("worldLine", 1);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		int[] indices = new int[] { 0, 0 };
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArrayLogigramValueX(domPersons, domCaches, new CoordinatesXDByIndices(axes, indices));
		array.setValue(ArrayLogigramValue.NEG, BLANDINE, ARBRE);
		array.setValue(ArrayLogigramValue.NEG, ASMA, ARBRE);
		// 1 0
		System.out.println(array.getValuesForAnAxe(0, 1));
		System.out.println(array.getValuesForAnAxe(1, 0));
		assertEquals("[NEG, EMPTY, EMPTY, EMPTY, EMPTY]", array.getValuesForAnAxe(0, 1).toString());
		assertEquals("[NEG, NEG, EMPTY, EMPTY, EMPTY]", array.getValuesForAnAxe(1, 0).toString());

		System.out.println(array.getAll());
	}

	public void test3D() {
		Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>("1"));
		domPersons.add(new AxeValue<String>("2"));
		domPersons.add(new AxeValue<String>("3"));
		domPersons.add(new AxeValue<String>("4"));
		domPersons.add(new AxeValue<String>("5"));

		Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>("1"));
		domCaches.add(new AxeValue<String>("2"));
		domCaches.add(new AxeValue<String>("3"));
		domCaches.add(new AxeValue<String>("4"));
		domCaches.add(new AxeValue<String>("5"));

		AxeOrd<AxeValue<String>> domDuree = new AxeOrd<AxeValue<String>>(DUREE);
		domDuree.add(new AxeValue<String>("1"));
		domDuree.add(new AxeValue<String>("2"));
		domDuree.add(new AxeValue<String>("3"));
		domDuree.add(new AxeValue<String>("4"));
		domDuree.add(new AxeValue<String>("5"));

		AxeInt axeLine = new AxeInt<>("worldLine", 1);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		AxeInt axeZ = new AxeInt<>("worldZ", 1);
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		axes.add(axeZ);
		int[] indices = new int[] { 0, 0, 0 };
		ArrayXDOrd<String, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<String, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArrayLogigramValue3(domPersons, domCaches, domDuree,
						new CoordinatesXDByIndices(axes, indices));

		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				for (int l = 0; l < 5; l++) {
					array.setValueByIndices(i + "" + k + "" + l, i, k, l);
				}
			}
		}
		// 1 0
		System.out.println(array.getValuesForAnAxe(0, 1));
		System.out.println(array.getValuesForAnAxe(1, 0));
		assertEquals(
				"[100, 101, 102, 103, 104, 110, 111, 112, 113, 114, 120, 121, 122, 123, 124, 130, 131, 132, 133, 134, 140, 141, 142, 143, 144]",
				array.getValuesForAnAxe(0, 1).toString());
		assertEquals(
				"[000, 001, 002, 003, 004, 100, 101, 102, 103, 104, 200, 201, 202, 203, 204, 300, 301, 302, 303, 304, 400, 401, 402, 403, 404]",
				array.getValuesForAnAxe(1, 0).toString());

		System.out.println(array.getAll());
	}

	public void test3DX() {
		Axe<AxeValue<Integer>> domPersons = new AxeOrd<AxeValue<Integer>>(ENFANT);
		domPersons.add(new AxeValue<Integer>(1));
		domPersons.add(new AxeValue<Integer>(2));
		domPersons.add(new AxeValue<Integer>(3));
		domPersons.add(new AxeValue<Integer>(4));
		domPersons.add(new AxeValue<Integer>(5));

		Axe<AxeValue<Integer>> domCaches = new AxeOrd<AxeValue<Integer>>(CACHETTE);
		domCaches.add(new AxeValue<Integer>(1));
		domCaches.add(new AxeValue<Integer>(2));
		domCaches.add(new AxeValue<Integer>(3));
		domCaches.add(new AxeValue<Integer>(4));
		domCaches.add(new AxeValue<Integer>(5));

		AxeOrd<AxeValue<Integer>> domDuree = new AxeOrd<AxeValue<Integer>>(DUREE);
		domDuree.add(new AxeValue<Integer>(1));
		domDuree.add(new AxeValue<Integer>(2));
		domDuree.add(new AxeValue<Integer>(3));
		domDuree.add(new AxeValue<Integer>(4));
		domDuree.add(new AxeValue<Integer>(5));

		AxeInt axeLine = new AxeInt<>("worldLine", 1);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		AxeInt axeZ = new AxeInt<>("worldZ", 1);
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		axes.add(axeZ);
		int[] indices = new int[] { 0, 0, 0 };
		ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArrayLogigramValueX3Integer(domPersons, domCaches, domDuree,
						new CoordinatesXDByIndices(axes, indices));

		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				for (int l = 0; l < 5; l++) {
					System.out.println(i + "" + k + "" + l);
					array.setValueByIndices(i + "" + k + "" + l, (Integer) i, (Integer) k, (Integer) l);
				}
			}
		}

		// 1 0
		System.out.println(array.getValuesForAnAxe(0, 1));
		System.out.println(array.getValuesForAnAxe(1, 0));
		assertEquals(
				"[100, 101, 102, 103, 104, 110, 111, 112, 113, 114, 120, 121, 122, 123, 124, 130, 131, 132, 133, 134, 140, 141, 142, 143, 144]",
				array.getValuesForAnAxe(0, 1).toString());
		assertEquals(
				"[000, 001, 002, 003, 004, 100, 101, 102, 103, 104, 200, 201, 202, 203, 204, 300, 301, 302, 303, 304, 400, 401, 402, 403, 404]",
				array.getValuesForAnAxe(1, 0).toString());

		System.out.println(array.getAll());
	}
	
	public void test3DMatrix() {
		System.out.println("test3DMatrix");
		Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>("1"));
		domPersons.add(new AxeValue<String>("2"));
		domPersons.add(new AxeValue<String>("3"));

		Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>("1"));
		domCaches.add(new AxeValue<String>("2"));
		domCaches.add(new AxeValue<String>("3"));


		AxeInt axeLine = new AxeInt<>("worldLine", 1);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		int[] indices = new int[] { 0, 0, 0 };
		ArrayXDOrd<String, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<String, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceFloatMatrice3f(domPersons, domCaches,
						new CoordinatesXDByIndices(axes, indices));
		ArrayXDOrd<Float, String, Axe<AxeValue<String>>> arrayGen = (ArrayXDOrd<Float, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArray2DFloat(domPersons, domCaches,
						new CoordinatesXDByIndices(axes, indices));
		
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				arrayGen.setValueByIndices(0.0f, (Integer) i, (Integer) j);
			}
		}
		arrayGen.setValueByIndices(1.0f, (Integer) 0, (Integer) 0);
		arrayGen.setValueByIndices(1.0f, (Integer) 1, (Integer) 1);
		arrayGen.setValueByIndices(1.0f, (Integer) 2, (Integer) 2);
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				assertEquals(array.getValuesForAnAxe(i, j), arrayGen.getValuesForAnAxe(i, j));
			}
		}
		
	}
	
	public void test4DMatrix() {
		System.out.println("test4DMatrix");
		Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>("1"));
		domPersons.add(new AxeValue<String>("2"));
		domPersons.add(new AxeValue<String>("3"));
		domPersons.add(new AxeValue<String>("4"));

		Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>("1"));
		domCaches.add(new AxeValue<String>("2"));
		domCaches.add(new AxeValue<String>("3"));
		domCaches.add(new AxeValue<String>("4"));


		AxeInt axeLine = new AxeInt<>("worldLine", 1);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		int[] indices = new int[] { 0, 0, 0 };
		ArrayXDOrd<String, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<String, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceFloatMatrice4f(domPersons, domCaches,
						new CoordinatesXDByIndices(axes, indices));
		ArrayXDOrd<Float, String, Axe<AxeValue<String>>> arrayGen = (ArrayXDOrd<Float, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArray2DFloat(domPersons, domCaches,
						new CoordinatesXDByIndices(axes, indices));
		
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				arrayGen.setValueByIndices(0.0f, (Integer) i, (Integer) j);
			}
		}
		arrayGen.setValueByIndices(1.0f, (Integer) 0, (Integer) 0);
		arrayGen.setValueByIndices(1.0f, (Integer) 1, (Integer) 1);
		arrayGen.setValueByIndices(1.0f, (Integer) 2, (Integer) 2);
		arrayGen.setValueByIndices(1.0f, (Integer) 3, (Integer) 3);
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				assertEquals(array.getValuesForAnAxe(i, j), arrayGen.getValuesForAnAxe(i, j));
			}
		}
		
	}

}
