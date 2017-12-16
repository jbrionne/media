package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedACharacter extends AxeOrd<AxeValue<Character>>{

	public MedACharacter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		this.getElements().add(new AxeValue<Character>('A'));
	}

}
