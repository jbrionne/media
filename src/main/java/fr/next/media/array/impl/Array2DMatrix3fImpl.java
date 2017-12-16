package fr.next.media.array.impl;

import java.lang.reflect.Array;
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

public class Array2DMatrix3fImpl<K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrdDomains<Float, K, G> implements ArrayXDOrd<Float, K, G> {

	private Matrix3f cases;

	private G domainLine;
	private G domainCol;

	@SuppressWarnings("unchecked")
	public Array2DMatrix3fImpl(G domainLine2, G domainCol2) {
		this.domainLine = domainLine2;
		this.domainCol = domainCol2;
		this.domains = (G[]) Array.newInstance(domainLine2.getClass(), 2);
		domains[0] = domainLine;
		domains[1] = domainCol;
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
	public List<Pair<List<K>, Float>> getAllWithKey() {
		List<Pair<List<K>, Float>> pair = new ArrayList<>();
		for (int j = 0; j < 3; j++) {
			K x = domainLine.getElements().get(j).getValue();
			Vector3f v = cases.getRow(j);
			{
				K y0 = domainCol.getElements().get(0).getValue();
				List<K> keys = new ArrayList<>();
				keys.add(x);
				keys.add(y0);
				pair.add(new ImmutablePair<List<K>, Float>(keys, v.x));
			}
			{
				K y1 = domainCol.getElements().get(1).getValue();
				List<K> keys = new ArrayList<>();
				keys.add(x);
				keys.add(y1);
				pair.add(new ImmutablePair<List<K>, Float>(keys, v.y));	
			}
			{
				K y2 = domainCol.getElements().get(2).getValue();
				List<K> keys = new ArrayList<>();
				keys.add(x);
				keys.add(y2);
				pair.add(new ImmutablePair<List<K>, Float>(keys, v.z));	
			}
		}
		return pair;
	}


	@Override
	public List<Pair<K, Float>> getPairForAnAxe(int indexAxe, float indexToFind) {
		List<Pair<K, Float>> pair = new ArrayList<>();
		List<Float> values = null;
		G domains = null;
		if(indexAxe == 0) {
			values = Arrays.asList(getLine(convertFloatToInt(indexToFind)));
			domains = domainLine;
		} else {
			values = Arrays.asList(getCol(convertFloatToInt(indexToFind)));
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
	public ArrayXDOrd<Float, K, Axe<? extends AxeVal<K>>> addAxe(G axe) {
		ArrayXDOrd<Float, K, Axe<? extends AxeVal<K>>> a = new Array3DGenericImpl<>(Float.class, domainLine, domainCol, axe);
		for(Pair<List<K>, Float> p : getAllWithKey()) {
			a.setValue(p.getValue(), p.getKey().get(0), p.getKey().get(1), axe.getElements().get(0).getValue());
		}
		return a;
	}
}
