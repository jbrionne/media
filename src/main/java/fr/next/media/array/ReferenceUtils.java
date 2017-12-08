package fr.next.media.array;

import fr.next.media.array.impl.Array2DGenericImpl;
import fr.next.media.array.impl.Array2DMatrix4fImpl;

public class ReferenceUtils {
	
	public static <T> ArrayXDOrd<T, String, Axe<AxeValue<String>>> inverseAxe2D(Class<T> clazz, ArrayXDOrd<T, String, Axe<AxeValue<String>>> axes) {
		AxeInt xLoc = new AxeInt("x", 0);
		AxeInt yLoc = new AxeInt("y", 0);
		ArrayXDOrd transformLoc = new Array2DMatrix4fImpl(xLoc, yLoc);
		transformLoc.setValue(0f, 0,0);
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
		ArrayXDOrd mirror = new Array2DGenericImpl<T, String, Axe<AxeValue<String>>>(clazz, axes.getAxe(0), axes.getAxe(1));
		CoordinatesXDByIndices coord = new CoordinatesXDByIndices(axes, transformLoc);
		mirror.addCoordinate(coord);
		coord.rotate(90f, 0f, 0f, 1f);
		coord.rotate(180f, 1f, 0f, 0f);
		return mirror;
	}
}
