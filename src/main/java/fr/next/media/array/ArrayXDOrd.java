package fr.next.media.array;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface ArrayXDOrd<T, K, G extends Axe<? extends AxeVal<K>>> extends Serializable  {

	void setValue(T value, K... indices);
	
	void setValueByIndices(T value, int... indices);
	
	T getValueByIndices(int... indices);
	
	T getValue(K... indices);
	
	T getValueFromUpperAxeCoord(ArrayXDOrd<T, K, G> axes, K... upperAxeIndices);
		
	CoordinatesXDByIndices getCoordinates(ArrayXDOrd<T, K, G> axes);
	
	CoordinatesXDByIndices getCoordinates();
	
	void addCoordinate(CoordinatesXDByIndices coordinates);

	G getAxe(int index);
	
	List<G> getAxes();
	
	List<T> getAll();
	
	List<T> getValuesForAnAxe(int indexAxe, int indexToFind);
	
	List<Pair<K,T>> getPairForAnAxe(int indexAxe, int indexToFind);
	
	void setTranslation(Class<T> clazzT, T... values);
	
	void setRotationQuaternion(Class<T> clazzT, T w, T... values);
			
	void setScale(Class<T> clazzT, T... values);
	
}