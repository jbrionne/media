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
	public void setValueByIndices(T value, int... indices) {
		int[] newIndicesValue = new int[indices.length];
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = indices[i];
		}
		this.base.setValueByIndices(value, newIndicesValue);
	}

	@Override
	public T getValueByIndices(int... indices) {
		int[] newIndicesValue = new int[indices.length];
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
	public G getAxe(int index) {
		return this.base.getAxe(newIndicesOrder[index]);
	}
	
	@Override
	public List<T> getValuesForAnAxe(int indexAxe, int indexToFind) {
		return this.base.getValuesForAnAxe(newIndicesOrder[indexAxe], indexToFind);
	}

	@Override
	public List<T> getAll() {
		return this.base.getAll();
	}

	
	@Override
	public void setTranslation(Class<T> clazzT, T... values) {
		T[] newIndicesValue = (T[]) Array.newInstance(clazzT, values.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = values[i];
		}
		this.base.setTranslation(clazzT, newIndicesValue);
	}

	@Override
	public void setRotationQuaternion(Class<T> clazzT, T w, T... values) {
		if(values.length != 3) {
			throw new AssertionError();
		}
		T[] newIndicesValue = (T[]) Array.newInstance(clazzT, values.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = values[i];
		}
		this.base.setRotationQuaternion(clazzT, w, newIndicesValue);
	}

	@Override
	public void setScale(Class<T> clazzT, T... values) {
		T[] newIndicesValue = (T[]) Array.newInstance(clazzT, values.length);
		for(int i = 0; i < newIndicesOrder.length; i++) {
			newIndicesValue[newIndicesOrder[i]] = values[i];
		}
		this.base.setScale(clazzT, newIndicesValue);
	}

	@Override
	public List<Pair<K, T>> getPairForAnAxe(int indexAxe, int indexToFind) {
		return this.base.getPairForAnAxe(newIndicesOrder[indexAxe], indexToFind);
	}

	@Override
	public T getValueFromUpperAxeCoord(ArrayXDOrd<T, K, G> axes, K... upperAxeIndices){
		return this.base.getValueFromUpperAxeCoord(axes, upperAxeIndices);
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

}
