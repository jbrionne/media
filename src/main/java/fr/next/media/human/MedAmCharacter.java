package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedAmCharacter extends AxeOrd<AxeValue<Character>>{

	public MedAmCharacter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		this.getElements().add(new AxeValue<Character>('a'));
	}

}
