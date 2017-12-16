package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXD;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.CoordinatesXDSpaceByIndices;

public abstract class AbstractArrayXDOrd<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private List<CoordinatesXD<T, K, G>> coordinates = new ArrayList<>();
	private List<CoordinatesXD<T, K, G>> childCoordinates = new ArrayList<>();

	@Override
	public void merge() {
		for (CoordinatesXD<T, K, G> c : childCoordinates) {
			if (c.isTransform()) {
				c.getAxes().merge();
				List<Pair<List<K>, T>> val = c.getAxes().getAllWithKey();
				for (Pair<List<K>, T> p : val) {
					K[] m = p.getKey()
							.toArray((K[]) Array.newInstance(p.getKey().get(0).getClass(), p.getKey().size()));
					setValue(p.getValue(), c.transformInv(m));
				}
			}
		}
	}

	@Override
	public void mergeOnlyFirstLevel() {
		for (CoordinatesXD<T, K, G> c : childCoordinates) {
			List<Pair<List<K>, T>> val = c.getAxes().getAllWithKey();
			for (Pair<List<K>, T> p : val) {
				K[] m = p.getKey().toArray((K[]) Array.newInstance(p.getKey().get(0).getClass(), p.getKey().size()));
				setValue(p.getValue(), c.transformInv(m));
			}
		}
	}

	@Override
	public void addCoordinate(CoordinatesXD<T, K, G> coordinates) {
		this.coordinates.add(coordinates);
		if (coordinates instanceof CoordinatesXDSpaceByIndices) {
			CoordinatesXDSpaceByIndices<T, K, G> c = (CoordinatesXDSpaceByIndices<T, K, G>) coordinates;
			coordinates.getAxes().addChildCoordinate(new CoordinatesXDSpaceByIndices<>(this, c.getTransform()));
		} else {
			coordinates.getAxes().addChildCoordinate(new CoordinatesXDByIndices<>(this, coordinates.getTransform()));
		}
	}

	@Override
	public List<CoordinatesXD<T, K, G>> getChildCoordinates() {
		return childCoordinates;
	}

	@Override
	public CoordinatesXD<T, K, G> getChildCoordinates(ArrayXDOrd<T, K, G> axes) {
		for (CoordinatesXD<T, K, G> c : childCoordinates) {
			boolean found = false;
			for (int i = 0; i < c.getAxesSize(); i++) {
				for (G a : axes.getAxes()) {
					if (c.getAxe(i).equals(a)) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				return c;
			}
		}
		throw new AssertionError("No coordinates were found");
	}

	@Override
	public void addChildCoordinate(CoordinatesXD<T, K, G> coordinates) {
		this.childCoordinates.add(coordinates);
	}

	@Override
	public CoordinatesXD<T, K, G> getSingleCoordinates() {
		if (coordinates.size() == 1) {
			return coordinates.get(0);
		}
		throw new AssertionError("Multiple upper coordinates, use getCoordinates(axes)");
	}

	@Override
	public CoordinatesXD<T, K, G> getCoordinates(ArrayXDOrd<T, K, G> axes) {
		for (CoordinatesXD<T, K, G> c : coordinates) {
			boolean found = false;
			for (int i = 0; i < c.getAxesSize(); i++) {
				for (G a : axes.getAxes()) {
					if (c.getAxe(i).equals(a)) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				return c;
			}
		}
		throw new AssertionError("No coordinates were found");
	}

	@Override
	public void setValueByIndices(T value, CoordinatesXD<T, K, G> coord) {
		if (!coord.getAxes().equals(this)) {
			throw new IllegalArgumentException("coordinate must be for this axe");
		}
		float[] newIndicesValue = new float[coord.getPositionIndicesList().size() - 1];
		for (int i = 0; i < newIndicesValue.length; i++) {
			newIndicesValue[i] = coord.getPositionIndicesList().get(i).intValue();
		}
		this.setValueByIndices(value, newIndicesValue);
	}

	@Override
	public T getValueByIndices(CoordinatesXD<T, K, G> coord) {
		if (!coord.getAxes().equals(this)) {
			throw new IllegalArgumentException("coordinate must be for this axe else use getValueFromUpperAxeCoord ");
		}
		float[] newIndicesValue = new float[coord.getPositionIndicesList().size() - 1];
		for (int i = 0; i < newIndicesValue.length; i++) {
			newIndicesValue[i] = coord.getPositionIndicesList().get(i).floatValue();
		}
		return this.getValueByIndices(newIndicesValue);
	}

	@Override
	public T getValueWithInclusionOfArrayChilds(K... values) {
		T val = getValue(values);
		if (val == null) {// val empty ?!
			float[] upperAxeIndices = valuesToIndices(values);
			for (CoordinatesXD<T, K, G> coord : this.getChildCoordinates()) {
				if (coord.isTransform()) {
					float[] c = coord.transformByIndices(upperAxeIndices);
					if (coord.getAxes().isInBoundariesByIndices(c)) {
						T valChild = coord.getAxes().getValueByIndices(c);
						if (valChild != null) {
							return valChild;
						}
					}
				}
			}
		}
		return val;
	}

	@Override
	public T getValueWithInclusionOfArrayChildsByIndices(float... indices) {
		T val = null;
		if (isInBoundariesByIndices(indices)) {
			val = getValueByIndices(indices);
		}
		if (val == null) {// val empty ?!
			for (CoordinatesXD<T, K, G> coord : this.getChildCoordinates()) {
				if (coord.isTransform()) {
					float[] c = coord.transformByIndices(indices);
					if (coord.getAxes().isInBoundariesByIndices(c)) {
						T valChild = coord.getAxes().getValueByIndices(c);
						if (valChild != null) {
							return valChild;
						}
					}
				}
			}
		}
		return val;
	}

	@Override
	public List<CoordinatesXD<T, K, G>> getCoordinates() {
		return coordinates;
	}

}
