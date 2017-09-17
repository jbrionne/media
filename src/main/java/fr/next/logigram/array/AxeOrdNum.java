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
		if(elements != null) {
			for (T e : elements) {
				if (e.getValue() instanceof AxeValue) {
					AxeValue a = (AxeValue) e.getValue();
					a.getAxe().removeElements();
				}
			}
		}
		oos.writeObject(elements);
		oos.writeInt(unit);
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
		unit = ois.readInt();
	}

}
