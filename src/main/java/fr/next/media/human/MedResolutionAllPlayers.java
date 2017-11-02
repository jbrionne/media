package fr.next.media.human;

import java.util.ArrayList;
import java.util.List;

import fr.next.media.array.Axe;
import fr.next.media.array.AxeInt;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.ArrayXDWithEmptyValueGenericImpl;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
import fr.next.media.draw.Draw;

public class MedResolutionAllPlayers extends AxeOrd<AxeValue>  {

	public MedResolutionAllPlayers(String name, AxeOrd<AxeValue<MedPlayer>> players, MedPlayer guiltyCards, AxeOrd<AxeValue<MedPerson>> persons, AxeOrd<AxeValue<MedWeapon>> weapons,
			AxeOrd<AxeValue<MedRoom>> rooms) {
		super(name);
		
		AxeOrd<AxeValue<MedPlayer>> allCards = new AxeOrd<>("tmp " + Math.random());
		for(AxeValue<MedPlayer> p : players.getElements()) {
			allCards.add(new AxeValue(p.getValue()));
		}
		allCards.add(new AxeValue(guiltyCards));
		
		this.add(new AxeValue(new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, ArrayLogigramValue.EMPTY,
				 persons, allCards)));
		
		this.add(new AxeValue(new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, ArrayLogigramValue.EMPTY,
				 weapons, allCards)));
		
		this.add(new AxeValue(new ArrayXDWithEmptyValueGenericImpl(ArrayLogigramValue.class, ArrayLogigramValue.EMPTY,
				 rooms, allCards)));
	}
	
	public ArrayXDWithEmptyValueGenericImpl getArrayPersons() {
		return (ArrayXDWithEmptyValueGenericImpl) this.getElements().get(0).getValue();
	}
	
	public ArrayXDWithEmptyValueGenericImpl getArrayWeapons() {
		return (ArrayXDWithEmptyValueGenericImpl) this.getElements().get(1).getValue();
	}
	
	public ArrayXDWithEmptyValueGenericImpl getArrayRooms() {
		return (ArrayXDWithEmptyValueGenericImpl) this.getElements().get(2).getValue();
	}

}
