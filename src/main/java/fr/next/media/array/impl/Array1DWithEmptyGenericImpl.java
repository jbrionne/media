package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXDByIndices;

public class Array1DWithEmptyGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private T[] cases;

	private G domainLine;

	private CoordinatesXDByIndices coordinates;
	
	private T emptyVal;

	@SuppressWarnings("unchecked")
	Array1DWithEmptyGenericImpl(Class<T> clazz, G domainLine2, CoordinatesXDByIndices coordinates, T emptyVal) {
		this.domainLine = domainLine2;
		this.coordinates = coordinates;
		this.emptyVal = emptyVal;
		cases = (T[]) Array.newInstance(clazz, domainLine2.getElements().size());
		for (int i = 0; i < cases.length; i++) {
			cases[i] = emptyVal;
		}
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
	public T getValueByIndices(int... indexAxeLines) {
		if(indexAxeLines.length != 1) {
			throw new AssertionError();
		}
		int indexAxeLine = indexAxeLines[0];
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
