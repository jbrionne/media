package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.MedBinary;

public class MedA extends AxeOrd<AxeValue>{

	public MedA(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		
		MedASound medAsound = new MedASound("medAsound");
		MedBinary b = new MedBinary("medA_medAsound");
		b.setLeft(medAsound);
		b.setRight(this);
		medAsound.getElements().add(new AxeValue(b));
		this.getElements().add(new AxeValue(b));
		
		//TODO
		
		this.getElements().add(new AxeValue<MedACharacter>(new MedACharacter("A")));
		this.getElements().add(new AxeValue<MedAmCharacter>(new MedAmCharacter("a")));
		
	}

}
