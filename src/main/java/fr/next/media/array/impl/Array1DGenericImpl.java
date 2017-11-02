package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

	private List<CoordinatesXDByIndices> coordinates = new ArrayList<>();

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
	public CoordinatesXDByIndices getCoordinates(ArrayXDOrd<T, K, G> axes) {
		for(CoordinatesXDByIndices c : coordinates) {
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
		CoordinatesXDByIndices coordinates = getCoordinates(axes);
		if (coordinates.getAxesSize() < 1) {
			throw new AssertionError(
					"Not compatible axes : upper reference should have at least the same number of axes");
		}
		Object valueAxeLine = null;
		for (int i = 0; i < coordinates.getAxesSize(); i++) {
			boolean found = false;
			if (domainLine.getName().equals(coordinates.getAxe(i).getName())) {
				found = true;
				if(upperAxeIndices[i] instanceof CoordOperation) {
					valueAxeLine = ((CoordOperation<K>) upperAxeIndices[i])
						.sub((K) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)));
				} else if(upperAxeIndices[i] instanceof Integer) {
					valueAxeLine = (Integer) upperAxeIndices[i] - (Integer) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)).getValue();
						
				}
			} 
			if (!found) {
				throw new AssertionError("Not compatible axes : unable to find " + coordinates.getAxe(i).getName());
			}
		}
		return getValue((K) valueAxeLine);
	}

	@Override
	public void addCoordinate(CoordinatesXDByIndices coordinates) {
		this.coordinates.add(coordinates);
	}
}
