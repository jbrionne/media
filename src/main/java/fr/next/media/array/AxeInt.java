package fr.next.media.array;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AxeInt other = (AxeInt) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
