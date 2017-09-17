package fr.next.logigram.array;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.db.SerialObject;
import fr.next.logigram.memory.Memory;

public class AxeOrd<T extends AxeVal> implements Axe<T> {

	/*
	 * Human readable name. Should be used only for toString method.
	 */
	protected String name;

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

	public int findIndex(Object elementVal) {
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

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void removeElements() {
		this.elements = null;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeObject(name);
		if(elements != null) {
			for (T e : elements) {
				if (e.getValue() instanceof AxeValue) {
					AxeValue a = (AxeValue) e.getValue();
					a.getAxe().removeElements();
				}
			}
		}
		oos.writeObject(elements);
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		name = (String) ois.readObject();
		Object o = ois.readObject();
		if(o != null) {
		elements = (List<T>) o;
		for (T m : elements) {
				if(m.getValue() instanceof AxeValue) {
					AxeValue axeValue = (AxeValue) m.getValue();
					Axe fullAxe = (Axe) Memory.getInstance().findAndGetContent(axeValue.getAxe().getName());
					if(fullAxe == null) {
						throw new AssertionError(axeValue.getAxe().getName() + " doesn't exist");
					}
					axeValue.setAxe(fullAxe);
				}
			}
		}
	}

}
