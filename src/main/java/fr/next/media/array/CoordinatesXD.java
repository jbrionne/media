package fr.next.media.array;

import java.util.List;

public interface CoordinatesXD<T, K, G extends Axe<? extends AxeVal<K>>> {

	G getAxe(int index);

	int getAxesSize();

	ArrayXDOrd<T, K, G> getAxes();
	
	boolean isTransform();
	
	Object getTransform();
	
	List<Float> getPositionIndicesList();
	
	Float getPosition(int index);
	
	float[] transformByIndices(float... b);
	
	 K[] transformInv(K[] b);
	 
	 float[] transformInvByIndices(float... b);
	 
	 K[] transform(K[] b);
}
