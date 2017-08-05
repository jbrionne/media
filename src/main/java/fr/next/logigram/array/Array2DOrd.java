package fr.next.logigram.array;

import java.util.List;

public interface Array2DOrd<T, K> {

	void setValue(K valueAxeLine, K valueAxeCol, T value);

	void setValue(int indexAxeLine, int indexAxeCol, T value);

	T getValue(int indexAxeLine, int indexAxeCol);

	T getValue(K valueAxeLine, K valueAxeCol);

	T[] getLine(int indexAxeLine);

	T[] getCol(int indexAxeCol);

	Axe<AxeValue<K>> getAxeLine();

	Axe<AxeValue<K>> getAxeCol();
	
	List<T> getAll();

	Coordinates getCoordinates();

}