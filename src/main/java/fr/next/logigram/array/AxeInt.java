package fr.next.logigram.array;

import java.util.ArrayList;
import java.util.List;

public class AxeInt<T extends AxeVal> implements Axe<T> {

	/*
	 * Human readable name. Should be used only for toString method.
	 */
	private String name;
	private int size;

	public AxeInt(String name, int size) {
		super();
		this.name = name;
		this.size = size;
	}

	@Override
	public void add(T e) {
	}
	
	@Override
	public List<T> getElements() {
		return new ArrayList<>();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void removeElements() {
	}

}
