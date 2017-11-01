package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedRoom extends AxeOrd<AxeValue<MedString>> {

	public MedRoom(String name, MedString roomName) {
		super(name);
		
		this.add(new AxeValue<MedString>(roomName));
		
	}

	public MedString getRoomName() {
		return (MedString) this.getElements().get(0).getValue();
	}


}
