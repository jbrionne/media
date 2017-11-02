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

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValue(
			G domainLine, G domainCol) {
		return new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, ArrayLogigramValue.EMPTY,
				domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueX(
			G domainLine, G domainCol) {
		return new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, ArrayLogigramValue.EMPTY,
				domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValue3(
			G domainLine, G domainCol, G domainZ) {
		return new Array3DWithEmptyValueGenericImpl(String.class, domainLine, domainCol, domainZ, "");
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueX3Integer(
			G domainLine, G domainCol, G domainZ) {
		return new ArrayXDWithEmptyValueGenericImpl(String.class, "", domainLine, domainCol, domainZ);
	}
	
	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueMapX3Integer(
			G domainLine, G domainCol, G domainZ) {
		return new MapXDWithEmptyValueGenericImpl(String.class, "", domainLine, domainCol, domainZ);
	}
	
	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueMapX2IntegerWithFloat(
			G domainLine, G domainCol) {
		return  new MapXDWithEmptyValueGenericImpl(Float.class, "", domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueX2Integer(
			G domainLine, G domainCol) {
		return new ArrayXDWithEmptyValueGenericImpl(String.class, "", domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceStringValue(G domainLine,
			G domainCol) {
		ArrayXDGenericImpl a = new ArrayXDGenericImpl(String.class, domainLine, domainCol);
		return new Array2DGenericImpl(a.getClass(), domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueForWorld(
			G domainLine, G domainCol) {
		ArrayXDWithEmptyValueGenericImpl a = new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class,
				ArrayLogigramValue.EMPTY, domainLine, domainCol);
		return new Array2DGenericImpl(a.getClass(), domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceFloatMatrice3f(G domainLine,
			G domainCol) {
		return new Array2DMatrix3fImpl<>(domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceFloatMatrice4f(G domainLine,
			G domainCol) {
		return new Array2DMatrix4fImpl<>(domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArray2DFloat(G domainLine,
			G domainCol) {
		return new Array2DGenericImpl<>(Float.class, domainLine, domainCol);
	}

	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceString(G[] domainLettre) {
		return new ArrayXDGenericImpl<>(String.class, domainLettre);
	}
	
}
