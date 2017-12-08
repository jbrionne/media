package fr.next.media.array;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("rawtypes")
public interface Axe<T extends AxeVal> extends Serializable {
	
	
	public static final int NOT_FOUND_IN_AXE =  -1;

	String getName();
	
	/**
	 * For an ordered axe, the order is the 'additional' order.
	 * @param axeValue
	 */
	void add(T axeValue);

	/*
	 * For an unordered Axe the return list should be considered as a random list.
	 * Possibility to iterate on elements
	 */
	List<T> getElements();
	
	
	void removeElements();
	
	/**
	 * @return size
	 */
	int size();

	public static int findIndex(Object elementVal, Axe<? extends AxeVal> domainLine) {
		int index = 0;
		if(domainLine instanceof AxeInt) {
			return (int) elementVal;
		}
		for (AxeVal s : domainLine.getElements()) {
			if (s.getValue().equals(elementVal)) {
				return index;
			}
			index++;
		}
		return NOT_FOUND_IN_AXE;
	}

}