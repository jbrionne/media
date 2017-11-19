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

public class Array2DGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>>  extends AbstractArrayXDOrdDomains<T, K, G> implements ArrayXDOrd<T, K, G> {

	private T[][] cases;

	private G domainLine;
	private G domainCol;

	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public Array2DGenericImpl(Class<T> clazz, G domainLine2, G domainCol2) {
		this.clazz = clazz;
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.domains = (G[]) Array.newInstance(domainLine2.getClass(), 2);
		domains[0] = domainLine;
		domains[1] = domainCol;
		cases = (T[][]) Array.newInstance(clazz, domainLine2.size(), domainCol2.size());
	}

	@Override
	public void setValue(T value, K... valueAxe) {
		if (valueAxe.length != 2) {
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
		if (indices.length != 2) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
		int indexAxeCol = indices[1];

		cases[indexAxeLine][indexAxeCol] = value;
	}

	@Override
	public T getValueByIndices(int... indices) {
		if (indices.length != 2) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
		int indexAxeCol = indices[1];
		return cases[indexAxeLine][indexAxeCol];
	}

	@Override
	public T getValue(K... valueAxe) {
		if (valueAxe.length != 2) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxe[0];
		K valueAxeCol = valueAxe[1];
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		int indexCol = Axe.findIndex(valueAxeCol, domainCol);
		return cases[indexLine][indexCol];
	}

	@Override
	public List<T> getValuesForAnAxe(int indexAxe, int indexToFind) {
		if (indexAxe == 0) {
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
		for (T[] a : cases) {
			for (T k : a) {
				if (k != null) {
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
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, int indexToFind) {
		List<Pair<K, T>> pair = new ArrayList<>();
		List<T> values = null;
		G domains = null;
		if (indexAxe == 0) {
			values = Arrays.asList(getLine(indexToFind));
			domains = domainLine;
		} else {
			values = Arrays.asList(getCol(indexToFind));
			domains = domainCol;
		}
		int index = 0;
		for (T c : values) {
			K d = domains.getElements().get(index).getValue();
			pair.add(new ImmutablePair<K, T>(d, c));
			index++;
		}
		return pair;
	}
	
	
}
