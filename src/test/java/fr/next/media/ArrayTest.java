package fr.next.media;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeInt;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.CoordinatesXDSpaceByIndices;
import fr.next.media.array.ReferenceUtils;
import fr.next.media.array.impl.Array2DMatrix4fImpl;
import fr.next.media.array.impl.ArrayFactory;
import fr.next.media.array.impl.ArrayXDWithEmptyValueGenericImpl;
import fr.next.media.array.impl.MapXDGenericImpl;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
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

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArrayLogigramValue(domPersons, domCaches);
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

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArrayLogigramValueX(domPersons, domCaches);
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

		ArrayXDOrd<String, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<String, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArrayLogigramValue3(domPersons, domCaches, domDuree);

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

		ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArrayLogigramValueX3Integer(domPersons, domCaches, domDuree);

		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				for (int l = 0; l < 5; l++) {
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
	
	public void test3DMap() {
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

		ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArrayLogigramValueMapX3Integer(domPersons, domCaches, domDuree);
		
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				for (int l = 0; l < 5; l++) {
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


		ArrayXDOrd<String, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<String, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceFloatMatrice3f(domPersons, domCaches);
		ArrayXDOrd<Float, String, Axe<AxeValue<String>>> arrayGen = (ArrayXDOrd<Float, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArray2DFloat(domPersons, domCaches);
		
		
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


		ArrayXDOrd<String, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<String, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceFloatMatrice4f(domPersons, domCaches);
		ArrayXDOrd<Float, String, Axe<AxeValue<String>>> arrayGen = (ArrayXDOrd<Float, String, Axe<AxeValue<String>>>) ArrayFactory
				.newInstanceArray2DFloat(domPersons, domCaches);
		
		
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
	
	public void testMerge() {
		Axe<AxeValue<Integer>> x = new AxeOrd<>("x");
		x.add(new AxeValue<Integer>(0));
		x.add(new AxeValue<Integer>(1));
		x.add(new AxeValue<Integer>(2));
		x.add(new AxeValue<Integer>(3));
		x.add(new AxeValue<Integer>(4));
		x.add(new AxeValue<Integer>(5));
		x.add(new AxeValue<Integer>(6));
		x.add(new AxeValue<Integer>(7));

		Axe<AxeValue<Integer>> y = new AxeOrd<>("y");
		y.add(new AxeValue<Integer>(0));
		y.add(new AxeValue<Integer>(1));
		y.add(new AxeValue<Integer>(2));
		y.add(new AxeValue<Integer>(3));
		y.add(new AxeValue<Integer>(4));
		y.add(new AxeValue<Integer>(5));
		y.add(new AxeValue<Integer>(6));
		y.add(new AxeValue<Integer>(7));
		
		Axe<AxeValue<Integer>> xLoc1 = new AxeOrd<>("x");
		xLoc1.add(new AxeValue<Integer>(0));
		xLoc1.add(new AxeValue<Integer>(1));
		xLoc1.add(new AxeValue<Integer>(2));

		Axe<AxeValue<Integer>> yLoc1 = new AxeOrd<>("y");
		yLoc1.add(new AxeValue<Integer>(0));
		yLoc1.add(new AxeValue<Integer>(1));
		yLoc1.add(new AxeValue<Integer>(2));
		
		Axe<AxeValue<Integer>> xLoc2 = new AxeOrd<>("x");
		xLoc2.add(new AxeValue<Integer>(0));
		xLoc2.add(new AxeValue<Integer>(1));
		xLoc2.add(new AxeValue<Integer>(2));
		xLoc2.add(new AxeValue<Integer>(3));

		Axe<AxeValue<Integer>> yLoc2 = new AxeOrd<>("y");
		yLoc2.add(new AxeValue<Integer>(0));
		yLoc2.add(new AxeValue<Integer>(1));
		yLoc2.add(new AxeValue<Integer>(2));
		yLoc2.add(new AxeValue<Integer>(3));

		AxeInt xLoc = new AxeInt("x", 3);
		AxeInt yLoc = new AxeInt("y", 3);
		ArrayXDOrd worldAxes = new MapXDGenericImpl<>(Integer.class, x, y);
		ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> arrayLoc1 = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArray2DFloat(xLoc1, yLoc1);
		ArrayXDOrd indicesLoc1 = new Array2DMatrix4fImpl<>(xLoc, yLoc);
		indicesLoc1.setValue(0f, 0, 0);
		indicesLoc1.setValue(0f, 0, 1);
		indicesLoc1.setValue(0f, 0, 2);
		indicesLoc1.setValue(0f, 1, 0);
		indicesLoc1.setValue(0f, 1, 1);
		indicesLoc1.setValue(0f, 1, 2);
		indicesLoc1.setValue(1f, 1, 3);
		indicesLoc1.setValue(1f, 2, 0);
		indicesLoc1.setValue(1f, 2, 1);
		indicesLoc1.setValue(1f, 2, 2);
		
		arrayLoc1.addCoordinate(new CoordinatesXDSpaceByIndices(worldAxes, indicesLoc1));
		arrayLoc1.setValue(1.0f, 0, 0);
		arrayLoc1.setValue(2.0f, 0, 1);
		arrayLoc1.setValue(3.0f, 1, 0);
		arrayLoc1.setValue(4.0f, 1, 1);
		
		ArrayXDOrd indicesLoc2 = new Array2DMatrix4fImpl<>(xLoc, yLoc);
		indicesLoc2.setValue(2f, 0, 0);
		indicesLoc2.setValue(2f, 0, 1);
		indicesLoc2.setValue(0f, 0, 2);
		indicesLoc2.setValue(0f, 1, 0);
		indicesLoc2.setValue(0f, 1, 1);
		indicesLoc2.setValue(0f, 1, 2);
		indicesLoc2.setValue(1f, 1, 3);
		indicesLoc2.setValue(1f, 2, 0);
		indicesLoc2.setValue(1f, 2, 1);
		indicesLoc2.setValue(1f, 2, 2);
		
		ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> arrayLoc2 = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArray2DFloat(xLoc2, yLoc2);
		CoordinatesXDSpaceByIndices coord2 = new CoordinatesXDSpaceByIndices(worldAxes, indicesLoc2);
		arrayLoc2.addCoordinate(coord2);
		arrayLoc2.setValue(5.0f, 0, 0);
		arrayLoc2.setValue(6.0f, 0, 1);
		arrayLoc2.setValue(7.0f, 0, 2);
		arrayLoc2.setValue(8.0f, 1, 0);
		arrayLoc2.setValue(9.0f, 1, 1);
		arrayLoc2.setValue(10.0f, 1, 2);
		arrayLoc2.setValue(11.0f, 2, 0);
		arrayLoc2.setValue(12.0f, 2, 1);
		arrayLoc2.setValue(13.0f, 2, 2);
		
		
		//  0  1 2  3   4 
		//0 1f 2f
		//1 3f 4f
		//2      5f  6f  7f
		//3      8f  9f   10f
		//4      11f 12f  13f
		assertEquals(4f, arrayLoc1.getValueFromUpperAxeCoordByIndices(worldAxes, 1f, 1f));
		assertEquals(9f, arrayLoc2.getValueFromUpperAxeCoordByIndices(worldAxes, 3f, 3f));
		
		assertEquals(4f, worldAxes.getValueWithInclusionOfArrayChilds(1, 1));
		assertEquals(9f, worldAxes.getValueWithInclusionOfArrayChilds(3, 3));
		
		worldAxes.merge();
		
		assertEquals(4f, worldAxes.getValue(1, 1));
		assertEquals(9f, worldAxes.getValue(3, 3));
		
		
		List l = coord2.getPositionIndicesList();
		System.out.println("position : " + Arrays.toString(l.toArray()));
		
		
		
		//TODO
		
		//Un repère A qui contient un repère B qui lui-même contient deux repères C et D
		// Si B change alors les coordonnées de C et D change dans le repère A !!
		//On doit rechercher la coordonnée aussi dans les parents et faire la transformation pour chaque étape
		
		//TODO
		//Un axe en relation avec un autre axe. Exemple Axe x1 avec unité à 1, Axe x2 avec unité à 2.
		//soit x1 * 2 = x2
		//Autre cas des rotations + echelle des axes..matrices.
		
	}
	
	
	public void testInverseAxeByRotation() {
		Axe<AxeValue<Integer>> x = new AxeOrd<>("x");
		x.add(new AxeValue<Integer>(0));
		x.add(new AxeValue<Integer>(1));
		x.add(new AxeValue<Integer>(2));
		x.add(new AxeValue<Integer>(3));
		x.add(new AxeValue<Integer>(4));
		x.add(new AxeValue<Integer>(5));
		x.add(new AxeValue<Integer>(6));
		x.add(new AxeValue<Integer>(7));

		Axe<AxeValue<Integer>> y = new AxeOrd<>("y");
		y.add(new AxeValue<Integer>(0));
		y.add(new AxeValue<Integer>(1));
		y.add(new AxeValue<Integer>(2));
		y.add(new AxeValue<Integer>(3));
		y.add(new AxeValue<Integer>(4));
		y.add(new AxeValue<Integer>(5));
		y.add(new AxeValue<Integer>(6));
		y.add(new AxeValue<Integer>(7));
		
		AxeInt xLoc = new AxeInt("x", 0);
		AxeInt yLoc = new AxeInt("y", 0);
		ArrayXDOrd mirrorAxes = new MapXDWithEmptyValueGenericImpl<>(Integer.class, 0, x, y);
		
		ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> world = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArray2DFloat(x, y);
		
		
		
		ArrayXDOrd transformLoc = new Array2DMatrix4fImpl(xLoc, yLoc);
		transformLoc.setValue(1f, 0,0);
		transformLoc.setValue(0f, 0,1);
		transformLoc.setValue(0f, 0,2);
		transformLoc.setValue(0f, 0,3);
		transformLoc.setValue(0f, 1,0);
		transformLoc.setValue(0f, 1,1);
		transformLoc.setValue(0f, 1,2);
		transformLoc.setValue(1f, 1,3);
		transformLoc.setValue(1f, 2,0);
		transformLoc.setValue(1f, 2,1);
		transformLoc.setValue(1f, 2,2);
		transformLoc.setValue(0f, 2,3);
		transformLoc.setValue(0f, 3,0);
		transformLoc.setValue(0f, 3,1);
		transformLoc.setValue(0f, 3,2);
		transformLoc.setValue(0f, 3,3);
		
		float[] expecteds = new float[] {0f, 0f, 0f};
		
		CoordinatesXDSpaceByIndices coord = new CoordinatesXDSpaceByIndices(world, transformLoc);
		mirrorAxes.addCoordinate(coord);
		mirrorAxes.setValue(1f, 0, 0);
		
		assertEquals(1f, mirrorAxes.getValue(0, 0));
		assertEquals(1f, world.getValueWithInclusionOfArrayChilds(1, 0));
		
		
		{float[] newCoordExpected = new float[] {1f, 0f, 0f};
		float[] newInvCoord = coord.transformByIndices(newCoordExpected);
		Assert.assertArrayEquals(expecteds, newInvCoord, 0.001f);
		float[] newCoord = coord.transformInvByIndices(expecteds);
		Assert.assertArrayEquals(newCoordExpected, newCoord, 0.001f);
		System.out.println(Arrays.toString(newInvCoord));
		System.out.println(Arrays.toString(newCoord));
		}
		
		coord.rotate(90f, 0f, 0f, 1f);
		
		{float[] newCoordExpected = new float[] {0f, 0.99999994f, 0f};
		float[] newInvCoord = coord.transformByIndices(newCoordExpected);
		Assert.assertArrayEquals(expecteds, newInvCoord, 0.001f);
		float[] newCoord = coord.transformInvByIndices(expecteds);
		Assert.assertArrayEquals(newCoordExpected, newCoord, 0.001f);
		System.out.println(Arrays.toString(newInvCoord));
		System.out.println(Arrays.toString(newCoord));
		}
		
		assertEquals(1f, mirrorAxes.getValue(0, 0));
		assertEquals(1f, world.getValueWithInclusionOfArrayChildsByIndices(0f, 0.99999994f));
		
		
		coord.rotate(180f, 1f, 0f, 0f);
		{float[] newCoordExpected = new float[] {0f, 0.99999994f, 0.00000008742278f};
		float[] newInvCoord = coord.transformByIndices(newCoordExpected);
		Assert.assertArrayEquals(expecteds, newInvCoord, 0.001f);
		float[] newCoord = coord.transformInvByIndices(expecteds);
		Assert.assertArrayEquals(newCoordExpected, newCoord, 0.001f);
		System.out.println(Arrays.toString(newInvCoord));
		System.out.println(Arrays.toString(newCoord));
		}
		
		assertEquals(1f, mirrorAxes.getValue(0, 0));
		assertEquals(1f, world.getValueWithInclusionOfArrayChildsByIndices(0f, 0.99999994f));
		
		
	}

	
	public void testInverseAxe() {
		
		List<Axe<AxeValue<String>>> domains = new ArrayList<>();
		
		AxeOrd<AxeValue<String>> domX = new AxeOrd<>("x");
		domX.add(new AxeValue<String>("1"));
		domX.add(new AxeValue<String>("2"));
		domX.add(new AxeValue<String>("3"));
		domX.add(new AxeValue<String>("4"));
		domX.add(new AxeValue<String>("5"));
		domains.add(domX);
		
		AxeOrd<AxeValue<String>> domY = new AxeOrd<>("y");
		domY.add(new AxeValue<String>("a"));
		domY.add(new AxeValue<String>("b"));
		domY.add(new AxeValue<String>("c"));
		domY.add(new AxeValue<String>("d"));
		domY.add(new AxeValue<String>("e"));
		domains.add(domY);
		
		ArrayXDOrd<String, String, Axe<AxeValue<String>>> c = new ArrayXDWithEmptyValueGenericImpl<>(String.class,
					"", domX, domY);
		
		c.setValue("1a", "1", "a");
		c.setValue("4d", "4", "d");
		c.setValue("4c", "4", "c");
		c.setValue("3d", "3", "d");
		
		ArrayXDOrd<String, String, Axe<AxeValue<String>>> cInv = ReferenceUtils.inverseAxe2D(String.class, c);
		Assert.assertEquals("1a", c.getValueByIndices(0,0));
		Assert.assertEquals("1a", c.getValueFromChildAxeCoordByIndices(cInv, 0,0));
		Assert.assertEquals("4d", c.getValueByIndices(3,3));
		Assert.assertEquals("4d", c.getValueFromChildAxeCoordByIndices(cInv, 3,3));
		Assert.assertEquals("3d", c.getValueByIndices(2,3));
		Assert.assertEquals("4c", c.getValueFromChildAxeCoordByIndices(cInv, 2,3));
	}
	
	
	public void testAddAxe() {
		Axe<AxeValue<Integer>> domNeuroneX = new AxeOrd<AxeValue<Integer>>("x");
		for (int i = 0; i < 10; i++) {
			domNeuroneX.add(new AxeValue<Integer>(i));
		}
		Axe<AxeValue<Integer>> domNeuroneY = new AxeOrd<AxeValue<Integer>>("y");
		for (int i = 0; i < 10; i++) {
			domNeuroneY.add(new AxeValue<Integer>(i));
		}
		Axe<AxeValue<Integer>> domNeuroneZ = new AxeOrd<AxeValue<Integer>>("z");
		for (int i = 0; i < 10; i++) {
			domNeuroneZ.add(new AxeValue<Integer>(i));
		}
		ArrayXDOrd axes = new MapXDWithEmptyValueGenericImpl<>(Integer.class, 0, domNeuroneX, domNeuroneY);
		axes.setValue(3, 0, 0);
		ArrayXDOrd newRef = axes.addAxe(domNeuroneZ);
		Assert.assertEquals(3, newRef.getValue(0, 0, 0));
	}
}
