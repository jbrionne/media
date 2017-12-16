package fr.next.media.array;

import java.io.Serializable;
import java.util.List;

public class CoordinatesXDByIndices<T, K, G extends Axe<? extends AxeVal<K>>>
		implements CoordinatesXD<T, K, G>, Serializable {

	private ArrayXDOrd<T, K, G> axes;
	private Object transform;

	public CoordinatesXDByIndices(ArrayXDOrd<T, K, G> axes, Object transform) {
		super();
		this.axes = axes;
		this.transform = transform;
	}

	public G getAxe(int index) {
		return axes.getAxe(index);
	}

	public int getAxesSize() {
		return axes.getAxes().size();
	}

	public ArrayXDOrd<T, K, G> getAxes() {
		return axes;
	}

	@Override
	public Object getTransform() {
		return transform;
	}

	@Override
	public List<Float> getPositionIndicesList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTransform() {
		return false;
	}

	@Override
	public Float getPosition(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float[] transformByIndices(float... b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public K[] transformInv(K[] b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float[] transformInvByIndices(float... b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public K[] transform(K[] b) {
		throw new UnsupportedOperationException();
	}

}
