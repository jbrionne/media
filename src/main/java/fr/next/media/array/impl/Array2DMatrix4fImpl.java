package fr.next.media.array.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jme3.math.Matrix4f;
import com.jme3.math.Quaternion;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXDByIndices;

public class Array2DMatrix4fImpl<K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<Float, K, G> {

	private Matrix4f cases;

	private G domainLine;
	private G domainCol;

	private CoordinatesXDByIndices coordinates;

	@SuppressWarnings("unchecked")
	public Array2DMatrix4fImpl(G domainLine2, G domainCol2, CoordinatesXDByIndices coordinates) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.coordinates = coordinates;

		cases = new Matrix4f();
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
		float[] val = new float[4 * 4];
		cases.get(val);
		Float[] f = new Float[4];
		int j = 0;
		for(int i = indexAxeLine * 4; i < indexAxeLine * 4 + 4; i++ ) {
			f[j] = val[i];
			j++;
		}
		return f;
	}

	private Float[] getCol(int indexAxeCol) {
		float[] val = new float[4 * 4];
		cases.get(val);
		Float[] f = new Float[4];
		int j = 0;
		for(int i = indexAxeCol; i < 4 * 4; i = i + 4 ) {
			f[j] = val[i];
			j++;
		}
		return f;
	}

	@Override
	public List<Float> getAll() {
		List<Float> all = new ArrayList<Float>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				all.add(cases.get(i, j));
			}
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
		if(values.length != 3) {
			throw new AssertionError();
		}
		this.cases.setTranslation(values[0], values[1], values[2]);
	}

	@Override
	public void setRotationQuaternion(Class<Float> clazzT, Float w, Float... values) {
		if(values.length != 3) {
			throw new AssertionError();
		}
		this.cases.setRotationQuaternion(new Quaternion(values[0], values[1], values[2], w));
		
	}

	@Override
	public void setScale(Class<Float> clazzT, Float... values) {
		if(values.length != 3) {
			throw new AssertionError();
		}
		this.cases.setTranslation(values[0], values[1], values[2]);
	}

}
