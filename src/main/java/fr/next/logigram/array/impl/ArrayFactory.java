package fr.next.logigram.array.impl;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeVal;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.CoordinatesXDByIndices;
import fr.next.logigram.array.impl.logigram.ArrayLogigramValue;
import fr.next.logigram.memory.Memory;

public class ArrayFactory {

	private static Memory memory = Memory.getInstance();

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValue(
			G domainLine, G domainCol, CoordinatesXDByIndices coordinates) {
		return new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, coordinates, ArrayLogigramValue.EMPTY,
				domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueX(
			G domainLine, G domainCol, CoordinatesXDByIndices coordinates) {
		return new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, coordinates, ArrayLogigramValue.EMPTY,
				domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValue3(
			G domainLine, G domainCol, G domainZ, CoordinatesXDByIndices coordinates) {
		return new Array3DWithEmptyValueGenericImpl(String.class, domainLine, domainCol, domainZ, coordinates, "");
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueX3Integer(
			G domainLine, G domainCol, G domainZ, CoordinatesXDByIndices coordinates) {
		return new ArrayXDWithEmptyValueGenericImpl(String.class, coordinates, "", domainLine, domainCol, domainZ);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueX2Integer(
			G domainLine, G domainCol, CoordinatesXDByIndices coordinates) {
		return new ArrayXDWithEmptyValueGenericImpl(String.class, coordinates, "", domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceStringValue(G domainLine,
			G domainCol, CoordinatesXDByIndices coordinates) {
		ArrayXDGenericImpl a = new ArrayXDGenericImpl(String.class, coordinates, domainLine, domainCol);
		return new Array2DGenericImpl(a.getClass(), domainLine, domainCol, coordinates);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueForWorld(
			G domainLine, G domainCol, CoordinatesXDByIndices coordinates) {
		ArrayXDWithEmptyValueGenericImpl a = new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, coordinates,
				ArrayLogigramValue.EMPTY, domainLine, domainCol);
		return new Array2DGenericImpl(a.getClass(), domainLine, domainCol, coordinates);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceFloatMatrice3f(G domainLine,
			G domainCol, CoordinatesXDByIndices coordinates) {
		return new Array2DMatrix3fImpl<>(domainLine, domainCol, coordinates);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceFloatMatrice4f(G domainLine,
			G domainCol, CoordinatesXDByIndices coordinates) {
		return new Array2DMatrix4fImpl<>(domainLine, domainCol, coordinates);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArray2DFloat(G domainLine,
			G domainCol, CoordinatesXDByIndices coordinates) {
		return new Array2DGenericImpl<>(Float.class, domainLine, domainCol, coordinates);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceString(G[] domainLettre,
			CoordinatesXDByIndices coordinates) {
		return new ArrayXDGenericImpl<>(String.class, coordinates, domainLettre);
	}

	public static AxeOrd<AxeValue<Character>> mot(String m) {
		AxeOrd<AxeValue<Character>> mot = new AxeOrd<AxeValue<Character>>(m);
		for (int i = 0; i < m.length(); i++) {
			char c = m.charAt(i);
			AxeValue<Character> val = null;
			for (AxeValue<Character> k : alpha().getElements()) {
				if (k.getValue().charValue() == c) {
					val = k;
					break;
				}
			}
			if(val == null) {
				throw new AssertionError("Unable to find " + c);
			}
			mot.add(val);
		}
		return mot;
	}

	public static void link() {
		// TODO
		AxeOrd<AxeValue<String>> link = new AxeOrd<AxeValue<String>>("lien");
		link.add(new AxeValue<String>("left"));
		link.add(new AxeValue<String>("right"));
	}

	public static AxeOrd<AxeValue<Character>> alpha() {
		Object a = memory.findAndGetContent("alpha");
		AxeOrd<AxeValue<Character>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<Character>>("lettre alphabet");
			alpha.add(new AxeValue<Character>('a'));
			alpha.add(new AxeValue<Character>('b'));
			alpha.add(new AxeValue<Character>('c'));
			alpha.add(new AxeValue<Character>('d'));
			alpha.add(new AxeValue<Character>('e'));
			alpha.add(new AxeValue<Character>('f'));
			alpha.add(new AxeValue<Character>('g'));
			alpha.add(new AxeValue<Character>('h'));
			alpha.add(new AxeValue<Character>('i'));
			alpha.add(new AxeValue<Character>('j'));
			alpha.add(new AxeValue<Character>('k'));
			alpha.add(new AxeValue<Character>('l'));
			alpha.add(new AxeValue<Character>('m'));
			alpha.add(new AxeValue<Character>('n'));
			alpha.add(new AxeValue<Character>('o'));
			alpha.add(new AxeValue<Character>('p'));
			alpha.add(new AxeValue<Character>('q'));
			alpha.add(new AxeValue<Character>('r'));
			alpha.add(new AxeValue<Character>('s'));
			alpha.add(new AxeValue<Character>('t'));
			alpha.add(new AxeValue<Character>('u'));
			alpha.add(new AxeValue<Character>('v'));
			alpha.add(new AxeValue<Character>('w'));
			alpha.add(new AxeValue<Character>('x'));
			alpha.add(new AxeValue<Character>('y'));
			alpha.add(new AxeValue<Character>('z'));
			alpha.add(new AxeValue<Character>('A'));
			alpha.add(new AxeValue<Character>('B'));
			alpha.add(new AxeValue<Character>('C'));
			alpha.add(new AxeValue<Character>('D'));
			alpha.add(new AxeValue<Character>('E'));
			alpha.add(new AxeValue<Character>('F'));
			alpha.add(new AxeValue<Character>('G'));
			alpha.add(new AxeValue<Character>('H'));
			alpha.add(new AxeValue<Character>('I'));
			alpha.add(new AxeValue<Character>('J'));
			alpha.add(new AxeValue<Character>('K'));
			alpha.add(new AxeValue<Character>('L'));
			alpha.add(new AxeValue<Character>('M'));
			alpha.add(new AxeValue<Character>('N'));
			alpha.add(new AxeValue<Character>('O'));
			alpha.add(new AxeValue<Character>('P'));
			alpha.add(new AxeValue<Character>('Q'));
			alpha.add(new AxeValue<Character>('R'));
			alpha.add(new AxeValue<Character>('S'));
			alpha.add(new AxeValue<Character>('T'));
			alpha.add(new AxeValue<Character>('U'));
			alpha.add(new AxeValue<Character>('V'));
			alpha.add(new AxeValue<Character>('W'));
			alpha.add(new AxeValue<Character>('X'));
			alpha.add(new AxeValue<Character>('Y'));
			alpha.add(new AxeValue<Character>('Z'));
			memory.save("alpha", alpha);
		} else {
			alpha = (AxeOrd<AxeValue<Character>>) a;
		}
		return alpha;
	}
}
