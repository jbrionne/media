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

public class Array2DWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrdDomains<T, K, G> implements ArrayXDOrd<T, K, G> {

	private T[][] cases;

	private G domainLine;
	private G domainCol;

	private T emptyVal;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	Array2DWithEmptyValueGenericImpl(Class<T> clazz, G domainLine2, G domainCol2, T emptyVal) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.domains = (G[]) Array.newInstance(domainLine2.getClass(), 2);
		domains[0] = domainLine;
		domains[1] = domainCol;
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
	public void setValueByIndices(T value, float... indices) {
		
		if(indices.length != 2) {
			throw new AssertionError();
		}
		float indexAxeLine = indices[0];
	    float indexAxeCol = indices[1];
	    
		cases[convertFloatToInt(indexAxeLine)][convertFloatToInt(indexAxeCol)] = value;
	}

	@Override
	public T getValueByIndices(float... indices) {
		if(indices.length != 2) {
			throw new AssertionError();
		}
		float indexAxeLine = indices[0];
	    float indexAxeCol = indices[1];
		return cases[convertFloatToInt(indexAxeLine)][convertFloatToInt(indexAxeCol)];
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
	public List<T> getValuesForAnAxe(int indexAxe, float indexToFind) {
		if(indexAxe == 0) {
			return Arrays.asList(getLine(convertFloatToInt(indexToFind)));
		} else {
			return Arrays.asList(getCol(convertFloatToInt(indexToFind)));
		}
	}
	
	@Override
	public void setValuesForAnAxe(int indexAxe, T... values) {
		for(int i = 0; i < values.length; i++) {
			cases[indexAxe][i] = values[i];
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
	public List<Pair<List<K>, T>> getAllWithKey() {
		List<Pair<List<K>, T>> pair = new ArrayList<>();
		int i = 0;
		for (T[] a : cases) {
			int j = 0;
			K x = domainLine.getElements().get(i).getValue();
			for (T k : a) {
				K y = domainCol.getElements().get(j).getValue();
				if (k != null) {
					List<K> keys = new ArrayList<>();
					keys.add(x);
					keys.add(y);
					pair.add(new ImmutablePair<List<K>, T>(keys, k));
				}
				j++;
			}
			i++;
		}
		return pair;
	}


	@Override
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, float indexToFind) {
		List<Pair<K, T>> pair = new ArrayList<>();
		List<T> values = null;
		G domains = null;
		if(indexAxe == 0) {
			values = Arrays.asList(getLine(convertFloatToInt(indexToFind)));
			domains = domainLine;
		} else {
			values = Arrays.asList(getCol(convertFloatToInt(indexToFind)));
			domains = domainCol;
		}
		int index = 0;
		for(T c : values) {
			K d =  domains.getElements().get(index).getValue();
			pair.add(new ImmutablePair<K, T>(d, c));
			index++;
		}
		return pair;
	}
	
	@Override
	public ArrayXDOrd<T, K, Axe<? extends AxeVal<K>>> addAxe(G axe) {
		ArrayXDOrd<T, K, Axe<? extends AxeVal<K>>> a = new Array3DWithEmptyValueGenericImpl<>(clazz, domainLine, domainCol, axe, emptyVal);
		for(Pair<List<K>, T> p : getAllWithKey()) {
			a.setValue(p.getValue(), p.getKey().get(0), p.getKey().get(1), axe.getElements().get(0).getValue());
		}
		return a;
	}
	
}
