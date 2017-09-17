package fr.next.media.array.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXDByIndices;

public class Array2DMatrix3fImpl<K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<Float, K, G> {

	private Matrix3f cases;

	private G domainLine;
	private G domainCol;

	private CoordinatesXDByIndices coordinates;

	@SuppressWarnings("unchecked")
	public Array2DMatrix3fImpl(G domainLine2, G domainCol2, CoordinatesXDByIndices coordinates) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.coordinates = coordinates;

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
	public CoordinatesXDByIndices getCoordinates() {
		return coordinates;
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

}
