package fr.next.logigram.array;

import java.util.ArrayList;
import java.util.List;

public class AxeOrd<T extends AxeVal> implements Axe<T> {

	/*
	 * Human readable name. Should be used only for toString method.
	 */
	private String name;

	/**
	 * Order list of axe values.
	 */
	protected List<T> elements = new ArrayList<>();

	public AxeOrd(String name) {
		super();
		this.name = name;
	}

	@Override
	public void add(T e) {
		e.setAxe(this);
		this.elements.add(e);
	}
	
	public static int findIndex(String elementVal, Axe<? extends AxeVal> domainLine) {
		int index = 0;
		for (AxeVal s : domainLine.getElements()) {
			if (s.getValue().equals(elementVal)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int findIndex(String elementVal) {
		int index = 0;
		for (T s : this.getElements()) {
			if (s.getValue().equals(elementVal)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	@Override
	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return name;
	}

}
