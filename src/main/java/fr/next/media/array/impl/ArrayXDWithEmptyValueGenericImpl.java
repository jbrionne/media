package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXDByIndices;

public class ArrayXDWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private Object cases;

	private G[] domains;

	private CoordinatesXDByIndices coordinates;

	private Class<T> clazz;

	private Class<T[]> clazzs;

	private T emptyVal;

	@SuppressWarnings("unchecked")
	public ArrayXDWithEmptyValueGenericImpl(Class<T> clazz, CoordinatesXDByIndices coordinates, T emptyVal,
			G... domains) {
		this.clazz = clazz;
		this.clazzs = (Class<T[]>) Array.newInstance(clazz, 0).getClass();
		this.domains = domains;
		this.coordinates = coordinates;
		this.emptyVal = emptyVal;
		int[] sizes = new int[domains.length];
		int index = 0;
		for (G d : domains) {
			sizes[index] = d.size();
			index++;
		}
		cases = Array.newInstance(clazz, sizes);

		Object o = cases;
		recEmpty(o);
	}

	private void recEmpty(Object o) {
		for (int k = 0; k < Array.getLength(o); k++) {
			Object i = Array.get(o, k);
			if (clazzs.isAssignableFrom(i.getClass())) {
				for (int x = 0; x < Array.getLength(i); x++) {
					((T[]) i)[x] = emptyVal;
				}
			} else {
				recEmpty(i);
			}
		}
	}

	@Override
	public void setValue(T value, K... values) {
		int[] indices = new int[values.length];
		int i = 0;
		for (G d : domains) {
			indices[i] = Axe.findIndex(values[i], d);
			i++;
		}

		setValueInternal(value, indices);
	}

	private T getValueInternal(int... indices) {
		Object o = cases;
		boolean monoDim = false;
		int i = 0;
		while (!monoDim) {
			o = Array.get(o, indices[i]);
			if (clazzs.isAssignableFrom(o.getClass())) {
				monoDim = true;
				o = ((T[]) o)[indices[i + 1]];
			}
			i++;
		}
		if (o != null) {
			return (T) o;
		} else {
			return null;
		}
	}

	private void setValueInternal(T value, int... indices) {
		Object o = cases;
		boolean monoDim = false;
		int i = 0;
		while (!monoDim) {
			o = Array.get(o, indices[i]);
			if (clazzs.isAssignableFrom(o.getClass())) {
				monoDim = true;
				((T[]) o)[indices[i + 1]] = value;
			}
			i++;
		}
	}

	@Override
	public void setValueByIndices(T value, int... indices) {
		setValueInternal(value, indices);
	}

	@Override
	public T getValueByIndices(int... indices) {
		return getValueInternal(indices);
	}

	@Override
	public T getValue(K... values) {
		int[] indices = new int[values.length];
		int i = 0;
		for (G d : domains) {
			indices[i] = Axe.findIndex(values[i], d);
			i++;
		}
		return getValueInternal(indices);
	}

	@Override
	public G getAxe(int index) {
		return domains[index];
	}

	@Override
	public CoordinatesXDByIndices getCoordinates() {
		return coordinates;
	}

	@Override
	public List<T> getValuesForAnAxe(int indexAxe, int indexToFind) {
		List<T> all = new ArrayList<>();
		Object o = cases;
		int[] indices = new int[domains.length];
		recWithIndex(all, o, indices, 0, indexAxe, indexToFind);
		return all;
	}

	@Override
	public List<T> getAll() {
		List<T> all = new ArrayList<>();
		Object o = cases;
		rec(all, o);
		return all;
	}

	private void recWithIndex(List<T> all, Object o, int[] indices, int index, int indexAxe, int indexToFind) {
		for (int k = 0; k < Array.getLength(o); k++) {
			indices[index] = k;
			Object i = Array.get(o, k);
			if (clazzs.isAssignableFrom(i.getClass())) {
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

	private void rec(List<T> all, Object o) {
		for (int k = 0; k < Array.getLength(o); k++) {
			Object i = Array.get(o, k);
			if (clazzs.isAssignableFrom(i.getClass())) {
				for (int x = 0; x < Array.getLength(i); x++) {
					Object z = Array.get(i, x);
					if (z != null) {
						all.add((T) z);
					}
				}
			} else {
				rec(all, i);
			}
		}
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
