package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedPerson extends AxeOrd<AxeValue> {

	public MedPerson(String name, MedString personName, MedDest dest) {
		super(name);
		
		this.add(new AxeValue<MedString>(personName));
		this.add(new AxeValue<MedDest>(dest));
	}
	
	public MedString getPersonName() {
		return (MedString) this.getElements().get(0).getValue();
	}
	
	public MedDest getDest() {
		return (MedDest) this.getElements().get(1).getValue();
	}

}
