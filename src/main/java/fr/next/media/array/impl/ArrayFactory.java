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
	
	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueMapX3Integer(
			G domainLine, G domainCol, G domainZ, CoordinatesXDByIndices coordinates) {
		return new MapXDWithEmptyValueGenericImpl(String.class, coordinates, "", domainLine, domainCol, domainZ);
	}
	
	public static <K, G extends Axe<? extends AxeVal<K>>> ArrayXDOrd<?, K, G> newInstanceArrayLogigramValueMapX2IntegerWithFloat(
			G domainLine, G domainCol, CoordinatesXDByIndices coordinates) {
		return new MapXDWithEmptyValueGenericImpl(Float.class, coordinates, "", domainLine, domainCol);
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
	
}
