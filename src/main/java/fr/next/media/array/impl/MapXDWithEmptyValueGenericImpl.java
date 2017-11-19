package fr.next.media.array.impl;

import java.lang.reflect.Array;
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
import fr.next.media.array.CoordinatesXDByIndices;

public class MapXDWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrdDomains<T, K, G> implements ArrayXDOrd<T, K, G> {

	private Map cases;

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
		int j = 0;
		for(int i : indices) {
			if(j == indices.length - 1) {
				return (T)  currentMap.get(i);
			} else {
				Object  m = currentMap.get(i);
				if(m == null) {
					return emptyVal;
				} else {
				   currentMap = (Map) m;
				}
			}
			j++;
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
				for (Object x : ii.entrySet()) {
					Entry ex = (Entry) x;
					Object z = ex.getValue();
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
	
	private void recWithKeys(List<Pair<List<K>, T>> all, List<K> keys, Map o, int index) {
		 for(Object eo : o.entrySet()) {
		    	Entry e = (Entry) eo;
		    	Object i = e.getValue();
		    	K m = domains[index].getElements().get((int) e.getKey()).getValue();
		    	List newIndexKey = new ArrayList<>(keys);
		    	newIndexKey.add(m);
		    	if (index  == domains.length - 2) {
					Map ii = (Map) i;
					for (Object x : ii.entrySet()) {
						Entry ex = (Entry) x;
						List newKey = new ArrayList<>(newIndexKey);
						K mx = domains[index].getElements().get((int) ex.getKey()).getValue();
						newKey.add(mx);
						Object z = ex.getValue();
						if (z != null) {
							all.add(new ImmutablePair<List<K>, T>(newKey, (T) z));
						}
					}
				} else {
					int dim = index + 1;
					recWithKeys(all, new ArrayList<>(keys), (Map) i, dim);
				}
		      }
	}
	
	@Override
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, int indexToFind) {
		List<Pair<K, T>> all = new ArrayList<>();
		int[] indices = new int[domains.length];
		recWithIndexWithKey(all, cases, indices, 0, indexAxe, indexToFind);
		return all;
	}
	
	
	
	
}
