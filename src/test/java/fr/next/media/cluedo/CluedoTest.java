package fr.next.media.cluedo;

import static fr.next.media.array.impl.ArrayFactory.mot;

import java.util.ArrayList;
import java.util.List;

import fr.next.media.array.Axe;
import fr.next.media.array.AxeFunctions;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.db.WriterReader;
import junit.framework.TestCase;

public class CluedoTest extends TestCase {

	private String target = "target";

	public void testCluedo() {
		
		AxeOrd<AxeValue<String>> weapons = new AxeOrd<AxeValue<String>>("armes");
		weapons.add(new AxeValue<String>("Poignard"));
		weapons.add(new AxeValue<String>("Corde"));
		weapons.add(new AxeValue<String>("Barre de fer"));
		weapons.add(new AxeValue<String>("Clé Anglaise"));
		weapons.add(new AxeValue<String>("Révolver"));
		weapons.add(new AxeValue<String>("Chandelier"));
		
		AxeOrd<AxeValue<String>> rooms = new AxeOrd<AxeValue<String>>("pieces");
		rooms.add(new AxeValue<String>("Entrée"));
		rooms.add(new AxeValue<String>("Salle de billard"));
		rooms.add(new AxeValue<String>("Salon"));
		rooms.add(new AxeValue<String>("Salle à manger"));
		rooms.add(new AxeValue<String>("Bureau"));
		rooms.add(new AxeValue<String>("Bibliothèque"));
		rooms.add(new AxeValue<String>("Salle de réception"));
		rooms.add(new AxeValue<String>("Jardin d'hiver"));
		rooms.add(new AxeValue<String>("Cuisine"));
		
		//Rose > entrée et salon
		AxeOrd<AxeValue<String>> roseDest = new AxeOrd<AxeValue<String>>("rose_dest");
		roseDest.add(new AxeValue(rooms.getElements().get(0)));
		roseDest.add(new AxeValue(rooms.getElements().get(2)));

		//Violet > Bureau et Bibliotheque
		AxeOrd<AxeValue<String>> violetDest = new AxeOrd<AxeValue<String>>("rose_dest");
		violetDest.add(new AxeValue(rooms.getElements().get(4)));
		violetDest.add(new AxeValue(rooms.getElements().get(5)));

		//Pervenche > Salle de billard et Jardin d'hiver
		AxeOrd<AxeValue<String>> pervDest = new AxeOrd<AxeValue<String>>("perv_dest");
		pervDest.add(new AxeValue(rooms.getElements().get(1)));
		pervDest.add(new AxeValue(rooms.getElements().get(7)));

		//olive > Jardin d'hiver et Salle de réception
		AxeOrd<AxeValue<String>> oliveDest = new AxeOrd<AxeValue<String>>("olive_dest");
		oliveDest.add(new AxeValue(rooms.getElements().get(7)));
		oliveDest.add(new AxeValue(rooms.getElements().get(6)));
		
		//Orchidée > Salle de réception et cuisine		
		AxeOrd<AxeValue<String>> orchDest = new AxeOrd<AxeValue<String>>("orch_dest");
		orchDest.add(new AxeValue(rooms.getElements().get(6)));
		orchDest.add(new AxeValue(rooms.getElements().get(8)));
				
	    //Col moutarde > salle à manger et salon
		AxeOrd<AxeValue<String>> colMDest = new AxeOrd<AxeValue<String>>("colM_dest");
		colMDest.add(new AxeValue(rooms.getElements().get(2)));
		colMDest.add(new AxeValue(rooms.getElements().get(3)));
	
		int personName = 0;
		int personDest = 1;
		
		Axe<AxeValue> rose = new AxeOrd<AxeValue>("Mlle Rose");
		rose.add(new AxeValue<String>("Mlle Rose"));
		rose.add(new AxeValue(roseDest));
		
		Axe<AxeValue> violet = new AxeOrd<AxeValue>("Prof. Violet");
		violet.add(new AxeValue<String>("Prof. Violet"));
		violet.add(new AxeValue(violetDest));
		
		Axe<AxeValue> perv = new AxeOrd<AxeValue>("Mme Pervenche");
		perv.add(new AxeValue<String>("Mme Pervenche"));
		perv.add(new AxeValue(pervDest));
		
		Axe<AxeValue> olive = new AxeOrd<AxeValue>("M. Olive");
		olive.add(new AxeValue<String>("M. Olive"));
		olive.add(new AxeValue(oliveDest));
		
		Axe<AxeValue> orch = new AxeOrd<AxeValue>("Dr. Orchidée");
		orch.add(new AxeValue<String>("Dr. Orchidée"));
		orch.add(new AxeValue(orchDest));
		
		Axe<AxeValue> colM = new AxeOrd<AxeValue>("Col. Moutarde");
		colM.add(new AxeValue<String>("Col. Moutarde"));
		colM.add(new AxeValue(colMDest));
		
		Axe<AxeValue<Axe<AxeValue>>> persons = AxeFunctions.list(rose, violet, perv, olive, orch, colM);
		
		
		int typeIA = 0;
		int typeHuman = 1;
		AxeOrd<AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>> type = new AxeOrd<AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>>("type");
		type.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("IA")));
		type.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("Humain")));
		
		int playerName = 0;
		int playerType = 1;
		int playerPers = 2;
		int playerRoomAtStart = 3;
		int playerHandNum = 4;
		
		Axe<AxeValue> h = new AxeOrd<AxeValue>("playerH");
		h.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("Helene")));
		h.add(new AxeValue(type.getElements().get(typeHuman)));
		h.add(new AxeValue(persons.getElements().get(3)));
		h.add(new AxeValue(""));
		
		Axe<AxeValue> player1 = new AxeOrd<AxeValue>("player1");
		player1.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("player1")));
		player1.add(new AxeValue(type.getElements().get(typeIA)));
		player1.add(new AxeValue(persons.getElements().get(2)));
		player1.add(new AxeValue(""));
		
		Axe<AxeValue> player2 = new AxeOrd<AxeValue>("player2");
		player2.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("player2")));
		player2.add(new AxeValue(type.getElements().get(typeIA)));
		player2.add(new AxeValue(persons.getElements().get(5)));
		player2.add(new AxeValue(""));
		
		Axe<AxeValue<Axe<AxeValue>>> players =  AxeFunctions.list(h, player1, player2);
		
		AxeOrd<AxeValue<String>> types = new AxeOrd<AxeValue<String>>("types");
		types.add(new AxeValue<String>("Humain"));
		types.add(new AxeValue<String>("IA"));
		
		int randA = (int) (Math.random() * weapons.size());
		int randP = (int) (Math.random() * persons.size());
		int randR = (int) (Math.random() * rooms.size());
		
		AxeValue<String> weaponToFind = weapons.getElements().get(randA);
		AxeValue<String> personToFind = (AxeValue<String>) persons.getElements().get(randP).getValue().getElements().get(personName);
		AxeValue<String> roomToFind = rooms.getElements().get(randR);
		
		StringBuilder toFind = new StringBuilder();
		
		toFind.append(weaponToFind.getValue()).append(System.lineSeparator());
		toFind.append(personToFind.getValue()).append(System.lineSeparator());
		toFind.append(roomToFind.getValue()).append(System.lineSeparator());
		
		WriterReader.writeFile(target,"tofind.txt", toFind.toString());
		
		List<AxeValue<String>> others = new ArrayList<>();
		for(AxeValue<String> w :weapons.getElements()) {
			if(!w.getValue().equals(weaponToFind.getValue())) {
				others.add(w);
			}
		}
		
		for(AxeValue<String> r :rooms.getElements()) {
			if(!r.getValue().equals(roomToFind.getValue())) {
				others.add(r);
			}
		}
		
		for(AxeValue<Axe<AxeValue>> per :persons.getElements()) {
			AxeValue<String> p = (AxeValue<String>) per.getValue().getElements().get(personName);
			if(!p.getValue().equals(personToFind.getValue())) {
				others.add(p);
			}
		}
		
		int nbCards = (int) ((others.size() / players.size()));

		for(int currentPlayer = 0; currentPlayer < players.size(); currentPlayer++) {
			AxeOrd<AxeValue<String>> playerHand = new AxeOrd<AxeValue<String>>("hand_" + players.getElements().get(currentPlayer).getValue().getElements().get(personName).getValue());
			players.getElements().get(currentPlayer).getValue().add(new AxeValue(playerHand));
		}
		
		
		for(int currentPlayer = 0; currentPlayer < players.size(); currentPlayer++) {
			int index = 0;
			while(others.size() > 0 && index < nbCards) {
				int randO = (int) (Math.random() * others.size());
				AxeValue<String> foundCard = others.get(randO);
				((AxeOrd<AxeValue<String>>) (players.getElements().get(currentPlayer).getValue().getElements().get(playerHandNum).getValue())).getElements().add(foundCard);
				others.remove(randO);
				index++;
			}
		}
		
		int index = 0;
			
       for(int currentPlayer = 0; currentPlayer < players.size(); currentPlayer++) {
    	   AxeValue<AxeOrd<AxeValue<String>>> hand = (AxeValue<AxeOrd<AxeValue<String>>>) (players.getElements().get(currentPlayer).getValue().getElements().get(playerHandNum));
			StringBuilder strB = new StringBuilder();
			for(AxeValue<String> card : hand.getValue().getElements()) {
				if(card != null) {
					strB.append(card.getValue()).append(System.lineSeparator());
				}
				WriterReader.writeFile(target, players.getElements().get(index).getValue().getElements().get(personName).getValue() + ".txt", strB.toString());
			}
			index++;
		}
		
		//Choix du nombre de joueur et de leur "type"
		//Le type d'un joueur peut-être "humain" ou "ia".
		//Détermination des cartes coupables et distribution des cartes aux joueurs, sous forme de fichiers
		//Rose qui commence
		//Lance deux dés, se déplace selon le résultat obtenu et pose une question à un joueur si il arrive dans une pièce (question assoicée à la pièce)
		//Un joueur est déplacé si il fait partie de l'hypothèse.
		//Le joueur remplit sa grille selon la réponse, les autres joueurs écoutent et déterminent aussi des indices.
		//Lorsque le joueur pense avoir trouvé le coupable alors il peut le dire à n(importe quel moment de son tour.
		
		//Rose > entrée et salon
		//Violet > Bureau et Bibliotheque
		//Pervenche > Salle de billard et Jardin d'hiver
		//olive > Jardin d'hiver et Salle de réception
		//Orchidée > Salle de réception et cuisine
		//Col moutarde > salle à manger et salon
       
       boolean first = true;
       
		for(AxeValue<Axe<AxeValue>> per : persons.getElements()) {
			AxeValue<String> person = per.getValue().getElements().get(personName);
			for(AxeValue<Axe<AxeValue>> py : players.getElements()) {
				String n = (String) ((AxeValue) (((AxeOrd) ((AxeValue) py.getValue().getElements().get(playerPers).getValue()).getValue()).getElements().get(personName))).getValue();
				if(n.equals(person.getValue())) {
					System.out.println(n);
					
					//choix de la destination
					if(first) {
						
					} else {
						//AxeValue<String> destPoss = (AxeValue<String>) py.getValue().getElements().get(playerRoomAtStart).getValue();
					}
					
					
					System.out.println("Lance les dés !");
					
					
//					System.out.print("Enter something:");
//					String input = System.console().readLine();
//					
//					System.out.println(py.getValue().getElements().get(0).getValue());
					
					
					//Si destination ok alors choix de la question
					
					//remplir feuille
					//Si humain remplir la question et pour tous les joueurs IA faire les calculs..
					
					//si enquête terminé alors terminé (si juste)
					
				}
			}
			first = false;
		}
		

//		Bureau       entrée    salon
//		Bibliotheque            salle à manger
//		salle de billard   
//		Jardin d'hiver salle de réception cusisine
//		
//		Cuisine passage Bureau
//		Salon passage jardin d'hiver
//		cuisine salle à manger 
//		salle à manger salon 
	}


}
