package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordOperation;
import fr.next.media.array.CoordinatesXDByIndices;

public class Array1DGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private T[] cases;

	private G domainLine;

	private List<CoordinatesXDByIndices<T, K, G>> coordinates = new ArrayList<>();
	private List<CoordinatesXDByIndices<T, K, G>> childCoordinates = new ArrayList<>();

	@SuppressWarnings("unchecked")
	Array1DGenericImpl(Class<T> clazz, G domainLine2) {
		this.domainLine = domainLine2;
		cases = (T[]) Array.newInstance(clazz, domainLine2.getElements().size());
	}
 
	@Override
	public void setValue(T value, K... valueAxeLines) {
		if(valueAxeLines.length != 1) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxeLines[0];
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		cases[indexLine] = value;
	}

	@Override
	public void setValueByIndices(T value, int... indexAxeLines) {
		if(indexAxeLines.length != 1) {
			throw new AssertionError();
		}
		int indexAxeLine = indexAxeLines[0];
		cases[indexAxeLine] = value;
	}

	@Override
	public T getValueByIndices(int... indices) {
		if(indices.length != 1) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
		return cases[indexAxeLine];
	}

	@Override
	public T getValue(K... valueAxeLines) {
		if(valueAxeLines.length != 1) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxeLines[0];
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		return cases[indexLine];
	}

	@Override
	public G getAxe(int index) {
		return domainLine;
	}
	
	@Override
	public List<G> getAxes() {
		List<G> a = new ArrayList<>();
		a.add(domainLine);
		return a;
	}

	@Override
	public List<T> getAll() {
		List<T> all = new ArrayList<>();
		for (T k : cases) {
			if (k != null) {
				all.add(k);
			}
		}
		return all;
	}
	
	@Override
	public CoordinatesXDByIndices getCoordinates() {
		if(coordinates.size() == 1) {
			return coordinates.get(0);
		}
		throw new AssertionError("Multiple upper coordinates, use getCoordinates(axes)");
	}

	@Override
	public CoordinatesXDByIndices<T, K, G>  getCoordinates(ArrayXDOrd<T, K, G> axes) {
		for(CoordinatesXDByIndices<T, K, G>  c : coordinates) {
			boolean found = false;
			for(int i = 0; i < c.getAxesSize(); i++) {
				for(G a : axes.getAxes()) {
					if(c.getAxe(i).equals(a)) {
						found = true;
						break;
					}
				}
			}
			if(found) {
				return c;
			}
		}
		throw new AssertionError("No coordinates were found");
	}

	@Override
	public List<T> getValuesForAnAxe(int indexAxe, int indexToFind) {
		return Arrays.asList(cases);
	}

	@Override
	public void setTranslation(Class<T> clazzT, T... values) {
		throw new IllegalMethod();
	}

	@Override
	public void setRotationQuaternion(Class<T> clazzT, T w, T... values) {
		throw new IllegalMethod();
	}

	@Override
	public void setScale(Class<T> clazzT, T... values) {
		throw new IllegalMethod();
	}

	@Override
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, int indexToFind) {
		List<Pair<K, T>> pair = new ArrayList<>();
		int index = 0;
		for(T c : cases) {
			K d =  domainLine.getElements().get(index).getValue();
			pair.add(new ImmutablePair<K, T>(d, c));
			index++;
		}
		return pair;
	}

	@Override
	public T getValueFromUpperAxeCoord(ArrayXDOrd<T, K, G> axes, K... upperAxeIndices) {
		CoordinatesXDByIndices<T, K, G>  coordinates = getCoordinates(axes);
		if (coordinates.getAxesSize() < 1) {
			throw new AssertionError(
					"Not compatible axes : upper reference should have at least the same number of axes");
		}
		for (int i = 0; i < coordinates.getAxesSize(); i++) {
			boolean found = false;
			if (domainLine.getName().equals(coordinates.getAxe(i).getName())) {
				found = true;
			} 
			if (!found) {
				throw new AssertionError("Not compatible axes : unable to find " + coordinates.getAxe(i).getName());
			}
		}
		
		return getValue(coordinates.transform(upperAxeIndices));
	}

	@Override
	public void addCoordinate(CoordinatesXDByIndices<T, K, G> coordinates) {
		this.coordinates.add(coordinates);
		coordinates.getAxes().addChildCoordinate(new CoordinatesXDByIndices<>(this, coordinates.getTransform()));
	}

	public List<CoordinatesXDByIndices<T, K, G>> getChildCoordinates() {
		return childCoordinates;
	}

	@Override
	public void addChildCoordinate(CoordinatesXDByIndices<T, K, G> coordinates) {
		this.childCoordinates.add(coordinates);
		coordinates.getAxes().addChildCoordinate(coordinates);
	}

	@Override
	public void mergeChildren() {
		for(CoordinatesXDByIndices<T, K, G> c : childCoordinates) {
			c.getAxes().mergeChildren();
			List<Pair<List<K>,T>> val = c.getAxes().getAllWithKey();
			for(Pair<List<K>,T> p : val) {
				K[] m = p.getKey().toArray((K[])Array.newInstance(p.getKey().get(0).getClass(), p.getKey().size()));
				setValue(p.getValue(), c.transformInv(m));
			}
		}
	}

	@Override
	public List<Pair<List<K>, T>> getAllWithKey() {
		List<Pair<List<K>, T>> pair = new ArrayList<>();
		int index = 0;
		for(T c : cases) {
			K d =  domainLine.getElements().get(index).getValue();
			pair.add(new ImmutablePair<List<K>, T>(Collections.singletonList(d), c));
			index++;
		}
		return pair;
	}
}
