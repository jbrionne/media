package fr.next.media.array;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface ArrayXDOrd<T, K, G extends Axe<? extends AxeVal<K>>> extends Serializable  {

	void setValue(T value, K... axeValues);
	
	void setValueByIndices(T value, CoordinatesXDByIndices<T, K, G> coord);
	
	void setValueByIndices(T value, float... indices);
	
	void setValuesForAnAxe(int indexAxe, T... values);
	
	T getValueByIndices(float... indices);
	
	T getValueWithInclusionOfArrayChilds(K... axeValues);
	
	T getValueWithInclusionOfArrayChildsByIndices(float... indices);
	
	T getValue(K... axeValues);
	
	T getValueByIndices(CoordinatesXDByIndices<T, K, G> coord);
	
	T getValueFromUpperAxeCoordByIndices(ArrayXDOrd<T, K, G> axes, float... upperAxeIndices);
	
	T getValueFromChildAxeCoordByIndices(ArrayXDOrd<T, K, G> axes, float... upperAxeIndices);
	
	CoordinatesXDByIndices<T, K, G> getCoordinates(ArrayXDOrd<T, K, G> axes);
	
	CoordinatesXDByIndices<T, K, G> getCoordinates();
	
	void addCoordinate(CoordinatesXDByIndices<T, K, G> coordinates);
	
	/**
	 * Do not use directly. Not add the coordinates in the parent.
	 * @param coordinates
	 */
	void addChildCoordinate(CoordinatesXDByIndices<T, K, G> coordinates);
	
	CoordinatesXDByIndices<T, K, G> getChildCoordinates(ArrayXDOrd<T, K, G> axes);
	
	List<CoordinatesXDByIndices<T, K, G>>  getChildCoordinates();
		
	G getAxe(int indexAxe);
	
	List<G> getAxes();
	
	List<T> getAll();
	
	List<Pair<List<K>, T>> getAllWithKey();
	
	List<T> getValuesForAnAxe(int indexAxe, float indexToFind);
	
	List<Pair<K,T>> getPairForAnAxe(int indexAxe, float indexToFind);
	
	void merge();
	
	void mergeOnlyFirstLevel();
	
	float[] valuesToIndices(K... axeValues);
	
	boolean isInBoundaries(K... axeValues);
	
	boolean isInBoundariesByIndices(float... indices);
	
}