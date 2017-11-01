package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedDest extends AxeOrd<AxeValue<MedRoom>> {
	

	public MedDest(String name, MedRoom... dests) {
		super(name);
		
		for(MedRoom dest : dests) {
			this.add(new AxeValue(dest));
		}
	}
	
	public MedRoom getDest1() {
		return this.getElements().get(0).getValue();
	}
	
	public MedRoom getDest2() {
		return this.getElements().get(1).getValue();
	}

	public MedRoom getDest3() {
		return this.getElements().get(2).getValue();
	}

}
