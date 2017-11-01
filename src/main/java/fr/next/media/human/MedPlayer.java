package fr.next.media.human;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedPlayer extends AxeOrd<AxeValue>{

	public MedPlayer(String name, MedString playerName, AxeValue<MedString> type, MedPerson person, MedDest destination) {
		super(name);
		
		this.add(new AxeValue<MedString>(playerName));
		this.add(new AxeValue<AxeValue<MedString>>(type));
		this.add(new AxeValue<MedPerson>(person));
		this.add(new AxeValue<MedPlayerHand>(new MedPlayerHand("player_hand_" + playerName)));
		this.add(new AxeValue<MedResolutionAllPlayers>(null));
		this.add(new AxeValue<MedDest>(destination));
		this.add(new AxeValue<AxeOrd<AxeValue<MedRoom>>>(new AxeOrd("room_history_" + playerName)));
	}

	public MedString getPlayerName() {
		return (MedString) this.getElements().get(0).getValue();
	}
	
	public AxeValue<MedString> getType() {
		return (AxeValue<MedString>) this.getElements().get(1).getValue();
	}
	
	public MedPerson getPerson() {
		return (MedPerson) this.getElements().get(2).getValue();
	}
	
	public MedPlayerHand getPlayerHand() {
		return (MedPlayerHand) this.getElements().get(3).getValue();
	}
	
	public MedResolutionAllPlayers getResolutionAllPlayers() {
		return (MedResolutionAllPlayers) this.getElements().get(4).getValue();
	}
	
	public void setResolutionAllPlayers(MedResolutionAllPlayers resoPlayers) {
		this.getElements().set(4, new AxeValue(resoPlayers));
	}
	
	public MedDest getDestination() {
		return (MedDest) this.getElements().get(5).getValue();
	}
	
	public AxeOrd<AxeValue<MedRoom>> getRoomHistory() {
		return (AxeOrd<AxeValue<MedRoom>>) this.getElements().get(6).getValue();
	}
	
	public void setDestination(MedDest dest) {
		this.getElements().set(5, new AxeValue(dest));
	}
}
