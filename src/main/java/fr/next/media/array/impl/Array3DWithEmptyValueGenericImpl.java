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

public class Array3DWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrdDomains<T, K, G> implements ArrayXDOrd<T, K, G> {

	private T[][][] cases;

	private G domainLine;
	private G domainCol;
	private G domainZ;
	
	private T emptyVal;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	Array3DWithEmptyValueGenericImpl(Class<T> clazz, G domainLine2, G domainCol2, G domainZ2, T emptyVal) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.domainZ = domainZ2;
		this.domains = (G[]) Array.newInstance(domainLine2.getClass(), 3);
		domains[0] = domainLine;
		domains[1] = domainCol;
		domains[2] = domainZ;
		this.emptyVal = emptyVal;
		this.clazz = clazz;
		cases = (T[][][]) Array.newInstance(clazz, domainLine2.size(), domainCol2.size(), domainZ2.size());
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases[i].length; j++) {
				for (int k = 0; k < cases[i][j].length; k++) {
					cases[i][j][k] = emptyVal;
				}
			}
		}
	}

	@Override
	public void setValue(T value, K... valueAxe) {
		if(valueAxe.length != 3) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxe[0];
		K valueAxeCol = valueAxe[1];
		K valueAxeZ = valueAxe[2];
		
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		int indexCol = Axe.findIndex(valueAxeCol, domainCol);
		int indexZ = Axe.findIndex(valueAxeZ, domainZ);
		
		cases[indexLine][indexCol][indexZ] = value;
	}
	
	@Override
	public void setValueByIndices(T value, float... indices) {
		if(indices.length != 3) {
			throw new AssertionError();
		}
		float indexAxeLine = indices[0];
	    float indexAxeCol = indices[1];
	    float indexAxeZ = indices[2];
		cases[convertFloatToInt(indexAxeLine)][convertFloatToInt(indexAxeCol)][convertFloatToInt(indexAxeZ)] = value;
	}

	@Override
	public T getValueByIndices(float... indices) {
		if(indices.length != 3) {
			throw new AssertionError();
		}
		float indexAxeLine = indices[0];
	    float indexAxeCol = indices[1];
	    float indexAxeZ = indices[2];
		return cases[convertFloatToInt(indexAxeLine)][convertFloatToInt(indexAxeCol)][convertFloatToInt(indexAxeZ)];
	}

	@Override
	public T getValue(K... valueAxe) {
		if(valueAxe.length != 3) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxe[0];
		K valueAxeCol = valueAxe[1];
		K valueAxeZ = valueAxe[2];
		
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		int indexCol = Axe.findIndex(valueAxeCol, domainCol);
		int indexZ = Axe.findIndex(valueAxeZ, domainZ);
		return cases[indexLine][indexCol][indexZ];
	}


	@Override
	public List<T> getValuesForAnAxe(int indexAxe, float indexToFind) {
		T[][] values = null;
		if (indexAxe == 0) {
			values = getLine(convertFloatToInt(indexToFind));
		} else if (indexAxe == 1) {
			values = getCol(convertFloatToInt(indexToFind));
		} else {
			values = getZ(convertFloatToInt(indexToFind));
		}
		List<T> response = new ArrayList<>();
		for (T[] value : values) {
			response.addAll(Arrays.asList(value));
		}
		return response;
	}
	
	@Override
	public void setValuesForAnAxe(int indexAxe, T... values) {
		throw new UnsupportedOperationException();
	}
	
	private T[][] getLine(int indexAxeLine) {
		return cases[indexAxeLine];
	}

	private T[][] getCol(int indexAxeCol) {
		@SuppressWarnings("unchecked")
		T[][] caseCol = (T[][]) Array.newInstance(clazz, domainLine.size(), domainZ.size());
		for (int i = 0; i < domainLine.size(); i++) {
			for (int j = 0; j < domainZ.size(); j++) {
				caseCol[i][j] = cases[i][indexAxeCol][j];
			}
		}
		return caseCol;
	}
	
	private T[][] getZ(int indexAxeZ) {
		@SuppressWarnings("unchecked")
		T[][] caseZ = (T[][]) Array.newInstance(clazz, domainLine.size(), domainCol.size());
		for (int i = 0; i < domainLine.size(); i++) {
			for (int j = 0; j < domainCol.size(); j++) {
				caseZ[i][j] = cases[i][j][indexAxeZ];
			}
		}
		return caseZ;
	}

	@Override
	public List<T> getAll() {
		List<T> all = new ArrayList<>();
		for(T[][] a : cases) {
			for(T[] b : a) {
				for(T k : b) {
					if(k != null) {
						all.add(k);
					}
				}
			}
		}
		return all;
	}
	
	@Override
	public List<Pair<List<K>, T>> getAllWithKey() {
		List<Pair<List<K>, T>> pair = new ArrayList<>();
		int i = 0;
		for(T[][] a : cases) {
			int j = 0;
			K x = domainLine.getElements().get(i).getValue();
			for(T[] b : a) {
					K y = domainCol.getElements().get(j).getValue();
					int m = 0;
					for(T k : b) {
						K z = domainZ.getElements().get(m).getValue();
						if (k != null) {
							List<K> keys = new ArrayList<>();
							keys.add(x);
							keys.add(y);
							keys.add(z);
							pair.add(new ImmutablePair<List<K>, T>(keys, k));
						}
						m++;
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
		List<T> response = null;
		T[][] values = null;
		G domains = null;
		if(indexAxe == 0) {
			values = getLine(convertFloatToInt(indexToFind));
			domains = domainLine;
		} else if(indexAxe == 1) {
			values = getCol(convertFloatToInt(indexToFind));
			domains = domainCol;
		} else {
			values = getZ(convertFloatToInt(indexToFind));
			domains = domainZ;
		}
		response = new ArrayList<>();
		for (T[] value : values) {
			response.addAll(Arrays.asList(value));
		}
		int index = 0;
		for(T c : response) {
			K d =  domains.getElements().get(index).getValue();
			pair.add(new ImmutablePair<K, T>(d, c));
			index++;
		}
		return pair;
	}

}
