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

public class Array3DWithEmptyValueGenericImpl<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G>  {

	private T[][][] cases;

	private G domainLine;
	private G domainCol;
	private G domainZ;
	
	private CoordinatesXDByIndices coordinates;
	
	private T emptyVal;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	Array3DWithEmptyValueGenericImpl(Class<T> clazz, G domainLine2, G domainCol2, G domainZ2,CoordinatesXDByIndices coordinates, T emptyVal) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.domainZ = domainZ2;
		this.coordinates = coordinates;
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
	public void setValueByIndices(T value, int... indices) {
		if(indices.length != 3) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
	    int indexAxeCol = indices[1];
	    int indexAxeZ = indices[2];
		cases[indexAxeLine][indexAxeCol][indexAxeZ] = value;
	}

	@Override
	public T getValueByIndices(int... indices) {
		if(indices.length != 3) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
	    int indexAxeCol = indices[1];
	    int indexAxeZ = indices[2];
		return cases[indexAxeLine][indexAxeCol][indexAxeZ];
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
	public List<T> getValuesForAnAxe(int indexAxe, int indexToFind) {
		T[][] values = null;
		if (indexAxe == 0) {
			values = getLine(indexToFind);
		} else if (indexAxe == 1) {
			values = getCol(indexToFind);
		} else {
			values = getZ(indexToFind);
		}
		List<T> response = new ArrayList<>();
		for (T[] value : values) {
			response.addAll(Arrays.asList(value));
		}
		return response;
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
	public G getAxe(int index) {
		if(index == 0) {
			return domainLine;
		} else if(index == 1) {
			return domainCol;
		} else {
			return domainZ;
		}
	}

	@Override
	public CoordinatesXDByIndices getCoordinates() {
		return coordinates;
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
		List<Pair<K, T>> pair = new ArrayList<>();
		List<T> response = null;
		T[][] values = null;
		G domains = null;
		if(indexAxe == 0) {
			values = getLine(indexToFind);
			domains = domainLine;
		} else if(indexAxe == 1) {
			values = getCol(indexToFind);
			domains = domainCol;
		} else {
			values = getZ(indexToFind);
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

	@Override
	public T getValueFromUpperAxeCoord(K... upperAxeIndices) {
		if (coordinates.getAxesSize() < 3) {
			throw new AssertionError(
					"Not compatible axes : upper reference should have at least the same number of axes");
		}
		Object valueAxeLine = null;
		Object valueAxeCol = null;
		Object valueAxeZ = null;
		for (int i = 0; i < coordinates.getAxesSize(); i++) {
			boolean found = false;
			if (domainLine.getName().equals(coordinates.getAxe(i).getName())) {
				found = true;
				if(upperAxeIndices[i] instanceof CoordOperation) {
					valueAxeLine = ((CoordOperation<K>) upperAxeIndices[i])
						.sub((K) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)));
				} else if(upperAxeIndices[i] instanceof Integer) {
					valueAxeLine = (Integer) upperAxeIndices[i] - (Integer) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)).getValue();
						
				}
			} else if (domainCol.getName().equals(coordinates.getAxe(i).getName())) {
				found = true;
				if(upperAxeIndices[i] instanceof CoordOperation) {
					valueAxeCol = ((CoordOperation<K>) upperAxeIndices[i])
						.sub((K) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)));
				} else if(upperAxeIndices[i] instanceof Integer) {
					valueAxeCol = (Integer) upperAxeIndices[i] - (Integer) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)).getValue();
				}
			} else if (domainZ.getName().equals(coordinates.getAxe(i).getName())) {
				found = true;
				if(upperAxeIndices[i] instanceof CoordOperation) {
					valueAxeZ = ((CoordOperation<K>) upperAxeIndices[i])
						.sub((K) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)));
				} else if(upperAxeIndices[i] instanceof Integer) {
					valueAxeZ = (Integer) upperAxeIndices[i] - (Integer) coordinates.getAxe(i).getElements().get(coordinates.getIndex(i)).getValue();
				}
			}
			if (!found) {
				throw new AssertionError("Not compatible axes : unable to find " + coordinates.getAxe(i).getName());
			}
		}
		return getValue((K) valueAxeLine, (K) valueAxeCol, (K) valueAxeZ);
	}
}
