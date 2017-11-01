package fr.next.media.array;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface ArrayXDOrd<T, K, G extends Axe<? extends AxeVal<K>>> extends Serializable  {

	void setValue(T value, K... indices);
	
	void setValueByIndices(T value, int... indices);
	
	T getValueByIndices(int... indices);
	
	T getValue(K... indices);
	
	T getValueFromUpperAxeCoord(K... upperAxeIndices);

	CoordinatesXDByIndices getCoordinates();

	G getAxe(int index);
	
	List<T> getAll();
	
	List<T> getValuesForAnAxe(int indexAxe, int indexToFind);
	
	List<Pair<K,T>> getPairForAnAxe(int indexAxe, int indexToFind);
	
	void setTranslation(Class<T> clazzT, T... values);
	
	void setRotationQuaternion(Class<T> clazzT, T w, T... values);
			
	void setScale(Class<T> clazzT, T... values);
	
}