package fr.next.media.array.impl;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeFunctions;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeVal;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
import fr.next.media.memory.Memory;

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

	public static AxeOrd<AxeValue<AxeValue<Character>>> mot(String m) {
		AxeOrd<AxeValue<AxeValue<Character>>> mot = new AxeOrd<AxeValue<AxeValue<Character>>>(m);
		for (int i = 0; i < m.length(); i++) {
			char c = m.charAt(i);
			AxeValue<Character> val = null;
			for (AxeValue<Axe<AxeValue<Character>>> k : AxeFunctions.listGen(alphaMini(), alphaMaj(), num()).getElements()) {
				for(AxeValue<Character> o : k.getValue().getElements()) {
					if (o.getValue().charValue() == c) {
						val = (AxeValue<Character>) o;
						break;
					}
				}
			}
			if(val == null) {
				throw new AssertionError("Unable to find " + c);
			}
			mot.add(new AxeValue<AxeValue<Character>>(val));
		}
		return mot;
	}
	
	public static AxeOrd<AxeValue<Character>> num() {
		String id = Memory.NUM_ID;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<Character>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<Character>>(id);
			alpha.add(new AxeValue<Character>('0'));
			alpha.add(new AxeValue<Character>('1'));
			alpha.add(new AxeValue<Character>('2'));
			alpha.add(new AxeValue<Character>('3'));
			alpha.add(new AxeValue<Character>('4'));
			alpha.add(new AxeValue<Character>('5'));
			alpha.add(new AxeValue<Character>('6'));
			alpha.add(new AxeValue<Character>('7'));
			alpha.add(new AxeValue<Character>('8'));
			alpha.add(new AxeValue<Character>('9'));
			memory.save(id, alpha);
		} else {
			alpha = (AxeOrd<AxeValue<Character>>) a;
		}
		return alpha;
	}

	public static AxeOrd<AxeValue<Character>> alphaMini() {
		String id = Memory.ALPHA_MIN_ID;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<Character>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<Character>>(id);
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
			memory.save(id, alpha);
		} else {
			alpha = (AxeOrd<AxeValue<Character>>) a;
		}
		return alpha;
	}
	
	public static AxeOrd<AxeValue<Character>> alphaMaj() {
		String id = Memory.ALPHA_MAJ_ID;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<Character>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<Character>>(id);
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
			memory.save(id, alpha);
		} else {
			alpha = (AxeOrd<AxeValue<Character>>) a;
		}
		return alpha;
	}
}
