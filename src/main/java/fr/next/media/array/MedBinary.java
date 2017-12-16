package fr.next.media.array;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedBinary extends AxeOrd<AxeValue<Axe>> {

	public MedBinary(String name) {
		super(name);
	}
	
	public Axe getLeft() {
		return this.getElements().get(0).getValue();
	}
	
	public Axe getRight() {
		return this.getElements().get(1).getValue();
	}
	
	public void setLeft(Axe axe) {
		this.getElements().set(0, new AxeValue(axe));
	}

	public void setRight(Axe axe) {
		this.getElements().set(1, new AxeValue(axe));
	}
}
