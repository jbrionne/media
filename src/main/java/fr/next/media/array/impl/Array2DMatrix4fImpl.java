package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.jme3.math.Matrix4f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordOperation;
import fr.next.media.array.CoordinatesXDByIndices;

public class Array2DMatrix4fImpl<K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrdDomains<Float, K, G> implements ArrayXDOrd<Float, K, G> {

	private Matrix4f cases;

	private G domainLine;
	private G domainCol;

	@SuppressWarnings("unchecked")
	public Array2DMatrix4fImpl(G domainLine2, G domainCol2) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.domains = (G[]) Array.newInstance(domainLine2.getClass(), 2);
		domains[0] = domainLine;
		domains[1] = domainCol;
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
	public void setValueByIndices(Float value, float... indices) {
		if (indices.length != 2) {
			throw new AssertionError();
		}
		float indexAxeLine = indices[0];
		float indexAxeCol = indices[1];

		cases.set(convertFloatToInt(indexAxeLine), convertFloatToInt(indexAxeCol), value);
	}

	@Override
	public Float getValueByIndices(float... indices) {
		if (indices.length != 2) {
			throw new AssertionError();
		}
		float indexAxeLine = indices[0];
		float indexAxeCol = indices[1];
		return cases.get(convertFloatToInt(indexAxeLine), convertFloatToInt(indexAxeCol));
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
	public List<Float> getValuesForAnAxe(int indexAxe, float indexToFind) {
		if (indexAxe == 0) {
			return Arrays.asList(getLine(convertFloatToInt(indexToFind)));
		} else {
			return Arrays.asList(getCol(convertFloatToInt(indexToFind)));
		}
	}
	
	@Override
	public void setValuesForAnAxe(int indexAxe, Float... values) {
		for(int i = 0; i < values.length; i++) {
			cases.set(indexAxe, i, values[i]);
		}
	}

	private Float[] getLine(int indexAxeLine) {
		float[] val = new float[4 * 4];
		cases.get(val);
		Float[] f = new Float[4];
		int j = 0;
		for (int i = indexAxeLine * 4; i < indexAxeLine * 4 + 4; i++) {
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
		for (int i = indexAxeCol; i < 4 * 4; i = i + 4) {
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
	public List<Pair<List<K>, Float>> getAllWithKey() {
		List<Pair<List<K>, Float>> pair = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			K x = domainLine.getElements().get(i).getValue();
			for (int j = 0; j < 4; j++) {
				K y = domainCol.getElements().get(j).getValue();
				List<K> keys = new ArrayList<>();
				keys.add(x);
				keys.add(y);
				pair.add(new ImmutablePair<List<K>, Float>(keys, cases.get(i, j)));
			}
		}
		return pair;
	}

	@Override
	public List<Pair<K, Float>> getPairForAnAxe(int indexAxe, float indexToFind) {
		List<Pair<K, Float>> pair = new ArrayList<>();
		List<Float> values = null;
		G domains = null;
		if (indexAxe == 0) {
			values = Arrays.asList(getLine(convertFloatToInt(indexToFind)));
			domains = domainLine;
		} else {
			values = Arrays.asList(getCol(convertFloatToInt(indexToFind)));
			domains = domainCol;
		}
		int index = 0;
		for (Float c : values) {
			K d = domains.getElements().get(index).getValue();
			pair.add(new ImmutablePair<K, Float>(d, c));
			index++;
		}
		return pair;
	}

	@Override
	public String toString() {
		return this.cases.toString();
	}

	public Matrix4f getCases() {
		return cases;
	}

	public void setCases(Matrix4f cases) {
		this.cases = cases;
	}
	
	@Override
	public ArrayXDOrd<Float, K, Axe<? extends AxeVal<K>>> addAxe(G axe) {
		ArrayXDOrd<Float, K, Axe<? extends AxeVal<K>>> a = new Array3DGenericImpl<>(Float.class, domainLine, domainCol, axe);
		for(Pair<List<K>, Float> p : getAllWithKey()) {
			a.setValue(p.getValue(), p.getKey().get(0), p.getKey().get(1), axe.getElements().get(0).getValue());
		}
		return a;
	}
	

}
