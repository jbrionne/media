package fr.next.logigram.array;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import fr.next.logigram.memory.Memory;

public class AxeOrdNum<T extends AxeVal> extends AxeOrd<T> {

	private int unit;

	public AxeOrdNum(String name, int unit) {
		super(name);
		this.unit = unit;
	}
	
	public int getUnit() {
		return unit;
	}
	
	@Override
	public List<T> getElements() {
		return elements;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeObject(name);
		oos.writeInt(unit);
		for (T e : elements) {
			if (e.getValue() instanceof Axe) {
				Axe a = (Axe) e.getValue();
				a.removeElements();
			}
		}
		if(elements != null) {
			oos.writeObject(elements);
		}
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		name = (String) ois.readObject();
		unit = ois.readInt();
		Object o = ois.readObject();
		if(o != null) {
		elements = (List<T>) o;
		for (T m : elements) {
				if(m.getValue() instanceof Axe) {
					Axe a = (Axe) m.getValue();
					Axe fullAxe = (Axe) Memory.getInstance().findAndGetContent(a.getName());
					if(fullAxe == null) {
						throw new AssertionError(a.getName() + " doesn't exist");
					}
					m.setValue(a);
				}
			}
		}
	}

}
