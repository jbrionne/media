package fr.next.logigram.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeVal;
import fr.next.logigram.array.CoordinatesXDByIndices;

public class Array2DWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G>  {

	private T[][] cases;

	private G domainLine;
	private G domainCol;

	private CoordinatesXDByIndices coordinates;
	
	private T emptyVal;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	Array2DWithEmptyValueGenericImpl(Class<T> clazz, G domainLine2, G domainCol2, CoordinatesXDByIndices coordinates, T emptyVal) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.coordinates = coordinates;
		this.emptyVal = emptyVal;
		this.clazz = clazz;
		cases = (T[][]) Array.newInstance(clazz, domainLine2.size(), domainCol2.size());
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases[i].length; j++) {
				cases[i][j] = emptyVal;
			}
		}
	}

	@Override
	public void setValue(T value, K... valueAxe) {
		
		if(valueAxe.length != 2) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxe[0];
		K valueAxeCol = valueAxe[1];
		
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		int indexCol = Axe.findIndex(valueAxeCol, domainCol);

		cases[indexLine][indexCol] = value;
	}

	@Override
	public void setValueByIndices(T value, int... indices) {
		
		if(indices.length != 2) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
	    int indexAxeCol = indices[1];
	    
		cases[indexAxeLine][indexAxeCol] = value;
	}

	@Override
	public T getValueByIndices(int... indices) {
		if(indices.length != 2) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
	    int indexAxeCol = indices[1];
		return cases[indexAxeLine][indexAxeCol];
	}

	@Override
	public T getValue(K... valueAxe) {
		if(valueAxe.length != 2) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxe[0];
		K valueAxeCol = valueAxe[1];
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		int indexCol = Axe.findIndex(valueAxeCol, domainCol);
		return cases[indexLine][indexCol];
	}

	@Override
	public G getAxe(int index) {
		if(index == 0) {
			return domainLine;
		} else {
			return domainCol;
		}
	}
	
	@Override
	public List<T> getValuesForAnAxe(int indexAxe, int indexToFind) {
		if(indexAxe == 0) {
			return Arrays.asList(getLine(indexToFind));
		} else {
			return Arrays.asList(getCol(indexToFind));
		}
	}
	
	private T[] getLine(int indexAxeLine) {
		return cases[indexAxeLine];
	}

	private T[] getCol(int indexAxeCol) {
		@SuppressWarnings("unchecked")
		T[] caseCol = (T[]) Array.newInstance(clazz, cases.length);
		for (int i = 0; i < cases.length; i++) {
			caseCol[i] = cases[i][indexAxeCol];
		}
		return caseCol;
	}

	@Override
	public List<T> getAll() {
		List<T> all = new ArrayList<>();
		for(T[] a : cases) {
			for(T k : a) {
				if(k != null) {
					all.add(k);
				}
			}
		}
		return all;
	}

	@Override
	public CoordinatesXDByIndices getCoordinates() {
		return coordinates;
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
