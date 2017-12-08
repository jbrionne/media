package fr.next.media.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class ArrayXDOrdProxy<T, K, G extends Axe<? extends AxeVal<K>>> implements ArrayXDOrd<T, K, G> {

	private ArrayXDOrd<T, K, G> base;
	private int[] newIndicesOrder;
	private Class<K> clazz;

	public ArrayXDOrdProxy(ArrayXDOrd<T, K, G> base, int[] newIndicesOrder, Class<K> clazz) {
		this.base = base;
		this.newIndicesOrder = newIndicesOrder;
		this.clazz = clazz;
	}

	@Override
	public void setValue(T value, K... indices) {
		K[] newIndicesValue = (K[]) Array.newInstance(clazz, indices.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		this.base.setValue(value, newIndicesValue);
	}

	@Override
	public void setValueByIndices(T value, float... indices) {
		float[] newIndicesValue = new float[indices.length];
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		this.base.setValueByIndices(value, newIndicesValue);
	}

	@Override
	public T getValueByIndices(float... indices) {
		float[] newIndicesValue = new float[indices.length];
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		return this.base.getValueByIndices(newIndicesValue);
	}

	@Override
	public T getValue(K... indices) {
		K[] newIndicesValue = (K[]) Array.newInstance(clazz, indices.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		return this.base.getValue(newIndicesValue);
	}

	@Override
	public CoordinatesXDByIndices getCoordinates(ArrayXDOrd<T, K, G> axes){
		return this.base.getCoordinates(axes);
	}
	
	@Override
	public CoordinatesXDByIndices getChildCoordinates(ArrayXDOrd<T, K, G> axes){
		return this.base.getChildCoordinates(axes);
	}

	@Override
	public G getAxe(int index) {
		return this.base.getAxe(newIndicesOrder[index]);
	}
	
	@Override
	public List<T> getValuesForAnAxe(int indexAxe, float indexToFind) {
		return this.base.getValuesForAnAxe(newIndicesOrder[indexAxe], indexToFind);
	}

	@Override
	public List<T> getAll() {
		return this.base.getAll();
	}

	

	@Override
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, float indexToFind) {
		return this.base.getPairForAnAxe(newIndicesOrder[indexAxe], indexToFind);
	}

	@Override
	public T getValueFromUpperAxeCoordByIndices(ArrayXDOrd<T, K, G> axes, float... upperAxeIndices){
		return this.base.getValueFromUpperAxeCoordByIndices(axes, upperAxeIndices);
	}

	@Override
	public void addCoordinate(CoordinatesXDByIndices coordinates) {
		 this.base.addCoordinate(coordinates);
	}

	@Override
	public List<G> getAxes() {
		List<G> n = new ArrayList<>();
		for(int i = this.base.getAxes().size(); i >= 0; i--) {
			n.add(this.base.getAxes().get(i));
		}
		return n;
	}

	@Override
	public CoordinatesXDByIndices getCoordinates() {
		return this.base.getCoordinates();
	}

	@Override
	public List<CoordinatesXDByIndices<T, K, G>> getChildCoordinates() {
		return this.base.getChildCoordinates();
	}

	@Override
	public void addChildCoordinate(CoordinatesXDByIndices<T, K, G> coordinates) {
		this.base.addChildCoordinate(coordinates);
	}

	@Override
	public void merge() {
		this.base.merge();
	}

	@Override
	public List<Pair<List<K>, T>> getAllWithKey() {
		return this.base.getAllWithKey();
	}

	@Override
	public void mergeOnlyFirstLevel() {
		this.base.mergeOnlyFirstLevel();
	}

	@Override
	public void setValueByIndices(T value, CoordinatesXDByIndices<T, K, G> coord) {
		float[] newIndicesValue = new float[coord.getPositionList().size() - 1];
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = (int) coord.getPositionList().get(i).intValue();
		}
		this.base.setValueByIndices(value, newIndicesValue);
	}

	@Override
	public T getValueByIndices(CoordinatesXDByIndices<T, K, G> coord) {
		float[] newIndicesValue = new float[coord.getPositionList().size() - 1];
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = coord.getPositionList().get(i).floatValue();
		}
		return this.base.getValueByIndices(newIndicesValue);
	}

	@Override
	public T getValueWithInclusionOfArrayChilds(K... indices) {
		K[] newIndicesValue = (K[]) Array.newInstance(clazz, indices.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		return this.base.getValueWithInclusionOfArrayChilds(newIndicesValue);
	}

	@Override
	public T getValueWithInclusionOfArrayChildsByIndices(float... indices) {
		float[] newIndicesValue = new float[indices.length];
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		return this.base.getValueWithInclusionOfArrayChildsByIndices(newIndicesValue);
	}

	@Override
	public float[] valuesToIndices(K... axeValues) {
		K[] newIndicesValue = (K[]) Array.newInstance(clazz, axeValues.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = axeValues[i];
		}
		return this.base.valuesToIndices(newIndicesValue);
	}

	@Override
	public boolean isInBoundaries(K... axeValues) {
		K[] newIndicesValue = (K[]) Array.newInstance(clazz, axeValues.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = axeValues[i];
		}
		return this.base.isInBoundaries(newIndicesValue);
	}

	@Override
	public boolean isInBoundariesByIndices(float... indices) {
		float[] newIndicesValue = new float[indices.length];
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		return this.base.isInBoundariesByIndices(newIndicesValue);
	}

	@Override
	public void setValuesForAnAxe(int indexAxe, T... values) {
		 this.base.setValuesForAnAxe(newIndicesOrder[indexAxe], values);
	}

	@Override
	public T getValueFromChildAxeCoordByIndices(ArrayXDOrd<T, K, G> axes, float... upperAxeIndices) {
		return this.base.getValueFromChildAxeCoordByIndices(axes, upperAxeIndices);
		
	}

}
