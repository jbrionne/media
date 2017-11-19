package fr.next.media.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.ArrayXDWithEmptyValueGenericImpl;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
import fr.next.media.db.WriterReader;
import fr.next.media.draw.Draw;
import fr.next.media.human.MedDest;
import fr.next.media.human.MedDestFrom;
import fr.next.media.human.MedPerson;
import fr.next.media.human.MedPlayer;
import fr.next.media.human.MedPlayerHand;
import fr.next.media.human.MedResolutionAllPlayers;
import fr.next.media.human.MedRoom;
import fr.next.media.human.MedString;
import fr.next.media.human.MedType;
import fr.next.media.human.MedWeapon;
import fr.next.media.process.History;
import fr.next.media.process.ResultStrategy;
import fr.next.media.process.Strategy;

public class CluedoMain {

	private static String target = "target";

	public static void main(String[] args) {

		History history = new History();
		Strategy strategy = new Strategy(history, null);

		AxeOrd<AxeValue<MedWeapon>> weapons = new AxeOrd<AxeValue<MedWeapon>>("armes");
		weapons.add(new AxeValue<MedWeapon>(new MedWeapon("Poignard", new MedString("Poignard"))));
		weapons.add(new AxeValue<MedWeapon>(new MedWeapon("Corde", new MedString("Corde"))));
		weapons.add(new AxeValue<MedWeapon>(new MedWeapon("Barre de fer", new MedString("Barre de fer"))));
		weapons.add(new AxeValue<MedWeapon>(new MedWeapon("Clé Anglaise", new MedString("Clé Anglaise"))));
		weapons.add(new AxeValue<MedWeapon>(new MedWeapon("Révolver", new MedString("Révolver"))));
		weapons.add(new AxeValue<MedWeapon>(new MedWeapon("Chandelier", new MedString("Chandelier"))));

		AxeOrd<AxeValue<MedRoom>> rooms = new AxeOrd<AxeValue<MedRoom>>("pieces");
		MedRoom entree = new MedRoom("Entrée", new MedString("Entrée"));
		rooms.add(new AxeValue<MedRoom>(entree));
		MedRoom billard = new MedRoom("Salle de billard", new MedString("Salle de billard"));
		rooms.add(new AxeValue<MedRoom>(billard));
		MedRoom salon = new MedRoom("Salon", new MedString("Salon"));
		rooms.add(new AxeValue<MedRoom>(salon));
		MedRoom salleAManger = new MedRoom("Salle à manger", new MedString("Salle à manger"));
		rooms.add(new AxeValue<MedRoom>(salleAManger));
		MedRoom bureau = new MedRoom("Bureau", new MedString("Bureau"));
		rooms.add(new AxeValue<MedRoom>(bureau));
		MedRoom bibliotheque = new MedRoom("Bibliothèque", new MedString("Bibliothèque"));
		rooms.add(new AxeValue<MedRoom>(bibliotheque));
		MedRoom salleReception = new MedRoom("Salle de réception", new MedString("Salle de réception"));
		rooms.add(new AxeValue<MedRoom>(salleReception));
		MedRoom jardinDHiver = new MedRoom("Jardin d'hiver", new MedString("Jardin d'hiver"));
		rooms.add(new AxeValue<MedRoom>(jardinDHiver));
		MedRoom cuisine = new MedRoom("Cuisine", new MedString("Cuisine"));
		rooms.add(new AxeValue<MedRoom>(cuisine));

		MedDest roseDest = new MedDest("rose_dest", entree, salon);
		MedDest violetDest = new MedDest("violet_dest", bureau, bibliotheque);
		MedDest pervDest = new MedDest("perv_dest", billard, jardinDHiver);
		MedDest oliveDest = new MedDest("olive_dest", jardinDHiver, salleReception);
		MedDest orchDest = new MedDest("orch_dest", salleReception, cuisine);
		MedDest colMDest = new MedDest("colM_dest", salleAManger, salon);

		MedDest fromBureau = new MedDest("from_bureau", entree, bibliotheque, cuisine);
		MedDestFrom fromBureauDest = new MedDestFrom("from_bureau_dest", bureau, fromBureau);
		MedDest fromBibliotheque = new MedDest("from_bibliotheque", bureau, entree, billard);
		MedDestFrom fromBibliothequeDest = new MedDestFrom("from_bibliotheque_dest", bibliotheque, fromBibliotheque);
		MedDest fromBillard = new MedDest("from_billard", bibliotheque, jardinDHiver, salleReception);
		MedDestFrom fromBillardDest = new MedDestFrom("from_billard_dest", billard, fromBillard);
		MedDest fromJardinDHiver = new MedDest("from_jardin_hiver", billard, salleReception, salon);
		MedDestFrom fromJardinDHiverDest = new MedDestFrom("from_jardin_hiver_dest", jardinDHiver, fromJardinDHiver);
		MedDest fromSalleReception = new MedDest("from_salle_reception", jardinDHiver, billard, cuisine, salleAManger);
		MedDestFrom fromSalleReceptionDest = new MedDestFrom("from_salle_reception_dest", salleReception,
				fromSalleReception);
		MedDest fromCuisine = new MedDest("from_cusine", bureau, salleReception, salleAManger);
		MedDestFrom fromCuisineDest = new MedDestFrom("from_cusine_dest", cuisine, fromCuisine);
		MedDest fromSalleAManger = new MedDest("from_salle_manger", cuisine, salleReception, salon, entree);
		MedDestFrom fromSalleAMangerDest = new MedDestFrom("from_salle_manger_dest", salleAManger, fromSalleAManger);
		MedDest fromSalon = new MedDest("from_salon", jardinDHiver, entree, salleAManger);
		MedDestFrom fromSalonDest = new MedDestFrom("from_salon_dest", salon, fromSalon);
		MedDest fromEntree = new MedDest("from_entree", bureau, salon, bibliotheque, salleAManger);
		MedDestFrom fromEntreeDest = new MedDestFrom("from_entree_dest", entree, fromEntree);

		AxeOrd<AxeValue<MedDestFrom>> allDest = new AxeOrd<AxeValue<MedDestFrom>>("allDest");
		allDest.add(new AxeValue<MedDestFrom>(fromBureauDest));
		allDest.add(new AxeValue<MedDestFrom>(fromBibliothequeDest));
		allDest.add(new AxeValue<MedDestFrom>(fromBillardDest));
		allDest.add(new AxeValue<MedDestFrom>(fromJardinDHiverDest));
		allDest.add(new AxeValue<MedDestFrom>(fromSalleReceptionDest));
		allDest.add(new AxeValue<MedDestFrom>(fromCuisineDest));
		allDest.add(new AxeValue<MedDestFrom>(fromSalleAMangerDest));
		allDest.add(new AxeValue<MedDestFrom>(fromSalonDest));
		allDest.add(new AxeValue<MedDestFrom>(fromEntreeDest));

		MedPerson rose = new MedPerson("Mlle Rose", new MedString("Mlle Rose"), roseDest);
		MedPerson violet = new MedPerson("Prof. Violet", new MedString("Prof. Violet"), violetDest);
		MedPerson perv = new MedPerson("Mme Pervenche", new MedString("Mme Pervenche"), pervDest);
		MedPerson olive = new MedPerson("M. Olive", new MedString("M. Olive"), oliveDest);
		MedPerson orch = new MedPerson("Dr. Orchidée", new MedString("Dr. Orchidée"), orchDest);
		MedPerson colM = new MedPerson("Col. Moutarde", new MedString("Col. Moutarde"), colMDest);

		AxeOrd<AxeValue<MedPerson>> persons = new AxeOrd<AxeValue<MedPerson>>("persons");
		persons.add(new AxeValue<MedPerson>(rose));
		persons.add(new AxeValue<MedPerson>(violet));
		persons.add(new AxeValue<MedPerson>(perv));
		persons.add(new AxeValue<MedPerson>(olive));
		persons.add(new AxeValue<MedPerson>(orch));
		persons.add(new AxeValue<MedPerson>(colM));

		MedType type = new MedType("type", new MedString("IA"), new MedString("Humain"));

		MedPlayer helene = new MedPlayer("playerH", new MedString("Hélène"), type.getHuman(), perv, pervDest);
		MedPlayer jerome = new MedPlayer("playerJ", new MedString("Jérome"), type.getHuman(), violet, violetDest);
		MedPlayer player1 = new MedPlayer("player1", new MedString("player1"), type.getIa(), colM, colMDest);

		MedPlayer guiltyCards = new MedPlayer("guiltyCards", new MedString("guiltyCards"), null, null, null);

		AxeOrd<AxeValue<MedPlayer>> players = new AxeOrd<AxeValue<MedPlayer>>("players");
		players.add(new AxeValue<MedPlayer>(helene));
		players.add(new AxeValue<MedPlayer>(jerome));
		players.add(new AxeValue<MedPlayer>(player1));

		for (AxeValue<MedPlayer> p : players.getElements()) {
			MedResolutionAllPlayers medAllPlayersPlayer1 = new MedResolutionAllPlayers(
					"reso_all_player_" + p.getValue().getPlayerName(), players, guiltyCards, persons, weapons, rooms);
			p.getValue().setResolutionAllPlayers(medAllPlayersPlayer1);
			for (Object o : medAllPlayersPlayer1.getArrayPersons().getAxe(0).getElements()) {
				AxeValue a = (AxeValue) o;
				medAllPlayersPlayer1.getArrayPersons().setValue(ArrayLogigramValue.NEG, a.getValue(), p.getValue());
			}
			for (Object o : medAllPlayersPlayer1.getArrayWeapons().getAxe(0).getElements()) {
				AxeValue a = (AxeValue) o;
				medAllPlayersPlayer1.getArrayWeapons().setValue(ArrayLogigramValue.NEG, a.getValue(), p.getValue());
			}
			for (Object o : medAllPlayersPlayer1.getArrayRooms().getAxe(0).getElements()) {
				AxeValue a = (AxeValue) o;
				medAllPlayersPlayer1.getArrayRooms().setValue(ArrayLogigramValue.NEG, a.getValue(), p.getValue());
			}
		}

		int randA = (int) (Math.random() * weapons.size());
		int randP = (int) (Math.random() * persons.size());
		int randR = (int) (Math.random() * rooms.size());

		MedWeapon weaponToFind = weapons.getElements().get(randA).getValue();
		MedPerson personToFind = persons.getElements().get(randP).getValue();
		MedRoom roomToFind = rooms.getElements().get(randR).getValue();

		StringBuilder toFind = new StringBuilder();
		toFind.append(weaponToFind.getWeaponName()).append(System.lineSeparator());
		toFind.append(personToFind.getPersonName()).append(System.lineSeparator());
		toFind.append(roomToFind.getRoomName()).append(System.lineSeparator());
		WriterReader.writeFile(target, "tofind.txt", toFind.toString());

		List<AxeValue> others = new ArrayList<>();
		for (AxeValue<MedWeapon> w : weapons.getElements()) {
			if (!w.getValue().equals(weaponToFind)) {
				others.add(new AxeValue(w.getValue()));
			}
		}

		for (AxeValue<MedRoom> r : rooms.getElements()) {
			if (!r.getValue().equals(roomToFind)) {
				others.add(new AxeValue(r.getValue()));
			}
		}

		for (AxeValue<MedPerson> per : persons.getElements()) {
			if (!per.getValue().equals(personToFind)) {
				others.add(new AxeValue(per.getValue()));
			}
		}

		int nbCards = (int) ((others.size() / players.size()));

		for (int currentPlayer = 0; currentPlayer < players.size(); currentPlayer++) {
			MedPlayerHand playerHand = new MedPlayerHand(
					"hand_" + players.getElements().get(currentPlayer).getValue().getPlayerName());
			players.getElements().get(currentPlayer).getValue().add(new AxeValue(playerHand));
		}

		for (int currentPlayer = 0; currentPlayer < players.size(); currentPlayer++) {
			int index = 0;
			while (others.size() > 0 && index < nbCards) {
				int randO = (int) (Math.random() * others.size());
				AxeValue foundCard = others.get(randO);
				players.getElements().get(currentPlayer).getValue().getPlayerHand().getElements().add(foundCard);
				others.remove(randO);
				index++;
			}
		}

		int index = 0;

		int[] maxByCol = new int[players.size() + 1];
		for (int currentPlayer = 0; currentPlayer < players.size(); currentPlayer++) {
			MedPlayer player = players.getElements().get(currentPlayer).getValue();
			MedPlayerHand hand = player.getPlayerHand();
			StringBuilder strB = new StringBuilder();
			maxByCol[currentPlayer] = -1;
			for (AxeValue card : hand.getElements()) {
				if (card != null) {
					if (card.getValue() instanceof MedPerson) {
						MedPerson p = (MedPerson) card.getValue();
						if (player.getType().equals(type.getIa())) {
							player.getResolutionAllPlayers().getArrayPersons().setValue(ArrayLogigramValue.OK, p,
									player);
						}
						strB.append(MedString.readMot(p.getPersonName()));
					} else if (card.getValue() instanceof MedWeapon) {
						MedWeapon p = (MedWeapon) card.getValue();
						if (player.getType().equals(type.getIa())) {
							player.getResolutionAllPlayers().getArrayWeapons().setValue(ArrayLogigramValue.OK, p,
									player);
						}
						strB.append(MedString.readMot(((MedWeapon) card.getValue()).getWeaponName()));
					} else if (card.getValue() instanceof MedRoom) {
						MedRoom p = (MedRoom) card.getValue();
						if (player.getType().equals(type.getIa())) {
							player.getResolutionAllPlayers().getArrayRooms().setValue(ArrayLogigramValue.OK, p, player);
						}
						strB.append(MedString.readMot(((MedRoom) card.getValue()).getRoomName()));
					}
					strB.append(System.lineSeparator());

				}
				WriterReader.writeFile(target, players.getElements().get(index).getValue().getPlayerName() + ".txt",
						strB.toString());
			}
			maxByCol[players.size()] = 1;
			if (player.getType().equals(type.getIa())) {

				Draw.draw(player.getResolutionAllPlayers().getArrayPersons());
				Draw.draw(player.getResolutionAllPlayers().getArrayWeapons());
				Draw.draw(player.getResolutionAllPlayers().getArrayRooms());

				analyse(strategy, Collections.singletonList(player.getResolutionAllPlayers().getArrayPersons()),
						maxByCol);
				analyse(strategy, Collections.singletonList(player.getResolutionAllPlayers().getArrayWeapons()),
						maxByCol);
				analyse(strategy, Collections.singletonList(player.getResolutionAllPlayers().getArrayRooms()),
						maxByCol);

				Draw.draw(player.getResolutionAllPlayers().getArrayPersons());
				Draw.draw(player.getResolutionAllPlayers().getArrayWeapons());
				Draw.draw(player.getResolutionAllPlayers().getArrayRooms());

			}

			index++;
		}

		while (true) {
			for (AxeValue<MedPerson> per : persons.getElements()) {
				MedString person = per.getValue().getPersonName();
				for (AxeValue<MedPlayer> player : players.getElements()) {
					MedString n = player.getValue().getPerson().getPersonName();
					if (n.getName().equals(person.getName())) {
						System.out.println(player.getValue().getPlayerName() + " avec " + n);

						if (player.getValue().getType().equals(type.getIa())) {
							MedRoom changeRoom = windowChangeRoom(player.getValue(), rooms);
							if (changeRoom != null) {
								MedDestFrom possibleDest = null;
								for (AxeValue<MedDestFrom> futureDest : allDest.getElements()) {
									if (futureDest.getValue().getFrom().equals(changeRoom)) {
										possibleDest = futureDest.getValue();
										break;
									}
								}
								player.getValue().setDestination(possibleDest.getDest());
							}

							// si le joueur a déjà un lieu dans ses cartes alors
							// il est préférable de ne pas y aller
							// sinon random..
							MedDest personDestinations = player.getValue().getDestination();
							int destChoice;
							MedRoom choice = null;
							AxeOrd<AxeValue<MedRoom>> roomHistory = player.getValue().getRoomHistory();

							AxeOrd<AxeValue<MedRoom>> resoRoomDest = getMedRoomWithStatus(player.getValue(),
									guiltyCards, ArrayLogigramValue.EMPTY);

							// AxeOrd<AxeValue<MedResolutionRoom>> resoRoomDest
							// =
							// player.getValue().getResolutionAllPlayers().getResolutionForCurrentPlayer().getResolutionForRoomsWithStatus(logicalStatus.getEmpty());
							if (resoRoomDest.size() >= 1) {
								AxeOrd<AxeValue<MedRoom>> resoRoomChoice = new AxeOrd<>("tmpR" + Math.random());
								for (AxeValue<MedRoom> possibleRoom : personDestinations.getElements()) {
									for (AxeValue<MedRoom> possibleResoRoom : resoRoomDest.getElements()) {
										if (possibleResoRoom.getValue().equals(possibleRoom.getValue())) {
											resoRoomChoice.add(new AxeValue(possibleResoRoom.getValue()));
										}
									}
								}
								if (resoRoomChoice.size() > 0) {
									destChoice = (int) (Math.random() * resoRoomChoice.getElements().size());
									choice = resoRoomChoice.getElements().get(destChoice).getValue();
								} else {
									destChoice = (int) (Math.random() * resoRoomDest.getElements().size());
									choice = resoRoomDest.getElements().get(destChoice).getValue();
								}
							}

							if (choice == null) {
								destChoice = (int) (Math.random() * rooms.getElements().size());
								choice = rooms.getElements().get(destChoice).getValue();
							}
							roomHistory.add(new AxeValue(choice));

							int destReached = windowMove(player.getValue(), choice);
							boolean aDestinationIsReached = destReached == 1;
							if (!aDestinationIsReached) {
								changeRoom = windowChangeRoom(player.getValue(), rooms);
								if (changeRoom != null) {
									MedDestFrom possibleDest = null;
									for (AxeValue<MedDestFrom> futureDest : allDest.getElements()) {
										if (futureDest.getValue().getFrom().equals(changeRoom)) {
											possibleDest = futureDest.getValue();
											break;
										}
									}
									player.getValue().setDestination(possibleDest.getDest());
									aDestinationIsReached = true;
									choice = changeRoom;
								}
							}
							if (aDestinationIsReached) {

								int strategyChoice = (int) (Math.random() * 2);

								AxeOrd<AxeValue<MedPlayer>> emptyPlayers = new AxeOrd<>("tmp" + Math.random());
								AxeOrd<AxeValue<MedPlayer>> otherPlayers = new AxeOrd<>("tmp" + Math.random());
								for (AxeValue<MedPlayer> p : players.getElements()) {
									ArrayLogigramValue val = (ArrayLogigramValue) p.getValue().getResolutionAllPlayers()
											.getArrayRooms().getValue(choice, p.getValue());
									if (!p.getValue().equals(player.getValue())) {
										AxeValue a = new AxeValue(p.getValue());
										if (val == ArrayLogigramValue.EMPTY) {
											emptyPlayers.add(a);
										} else {
											otherPlayers.add(a);
										}
									}
								}

								MedPlayer playerToQuestion = null;
								if (emptyPlayers.getElements().size() > 0) {
									int playerToQuestionChoice = (int) (Math.random()
											* emptyPlayers.getElements().size());
									playerToQuestion = emptyPlayers.getElements().get(playerToQuestionChoice)
											.getValue();
								} else {
									int playerToQuestionChoice = (int) (Math.random()
											* otherPlayers.getElements().size());
									playerToQuestion = otherPlayers.getElements().get(playerToQuestionChoice)
											.getValue();
								}

								MedPerson personToQuestion = null;
								MedWeapon weaponToQuestion = null;
								if (strategyChoice == 0) {
									AxeOrd<AxeValue<MedPerson>> personsWithNotFoundStatus = getMedPersonWithStatus(
											player.getValue(), playerToQuestion, ArrayLogigramValue.EMPTY);
									if (personsWithNotFoundStatus.size() == 0) {
										personsWithNotFoundStatus = persons;
									}
									int personChoice = (int) (Math.random()
											* personsWithNotFoundStatus.getElements().size());
									AxeOrd<AxeValue<MedWeapon>> weaponsWithNotFoundStatus = getMedWeaponWithStatus(
											player.getValue(), playerToQuestion, ArrayLogigramValue.EMPTY);
									if (weaponsWithNotFoundStatus.size() == 0) {
										weaponsWithNotFoundStatus = weapons;
									}
									int weaponChoice = (int) (Math.random()
											* weaponsWithNotFoundStatus.getElements().size());
									personToQuestion = personsWithNotFoundStatus.getElements().get(personChoice)
											.getValue();

									weaponToQuestion = weaponsWithNotFoundStatus.getElements().get(weaponChoice)
											.getValue();
								} else {
									AxeOrd<AxeValue<MedPerson>> personsWithNotFoundStatus = getMedPersonWithStatus(
											player.getValue(), playerToQuestion, ArrayLogigramValue.NEG);
									if (personsWithNotFoundStatus.size() == 0) {
										personsWithNotFoundStatus = persons;
									}
									int personChoice = (int) (Math.random()
											* personsWithNotFoundStatus.getElements().size());
									AxeOrd<AxeValue<MedWeapon>> weaponsWithNotFoundStatus = getMedWeaponWithStatus(
											player.getValue(), playerToQuestion, ArrayLogigramValue.NEG);
									if (weaponsWithNotFoundStatus.size() == 0) {
										weaponsWithNotFoundStatus = weapons;
									}
									int weaponChoice = (int) (Math.random()
											* weaponsWithNotFoundStatus.getElements().size());
									personToQuestion = personsWithNotFoundStatus.getElements().get(personChoice)
											.getValue();

									weaponToQuestion = weaponsWithNotFoundStatus.getElements().get(weaponChoice)
											.getValue();
								}

								IAAskQuestionDialogToPlayer question = showWindowIAQuestion(playerToQuestion,
										personToQuestion, weaponToQuestion, choice);
								int shownCard = question.getIndexChoice();

								if (shownCard == 0) {
									player.getValue().getResolutionAllPlayers().getArrayPersons()
											.setValue(ArrayLogigramValue.NEG, personToQuestion, playerToQuestion);
									player.getValue().getResolutionAllPlayers().getArrayWeapons()
											.setValue(ArrayLogigramValue.NEG, weaponToQuestion, playerToQuestion);
									player.getValue().getResolutionAllPlayers().getArrayRooms()
											.setValue(ArrayLogigramValue.NEG, choice, playerToQuestion);
								} else if (shownCard == 1) {
									player.getValue().getResolutionAllPlayers().getArrayPersons()
											.setValue(ArrayLogigramValue.OK, personToQuestion, playerToQuestion);
								} else if (shownCard == 2) {
									player.getValue().getResolutionAllPlayers().getArrayWeapons()
											.setValue(ArrayLogigramValue.OK, weaponToQuestion, playerToQuestion);
								} else if (shownCard == 3) {
									player.getValue().getResolutionAllPlayers().getArrayRooms()
											.setValue(ArrayLogigramValue.OK, choice, playerToQuestion);
								}

								analyse(strategy,
										Collections.singletonList(
												player.getValue().getResolutionAllPlayers().getArrayPersons()),
										maxByCol);
								analyse(strategy,
										Collections.singletonList(
												player.getValue().getResolutionAllPlayers().getArrayWeapons()),
										maxByCol);
								analyse(strategy, Collections.singletonList(
										player.getValue().getResolutionAllPlayers().getArrayRooms()), maxByCol);

								// changement ou pas de destination
								boolean roomIsFound = (ArrayLogigramValue.OK == player.getValue()
										.getResolutionAllPlayers().getArrayRooms().getValue(choice, player.getValue()));

								if (roomIsFound) {
									MedDestFrom possibleDest = null;
									for (AxeValue<MedDestFrom> futureDest : allDest.getElements()) {
										if (futureDest.getValue().getFrom().equals(choice)) {
											possibleDest = futureDest.getValue();
											break;
										}
									}
									player.getValue().setDestination(possibleDest.getDest());
								}

								AxeOrd<AxeValue<MedPerson>> resoPerson = getMedPersonWithStatus(player.getValue(),
										guiltyCards, ArrayLogigramValue.OK);
								AxeOrd<AxeValue<MedWeapon>> resoWeapon = getMedWeaponWithStatus(player.getValue(),
										guiltyCards, ArrayLogigramValue.OK);
								AxeOrd<AxeValue<MedRoom>> resoRoom = getMedRoomWithStatus(player.getValue(),
										guiltyCards, ArrayLogigramValue.OK);

								if (resoPerson.size() == 1 && resoWeapon.size() == 1 && resoRoom.size() == 1) {
									JOptionPane.showMessageDialog(null,
											player.getValue().getPlayerName() + " :  LE COUPABLE EST "
													+ resoPerson.getElements().get(0).getValue().getPersonName()
													+ " avec "
													+ resoWeapon.getElements().get(0).getValue().getWeaponName()
													+ " dans " + resoRoom.getElements().get(0).getValue().getRoomName(),
											"Fin", JOptionPane.INFORMATION_MESSAGE);
								}

								Draw.draw(player.getValue().getResolutionAllPlayers().getArrayPersons());
								Draw.draw(player.getValue().getResolutionAllPlayers().getArrayWeapons());
								Draw.draw(player.getValue().getResolutionAllPlayers().getArrayRooms());
							}
						} else {
							// human
							// register question..
							int questionToPlayer1 = windowAskQuestion(player.getValue(), player1);

							if (questionToPlayer1 == 1) {
								PlayerAskQuestionToIADialog question = showWindowQuestion(persons, weapons, rooms);
								MedPerson p = question.getPerson();
								MedWeapon w = question.getWeapon();
								MedRoom r = question.getRoom();

								MedPlayerHand hand = player1.getPlayerHand();
								AxeOrd<AxeValue> hasCard = new AxeOrd<>("tmp" + Math.random());
								for (AxeValue h : hand.getElements()) {
									if (h.getValue().equals(w) || h.getValue().equals(r) || h.getValue().equals(p)) {
										hasCard.add(new AxeValue(h.getValue()));
									}
								}
								if (hasCard.size() == 0) {
									JOptionPane.showMessageDialog(null, " Pas de carte correspondante", "Reponse",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									int cardChoice = (int) (Math.random() * hasCard.getElements().size());
									JOptionPane.showMessageDialog(null,
											player1.getPlayerName() + " montre "
													+ hasCard.getElements().get(cardChoice).getValue(),
											"Reponse", JOptionPane.INFORMATION_MESSAGE);
								}
							}

						}
					}
				}
			}
		}
	}

	private static void analyse(Strategy strategy, List<ArrayXDOrd> arrays2D, int[] maxByCol) {
		ResultStrategy rGlobal = new ResultStrategy();
		// first loop
		rGlobal.setFindNewCase(true);

		while (rGlobal.isFindNewCase()) {
			rGlobal = new ResultStrategy();

			ResultStrategy sFillLine = strategy.strategyFillLine(arrays2D);
			rGlobal.setFindNewCase(sFillLine.isFindNewCase() || rGlobal.isFindNewCase());
			System.out.println("sFillLine");
			Draw.draw(arrays2D.get(0));

			ResultStrategy sFillIfAllNByCol = strategy.strategyFillIfAllNByCol(arrays2D);
			rGlobal.setFindNewCase(sFillIfAllNByCol.isFindNewCase() || rGlobal.isFindNewCase());
			System.out.println("sFillIfAllNByCol");
			Draw.draw(arrays2D.get(0));

			ResultStrategy sFillIfMaxOkByLine = strategy.strategyFillIfMaxOkByLine(arrays2D, maxByCol);
			rGlobal.setFindNewCase(sFillIfMaxOkByLine.isFindNewCase() || rGlobal.isFindNewCase());
			System.out.println("sFillIfMaxOkByLine");
			Draw.draw(arrays2D.get(0));

			ResultStrategy sFillIfMaxOkByLineByCountN = strategy.strategyFillIfMaxOkByLineByCountN(arrays2D, maxByCol);
			rGlobal.setFindNewCase(sFillIfMaxOkByLineByCountN.isFindNewCase() || rGlobal.isFindNewCase());
			System.out.println("sFillIfMaxOkByLineByCountN");
			Draw.draw(arrays2D.get(0));
		}
	}

	private static AxeOrd<AxeValue<MedRoom>> getMedRoomWithStatus(MedPlayer player, MedPlayer forPlayer,
			ArrayLogigramValue value) {
		ArrayXDWithEmptyValueGenericImpl arrayResoRoom = player.getResolutionAllPlayers().getArrayRooms();
		AxeOrd<AxeValue<MedRoom>> resoRoomDest = new AxeOrd<>("");
		List<ArrayLogigramValue> l = arrayResoRoom.getValuesForAnAxe(1,
				Axe.findIndex(forPlayer, arrayResoRoom.getAxe(1)));
		int indexReso = 0;
		for (ArrayLogigramValue a : l) {
			if (a == value) {
				resoRoomDest.add(
						new AxeValue(((AxeValue) arrayResoRoom.getAxe(0).getElements().get(indexReso)).getValue()));
			}
			indexReso++;
		}
		return resoRoomDest;
	}

	private static AxeOrd<AxeValue<MedWeapon>> getMedWeaponWithStatus(MedPlayer player, MedPlayer forPlayer,
			ArrayLogigramValue value) {
		ArrayXDWithEmptyValueGenericImpl arrayResoRoom = player.getResolutionAllPlayers().getArrayWeapons();
		AxeOrd<AxeValue<MedWeapon>> resoRoomDest = new AxeOrd<>("");
		List<ArrayLogigramValue> l = arrayResoRoom.getValuesForAnAxe(1,
				Axe.findIndex(forPlayer, arrayResoRoom.getAxe(1)));
		int indexReso = 0;
		for (ArrayLogigramValue a : l) {
			if (a == value) {
				resoRoomDest.add(
						new AxeValue(((AxeValue) arrayResoRoom.getAxe(0).getElements().get(indexReso)).getValue()));
			}
			indexReso++;
		}
		return resoRoomDest;
	}

	private static AxeOrd<AxeValue<MedPerson>> getMedPersonWithStatus(MedPlayer player, MedPlayer forPlayer,
			ArrayLogigramValue value) {
		ArrayXDWithEmptyValueGenericImpl arrayResoRoom = player.getResolutionAllPlayers().getArrayPersons();
		AxeOrd<AxeValue<MedPerson>> resoRoomDest = new AxeOrd<>("");
		List<ArrayLogigramValue> l = arrayResoRoom.getValuesForAnAxe(1,
				Axe.findIndex(forPlayer, arrayResoRoom.getAxe(1)));
		int indexReso = 0;
		for (ArrayLogigramValue a : l) {
			if (a == value) {
				resoRoomDest.add(
						new AxeValue(((AxeValue) arrayResoRoom.getAxe(0).getElements().get(indexReso)).getValue()));
			}
			indexReso++;
		}
		return resoRoomDest;
	}

	public static MedRoom windowChangeRoom(MedPlayer player, AxeOrd<AxeValue<MedRoom>> rooms) {

		int indexRoom = 1;
		String[] roomNames = new String[1 + rooms.getElements().size()];
		roomNames[0] = "Pas de changement";
		for (AxeValue<MedRoom> r : rooms.getElements()) {
			roomNames[indexRoom] = String.valueOf(r.getValue().getRoomName());
			indexRoom++;
		}

		String roomChoice = (String) JOptionPane.showInputDialog(null,
				"Changement d'endroit " + player.getPlayerName() + " ?", "Changement d'endroit ",
				JOptionPane.QUESTION_MESSAGE, null, roomNames, roomNames[0]);
		// JOptionPane.showMessageDialog(null, "Votre choix pour " +
		// player.getPlayerName() + " : " + roomChoice, "Changement d'endroit ",
		// JOptionPane.INFORMATION_MESSAGE);

		if (roomChoice.equals(roomNames[0])) {
			return null;
		} else {
			for (AxeValue<MedRoom> r : rooms.getElements()) {
				if (String.valueOf(r.getValue().getRoomName()).equals(roomChoice)) {
					return r.getValue();
				}
			}
		}
		return null;
	}

	public  static int windowMove(MedPlayer player, MedRoom choice) {
		int nbMove = (int) ((Math.random() * 11) + 2);
		int option = JOptionPane
				.showConfirmDialog(null,
						player.getPlayerName() + "\nLance les dés : " + nbMove + "  !\nDestination atteinte ? "
								+ choice.getRoomName(),
						"Déplacement", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (option == JOptionPane.OK_OPTION) {
			return 1;
		} else {
			return 0;
		}
	}

	public static int windowAskQuestion(MedPlayer player, MedPlayer playerIA) {
		int option = JOptionPane.showConfirmDialog(null,
				player.getPlayerName() + "\nPoser question à " + playerIA.getName() + " ?", "Poser question",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (option == JOptionPane.OK_OPTION) {
			return 1;
		} else {
			return 0;
		}
	}

	public static PlayerAskQuestionToIADialog showWindowQuestion(AxeOrd<AxeValue<MedPerson>> persons,
			AxeOrd<AxeValue<MedWeapon>> weapons, AxeOrd<AxeValue<MedRoom>> rooms) {
		PlayerAskQuestionToIADialog dialog = new PlayerAskQuestionToIADialog(null, "Choix question", true, persons,
				weapons, rooms);
		dialog.setVisible(true); // show the dialog on the screen
		return dialog;
	}

	public static IAAskQuestionDialogToPlayer showWindowIAQuestion(MedPlayer playerToQuestion, MedPerson person,
			MedWeapon weapon, MedRoom room) {
		IAAskQuestionDialogToPlayer dialog = new IAAskQuestionDialogToPlayer(null,
				"IA question à " + "Question à " + playerToQuestion.getPlayerName(), true, person, weapon, room);
		dialog.setVisible(true); // show the dialog on the screen
		return dialog;
	}

}
