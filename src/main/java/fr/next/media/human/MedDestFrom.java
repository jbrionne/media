package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedDestFrom extends AxeOrd<AxeValue> {
	

	public MedDestFrom(String name, MedRoom from, MedDest dests) {
		super(name);
		this.add(new AxeValue(from));
		this.add(new AxeValue(dests));
	}
	
	public MedRoom getFrom() {
		return (MedRoom) this.getElements().get(0).getValue();
	}
	
	public MedDest getDest() {
		return (MedDest) this.getElements().get(1).getValue();
	}
	



}
