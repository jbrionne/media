package fr.next.media.array.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordOperation;
import fr.next.media.array.CoordinatesXDByIndices;

public class MapXDWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private Map cases;

	private G[] domains;

	private List<CoordinatesXDByIndices> coordinates = new ArrayList<>();

	private Class<T> clazz;
	
	private T emptyVal;

	@SuppressWarnings("unchecked")
	public MapXDWithEmptyValueGenericImpl(Class<T> clazz, T emptyVal,
			G... domains) {
		this.clazz = clazz;
		this.domains = domains;
		this.emptyVal = emptyVal;
		cases = new HashMap<>();
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
		Map currentMap = cases;
		for(int i : indices) {
			if(i == indices.length - 1) {
				return (T)  currentMap.get(i);
			} else {
				Object  m = currentMap.get(i);
				if(m == null) {
					return emptyVal;
				} else {
				   currentMap = (Map) m;
				}
			}
		}
		throw new AssertionError("no value for map");
	}

	private void setValueInternal(T value, int... indices) {
		Map currentMap = cases;
		int loop = 0;
		for(int i : indices) {
			if(loop == indices.length - 1) {
				currentMap.put(i, value);
			} else {
				Object  m = currentMap.get(i);
				if(m == null) {
					Map nextMap = new HashMap<>();
					currentMap.put(i, nextMap);
					currentMap = nextMap;
				} else {
				   currentMap = (Map) m;
				}
			}
			loop++;
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
	public CoordinatesXDByIndices getCoordinates(ArrayXDOrd<T, K, G> axes) {
		for(CoordinatesXDByIndices c : coordinates) {
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
	
	private void recWithIndex(List<T> all, Map o, int[] indices, int index, int indexAxe, int indexToFind) {
	    for(Object eo : o.entrySet()) {
	    	Entry e = (Entry) eo;
	    	indices[index] = (int) e.getKey();
	    	Object i = e.getValue();
		    if (index  == domains.length - 2) {
				Map ii = (Map) i;
			    for(Object ieo : ii.entrySet()) {
			    	Entry ie = (Entry) ieo;
			    	indices[index + 1] = (int) ie.getKey();
					if (indices[indexAxe] == indexToFind) {
						Object z = ie.getValue();
						if (z != null) {
							all.add((T) z);
						}
					}
				}
			} else {
				int dim = index + 1;
				recWithIndex(all, (Map) i, indices, dim, indexAxe, indexToFind);
			}
		}
	}
	
	private void recWithIndexWithKey(List<Pair<K,T>> all, Map o, int[] indices, int index, int indexAxe, int indexToFind) {
	    for(Object eo : o.entrySet()) {
	    	Entry e = (Entry) eo;
	    	indices[index] = (int) e.getKey();
	    	Object i = e.getValue();
		    if (index  == domains.length - 2) {
				Map ii = (Map) i;
			    for(Object ieo : ii.entrySet()) {
			    	Entry ie = (Entry) ieo;
			    	indices[index + 1] = (int) ie.getKey();
					if (indices[indexAxe] == indexToFind) {
						Object z = ie.getValue();
						if (z != null) {
							all.add(new ImmutablePair<K,T>((K) domains[indexAxe].getElements().get((int) ie.getKey()).getValue(), (T) z));
						}
					}
				}
			} else {
				int dim = index + 1;
				recWithIndexWithKey(all, (Map) i, indices, dim, indexAxe, indexToFind);
			}
		}
	}

	private void rec(List<T> all, Map o, int index) {
	      for(Object eo : o.entrySet()) {
	    	Entry e = (Entry) eo;
	    	Object i = e.getValue();
	    	if (index  == domains.length - 2) {
				Map ii = (Map) i;
				for (int x = 0; x < ii.size(); x++) {
					Object z = ii.get(x);
					if (z != null) {
						all.add((T) z);
					}
				}
			} else {
				int dim = index + 1;
				rec(all, (Map) i, dim);
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
		int[] indices = new int[domains.length];
		recWithIndexWithKey(all, cases, indices, 0, indexAxe, indexToFind);
		return all;
	}
	
	@Override
	public T getValueFromUpperAxeCoord(ArrayXDOrd<T, K, G> axes, K... upperAxeIndices) {
		CoordinatesXDByIndices coordinates = getCoordinates(axes);
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
	
	@Override
	public void addCoordinate(CoordinatesXDByIndices coordinates) {
		this.coordinates.add(coordinates);
	}
	
	@Override
	public List<G> getAxes() {
		return Arrays.asList(domains);
	}
	
	@Override
	public CoordinatesXDByIndices getCoordinates() {
		if(coordinates.size() == 1) {
			return coordinates.get(0);
		}
		throw new AssertionError("Multiple upper coordinates, use getCoordinates(axes)");
	}
}
