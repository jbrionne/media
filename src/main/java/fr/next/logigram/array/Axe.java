package fr.next.logigram.array;

import java.util.List;

public interface Axe<T extends AxeVal> {

	/**
	 * For an ordered axe, the order is the 'additional' order.
	 * @param axeValue
	 */
	void add(T axeValue);

	/*
	 * For an unordered Axe the return list should be considered as a random list.
	 */
	List<T> getElements();

}