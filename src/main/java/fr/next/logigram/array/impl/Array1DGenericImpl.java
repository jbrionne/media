package fr.next.logigram.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeVal;
import fr.next.logigram.array.CoordinatesXDByIndices;

public class Array1DGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private T[] cases;

	private G domainLine;

	private CoordinatesXDByIndices coordinates;

	@SuppressWarnings("unchecked")
	Array1DGenericImpl(Class<T> clazz, G domainLine2, CoordinatesXDByIndices coordinates) {
		this.domainLine = domainLine2;
		this.coordinates = coordinates;
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
		return coordinates;
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


}
