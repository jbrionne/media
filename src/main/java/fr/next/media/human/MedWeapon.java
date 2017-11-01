package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedWeapon extends AxeOrd<AxeValue<MedString>> {

	public MedWeapon(String name, MedString weaponName) {
		super(name);
		
		this.add(new AxeValue<MedString>(weaponName));
		
	}

	public MedString getWeaponName() {
		return (MedString) this.getElements().get(0).getValue();
	}

}
