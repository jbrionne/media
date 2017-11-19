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

public class Array1DWithEmptyGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>>  extends AbstractArrayXDOrdDomains<T, K, G> implements ArrayXDOrd<T, K, G> {

	private T[] cases;

	private G domainLine;

	private T emptyVal;

	@SuppressWarnings("unchecked")
	Array1DWithEmptyGenericImpl(Class<T> clazz, G domainLine2, T emptyVal) {
		this.domainLine = domainLine2;
		this.domains = (G[]) Array.newInstance(domainLine2.getClass(), 1);
		domains[0] = domainLine;
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
	public List<T> getValuesForAnAxe(int indexAxe, int indexToFind) {
		return Arrays.asList(cases);
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
