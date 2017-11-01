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

public class ArrayXDGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private Object cases;

	private G[] domains;

	private CoordinatesXDByIndices coordinates;

	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public ArrayXDGenericImpl(Class<T> clazz, CoordinatesXDByIndices coordinates, G... domains) {
		this.clazz = clazz;
		this.domains = domains;
		this.coordinates = coordinates;
		int[] sizes = new int[domains.length];
		int index = 0;
		for (G d : domains) {
			sizes[index] = d.size();
			index++;
		}
		cases = Array.newInstance(clazz, sizes);
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
			if(i == domains.length - 2) {
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
			if(i == domains.length - 2) {
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
		int[] indices = new int[domains.length];
		recWithIndex(all, cases, indices, 0, indexAxe, indexToFind);
		return all;
	}

	@Override
	public List<T> getAll() {
		List<T> all = new ArrayList<>();
		rec(all, cases, 0);
		return all;
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
		List<Pair<K, T>> all = new ArrayList<>();
		Object o = cases;
		int[] indices = new int[domains.length];
		recWithIndexWithKey(all, o, indices, 0, indexAxe, indexToFind);
		return all;
	}
	
	@Override
	public T getValueFromUpperAxeCoord(K... upperAxeIndices) {
		if(coordinates.getAxesSize() < domains.length) {
			throw new AssertionError("Not compatible axes : upper reference should have at least the same number of axes");
		}
		
		int[] indices = new int[domains.length];
		for(int i = 0; i < coordinates.getAxesSize(); i++) {
			boolean found = false;
			int j = 0;
			for (G d : domains) {
				if(d.getName().equals(coordinates.getAxe(i).getName())) {
					found = true;
					if(upperAxeIndices[i] instanceof CoordOperation) {
						indices[j] = Axe.findIndex(((CoordOperation<K>) upperAxeIndices[i])
								.sub((K) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i))), d);
					} else if(upperAxeIndices[i] instanceof Integer) {
						indices[j] = Axe.findIndex((Integer) upperAxeIndices[i] - (Integer) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)).getValue(), d);
					}
					break;
				}
				j++;
			}
			if(!found) {
				throw new AssertionError("Not compatible axes : unable to find " + coordinates.getAxe(i).getName());
			}
		}
		return getValueByIndices(indices);
	}
}
