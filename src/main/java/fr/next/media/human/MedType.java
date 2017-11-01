package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedType extends AxeOrd<AxeValue<MedString>>{

	public MedType(String name, MedString ia, MedString human)  {
		super(name);
		
		this.add(new AxeValue(ia));
		this.add(new AxeValue(human));
		
	}
	
	public AxeValue<MedString> getIa() {
		return this.getElements().get(0);
	}
	
	public AxeValue<MedString> getHuman() {
		return this.getElements().get(1);
	}
}
