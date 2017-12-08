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

public class ArrayXDWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrdDomains<T, K, G> implements ArrayXDOrd<T, K, G> {

	private Object cases;

	private Class<T> clazz;

	private T emptyVal;

	@SuppressWarnings("unchecked")
	public ArrayXDWithEmptyValueGenericImpl(Class<T> clazz, T emptyVal,
			G... domains) {
		this.clazz = clazz;
		this.domains = domains;
		this.emptyVal = emptyVal;
		int[] sizes = new int[domains.length];
		int index = 0;
		for (G d : domains) {
			sizes[index] = d.size();
			index++;
		}
		cases = Array.newInstance(clazz, sizes);

		Object o = cases;
		recEmpty(o, 0);
	}

	private void recEmpty(Object o, int index) {
		for (int k = 0; k < Array.getLength(o); k++) {
			Object i = Array.get(o, k);
			if(index == domains.length - 2) {
				for (int x = 0; x < Array.getLength(i); x++) {
					((T[]) i)[x] = emptyVal;
				}
			} else {
				int dim = index + 1;
				recEmpty(i, dim);
			}
		}
	}
	
	@Override
	public void setValue(T value, K... values) {
		float[] indices = new float[values.length];
		int i = 0;
		for (G d : domains) {
			indices[i] = Axe.findIndex(values[i], d);
			i++;
		}

		setValueInternal(value, indices);
	}

	private T getValueInternal(float... indices) {
		Object o = cases;
		boolean monoDim = false;
		int i = 0;
		while (!monoDim) {
			o = Array.get(o, convertFloatToInt(indices[i]));
			if(i == domains.length - 2) {
				monoDim = true;
				o = ((T[]) o)[convertFloatToInt(indices[i + 1])];
			}
			i++;
		}
		if (o != null) {
			return (T) o;
		} else {
			return null;
		}
	}

	private void setValueInternal(T value, float... indices) {
		Object o = cases;
		boolean monoDim = false;
		int i = 0;
		while (!monoDim) {
			o = Array.get(o, convertFloatToInt(indices[i]));
			if(i == domains.length - 2) {
				monoDim = true;
				((T[]) o)[convertFloatToInt(indices[i + 1])] = value;
			}
			i++;
		}
	}

	@Override
	public void setValueByIndices(T value, float... indices) {
		setValueInternal(value, indices);
	}

	@Override
	public T getValueByIndices(float... indices) {
		return getValueInternal(indices);
	}

	@Override
	public T getValue(K... values) {
		float[] indices = new float[values.length];
		int i = 0;
		for (G d : domains) {
			indices[i] = (float) Axe.findIndex(values[i], d);
			i++;
		}
		return getValueInternal(indices);
	}

	@Override
	public List<T> getValuesForAnAxe(int indexAxe, float indexToFind) {
		List<T> all = new ArrayList<>();
		int[] indices = new int[domains.length];
		recWithIndex(all, cases, indices, 0, indexAxe, convertFloatToInt(indexToFind));
		return all;
	}
	
	@Override
	public void setValuesForAnAxe(int indexAxe, T... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> getAll() {
		List<T> all = new ArrayList<>();
		rec(all, cases, 0);
		return all;
	}
	
	@Override
	public List<Pair<List<K>, T>> getAllWithKey() {
		List<Pair<List<K>, T>> pair = new ArrayList<>();
		List<K> keys = new ArrayList<>();
		recWithKeys(pair, keys, cases, 0);
		return pair;
	}

	private void recWithIndex(List<T> all, Object o, int[] indices, int index, int indexAxe, int indexToFind) {
		for (int k = 0; k < Array.getLength(o); k++) {
			indices[index] = k;
			Object i = Array.get(o, k);
			if (index  == domains.length - 2) {
				for (int x = 0; x < Array.getLength(i); x++) {
					indices[index + 1] = x;
					if (indices[indexAxe] == indexToFind) {
						Object z = Array.get(i, x);
						if (z != null) {
							all.add((T) z);
						}
					}
				}
			} else {
				
				int dim = index + 1;
				recWithIndex(all, i, indices, dim, indexAxe, indexToFind);
			}
		}
	}
	
	private void recWithIndexWithKey(List<Pair<K, T>> all, Object o, int[] indices, int index, int indexAxe, int indexToFind) {
		for (int k = 0; k < Array.getLength(o); k++) {
			indices[index] = k;
			Object i = Array.get(o, k);
			if (index  == domains.length - 2) {
				for (int x = 0; x < Array.getLength(i); x++) {
					indices[index + 1] = x;
					if (indices[indexAxe] == indexToFind) {
						Object z = Array.get(i, x);
						if (z != null) {
							all.add(new ImmutablePair<K, T>((K) domains[indexAxe].getElements().get(x).getValue(), (T) z));
						}
					}
				}
			} else {
				int dim = index + 1;
				recWithIndexWithKey(all, i, indices, dim, indexAxe, indexToFind);
			}
		}
	}

	private void rec(List<T> all, Object o, int index) {
		for (int k = 0; k < Array.getLength(o); k++) {
			Object i = Array.get(o, k);
			if (index  == domains.length - 2) {
				for (int x = 0; x < Array.getLength(i); x++) {
					Object z = Array.get(i, x);
					if (z != null) {
						all.add((T) z);
					}
				}
			} else {
				int dim = index + 1;
				rec(all, i, dim);
			}
		}
	}
	
	
	private void recWithKeys(List<Pair<List<K>, T>> all, List<K> keys, Object o, int index) {
		for (int k = 0; k < Array.getLength(o); k++) {
			Object i = Array.get(o, k);
			K m = domains[index].getElements().get(k).getValue();
			List newIndexKey = new ArrayList<>(keys);
			newIndexKey.add(m);
			if (index  == domains.length - 2) {
				for (int x = 0; x < Array.getLength(i); x++) {
					Object z = Array.get(i, x);
					List newKey = new ArrayList<>(newIndexKey);
					K mx = domains[index].getElements().get(x).getValue();
					newKey.add(mx);
					if (z != null) {
						all.add(new ImmutablePair<List<K>, T>(new ArrayList<>(newKey), (T) z));
					}
				}
			} else {
				int dim = index + 1;
				recWithKeys(all, new ArrayList<>(keys), i, dim);
			}
		}
	}
	
	@Override
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, float indexToFind) {
		List<Pair<K, T>> all = new ArrayList<>();
		Object o = cases;
		int[] indices = new int[domains.length];
		recWithIndexWithKey(all, o, indices, 0, indexAxe, convertFloatToInt(indexToFind));
		return all;
	}
	
}
