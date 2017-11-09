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

public class ArrayXDWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private Object cases;

	private G[] domains;

	private List<CoordinatesXDByIndices<T, K, G>> coordinates = new ArrayList<>();
	private List<CoordinatesXDByIndices<T, K, G>> childCoordinates = new ArrayList<>();

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
	public CoordinatesXDByIndices<T, K, G> getCoordinates(ArrayXDOrd<T, K, G> axes) {
		for(CoordinatesXDByIndices<T, K, G> c : coordinates) {
			boolean found = false;
			for(int i = 0; i < c.getAxesSize(); i++) {
				for(G a : axes.getAxes()) {
					if(c.getAxe(i).equals(a)) {
						found = true;
						break;
					}
				}
			}
			if(found) {
				return c;
			}
		}
		throw new AssertionError("No coordinates were found");
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
			keys.add(m);
			if (index  == domains.length - 2) {
				for (int x = 0; x < Array.getLength(i); x++) {
					Object z = Array.get(i, x);
					if (z != null) {
						all.add(new ImmutablePair<List<K>, T>(new ArrayList<>(keys), (T) z));
					}
				}
			} else {
				int dim = index + 1;
				recWithKeys(all, new ArrayList<>(keys), i, dim);
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
	public T getValueFromUpperAxeCoord(ArrayXDOrd<T, K, G> axes, K... upperAxeIndices) {
		CoordinatesXDByIndices<T, K, G> coordinates = getCoordinates(axes);
		if(coordinates.getAxesSize() < domains.length) {
			throw new AssertionError("Not compatible axes : upper reference should have at least the same number of axes");
		}
		
		for(int i = 0; i < coordinates.getAxesSize(); i++) {
			boolean found = false;
			int j = 0;
			for (G d : domains) {
				if(d.getName().equals(coordinates.getAxe(i).getName())) {
					found = true;
					break;
				}
				j++;
			}
			if(!found) {
				throw new AssertionError("Not compatible axes : unable to find " + coordinates.getAxe(i).getName());
			}
		}
		return getValue(coordinates.transform(upperAxeIndices));
	}
	
	@Override
	public CoordinatesXDByIndices<T, K, G> getCoordinates() {
		if(coordinates.size() == 1) {
			return coordinates.get(0);
		}
		throw new AssertionError("No or Multiple upper coordinates, use getCoordinates(axes) size=" + coordinates.size());
	}
	
	@Override
	public void addCoordinate(CoordinatesXDByIndices<T, K, G> coordinates) {
		this.coordinates.add(coordinates);
		coordinates.getAxes().addChildCoordinate(new CoordinatesXDByIndices<>(this, coordinates.getTransform()));
	}
	
	@Override
	public List<G> getAxes() {
		return Arrays.asList(domains);
	}

	public List<CoordinatesXDByIndices<T, K, G>> getChildCoordinates() {
		return childCoordinates;
	}
	
	@Override
	public void addChildCoordinate(CoordinatesXDByIndices<T, K, G> coordinates) {
		this.childCoordinates.add(coordinates);
	}

	@Override
	public void mergeChildren() {
		for(CoordinatesXDByIndices<T, K, G> c : childCoordinates) {
			c.getAxes().mergeChildren();
			List<Pair<List<K>,T>> val = c.getAxes().getAllWithKey();
			for(Pair<List<K>,T> p : val) {
				K[] m = p.getKey().toArray((K[])Array.newInstance(p.getKey().get(0).getClass(), p.getKey().size()));
				setValue(p.getValue(), c.transformInv(m));
			}
		}
	}
}
