package fr.next.media.array.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordOperation;
import fr.next.media.array.CoordinatesXDByIndices;

public class Array2DMatrix3fImpl<K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<Float, K, G> {

	private Matrix3f cases;

	private G domainLine;
	private G domainCol;

	private List<CoordinatesXDByIndices> coordinates = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public Array2DMatrix3fImpl(G domainLine2, G domainCol2) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;

		cases = new Matrix3f();
	}

	@Override
	public void setValue(Float value, K... valueAxe) {
		if (valueAxe.length != 2) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxe[0];
		K valueAxeCol = valueAxe[1];

		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		int indexCol = Axe.findIndex(valueAxeCol, domainCol);
		cases.get(indexLine, indexCol);
		cases.set(indexLine, indexCol, value);
	}

	@Override
	public void setValueByIndices(Float value, int... indices) {
		if (indices.length != 2) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
		int indexAxeCol = indices[1];

		cases.set(indexAxeLine, indexAxeCol, value);
	}

	@Override
	public Float getValueByIndices(int... indices) {
		if (indices.length != 2) {
			throw new AssertionError();
		}
		int indexAxeLine = indices[0];
		int indexAxeCol = indices[1];
		return cases.get(indexAxeLine, indexAxeCol);
	}

	@Override
	public Float getValue(K... valueAxe) {
		if (valueAxe.length != 2) {
			throw new AssertionError();
		}
		K valueAxeLine = valueAxe[0];
		K valueAxeCol = valueAxe[1];
		int indexLine = Axe.findIndex(valueAxeLine, domainLine);
		int indexCol = Axe.findIndex(valueAxeCol, domainCol);
		return cases.get(indexLine, indexCol);
	}

	@Override
	public List<Float> getValuesForAnAxe(int indexAxe, int indexToFind) {
		if (indexAxe == 0) {
			return Arrays.asList(getLine(indexToFind));
		} else {
			return Arrays.asList(getCol(indexToFind));
		}
	}

	private Float[] getLine(int indexAxeLine) {
		Vector3f v = cases.getRow(indexAxeLine);
		return new Float[] { v.x, v.y, v.z };
	}

	private Float[] getCol(int indexAxeCol) {
		Vector3f v = cases.getColumn(indexAxeCol);
		return new Float[] { v.x, v.y, v.z };
	}

	@Override
	public List<Float> getAll() {
		List<Float> all = new ArrayList<Float>();
		for (int j = 0; j < 3; j++) {
			Vector3f v = cases.getRow(j);
			all.add(v.x);
			all.add(v.y);
			all.add(v.z);
		}
		return all;
	}

	@Override
	public CoordinatesXDByIndices getCoordinates(ArrayXDOrd<Float, K, G> axes) {
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
	public G getAxe(int index) {
		if (index == 0) {
			return domainLine;
		} else {
			return domainCol;
		}
	}
	
	@Override
	public void setTranslation(Class<Float> clazzT, Float... values) {
		throw new AssertionError();
	}

	@Override
	public void setRotationQuaternion(Class<Float> clazzT, Float w, Float... values) {
		throw new IllegalMethod();	
	}

	@Override
	public void setScale(Class<Float> clazzT, Float... values) {
		throw new IllegalMethod();
	}
	
	@Override
	public List<Pair<K, Float>> getPairForAnAxe(int indexAxe, int indexToFind) {
		List<Pair<K, Float>> pair = new ArrayList<>();
		List<Float> values = null;
		G domains = null;
		if(indexAxe == 0) {
			values = Arrays.asList(getLine(indexToFind));
			domains = domainLine;
		} else {
			values = Arrays.asList(getCol(indexToFind));
			domains = domainCol;
		}
		int index = 0;
		for(Float c : values) {
			K d =  domains.getElements().get(index).getValue();
			pair.add(new ImmutablePair<K, Float>(d, c));
			index++;
		}
		return pair;
	}
	
	@Override
	public CoordinatesXDByIndices getCoordinates() {
		if(coordinates.size() == 1) {
			return coordinates.get(0);
		}
		throw new AssertionError("Multiple upper coordinates, use getCoordinates(axes)");
	}

	@Override
	public Float getValueFromUpperAxeCoord(ArrayXDOrd<Float, K, G> axes, K... upperAxeIndices) {
		CoordinatesXDByIndices coordinates = getCoordinates(axes);
		if (coordinates.getAxesSize() < 2) {
			throw new AssertionError(
					"Not compatible axes : upper reference should have at least the same number of axes");
		}
		Object valueAxeLine = null;
		Object valueAxeCol = null;
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
			}
			if (!found) {
				throw new AssertionError("Not compatible axes : unable to find " + coordinates.getAxe(i).getName());
			}
		}
		return getValue((K) valueAxeLine, (K) valueAxeCol);
	}
	
	@Override
	public void addCoordinate(CoordinatesXDByIndices coordinates) {
		this.coordinates.add(coordinates);
	}
	
	@Override
	public List<G> getAxes() {
		List<G> a = new ArrayList<>();
		a.add(domainLine);
		a.add(domainCol);
		return a;
	}
}
