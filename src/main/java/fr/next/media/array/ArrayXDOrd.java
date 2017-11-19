package fr.next.media.array;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface ArrayXDOrd<T, K, G extends Axe<? extends AxeVal<K>>> extends Serializable  {

	void setValue(T value, K... indices);
	
	void setValueByIndices(T value, CoordinatesXDByIndices<T, K, G> coord);
	
	void setValueByIndices(T value, int... indices);
	
	T getValueByIndices(int... indices);
	
	T getValueWithInclusionOfArrayChilds(K... indices);
	
	T getValueWithInclusionOfArrayChildsByIndices(int... indices);
	
	T getValue(K... indices);
	
	T getValueByIndices(CoordinatesXDByIndices<T, K, G> coord);
	
	T getValueFromUpperAxeCoord(ArrayXDOrd<T, K, G> axes, K... upperAxeIndices);
		
	CoordinatesXDByIndices<T, K, G> getCoordinates(ArrayXDOrd<T, K, G> axes);
	
	CoordinatesXDByIndices<T, K, G> getCoordinates();
	
	void addCoordinate(CoordinatesXDByIndices<T, K, G> coordinates);
	
	/**
	 * Do not use directly. Not add the coordinates in the parent.
	 * @param coordinates
	 */
	void addChildCoordinate(CoordinatesXDByIndices<T, K, G> coordinates);
	
	List<CoordinatesXDByIndices<T, K, G>>  getChildCoordinates();
		
	G getAxe(int index);
	
	List<G> getAxes();
	
	List<T> getAll();
	
	List<Pair<List<K>, T>> getAllWithKey();
	
	List<T> getValuesForAnAxe(int indexAxe, int indexToFind);
	
	List<Pair<K,T>> getPairForAnAxe(int indexAxe, int indexToFind);
	
	void setTranslation(Class<T> clazzT, T... values);
	
	void setRotationQuaternion(Class<T> clazzT, T w, T... values);
			
	void setScale(Class<T> clazzT, T... values);
	
	void merge();
	
	void mergeOnlyFirstLevel();
	
	int[] valuesToIndices(K... values);
	
}