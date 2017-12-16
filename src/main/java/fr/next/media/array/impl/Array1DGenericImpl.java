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
import fr.next.media.array.CoordinatesXDByIndices;

public class Array1DGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrdDomains<T, K, G>  implements ArrayXDOrd<T, K, G> {

	private T[] cases;

	private G domainLine;
	
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public Array1DGenericImpl(Class<T> clazz, G domainLine2) {
		this.domainLine = domainLine2;
		this.domains = (G[]) Array.newInstance(domainLine2.getClass(), 1);
		domains[0] = domainLine;
		cases = (T[]) Array.newInstance(clazz, domainLine2.getElements().size());
		this.clazz = clazz;
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
	public void setValueByIndices(T value, float... indexAxeLines) {
		if(indexAxeLines.length != 1) {
			throw new AssertionError();
		}
		float indexAxeLine = indexAxeLines[0];
		cases[convertFloatToInt(indexAxeLine)] = value;
	}

	@Override
	public T getValueByIndices(float... indices) {
		if(indices.length != 1) {
			throw new AssertionError();
		}
		float indexAxeLine = indices[0];
		return cases[convertFloatToInt(indexAxeLine)];
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
	public List<T> getValuesForAnAxe(int indexAxe, float indexToFind) {
		return Arrays.asList(cases);
	}
	
	@Override
	public void setValuesForAnAxe(int indexAxe, T... values) {
		for(int i = 0; i < values.length; i++) {
			cases[i] = values[i];
		}
	}


	@Override
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, float indexToFind) {
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

	@Override
	public ArrayXDOrd<T, K, Axe<? extends AxeVal<K>>> addAxe(G axe) {
		ArrayXDOrd<T, K, Axe<? extends AxeVal<K>>> a = new Array2DGenericImpl<>(clazz, domainLine, axe);
		for(Pair<List<K>, T> p : getAllWithKey()) {
			a.setValue(p.getValue(), p.getKey().get(0), axe.getElements().get(0).getValue());
		}
		return a;
	}

}
