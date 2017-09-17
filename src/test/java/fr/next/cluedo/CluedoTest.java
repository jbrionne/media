package fr.next.cluedo;

import static fr.next.logigram.array.impl.ArrayFactory.mot;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeInt;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.CoordinatesXDByIndices;
import fr.next.logigram.array.impl.ArrayFactory;
import junit.framework.TestCase;

public class CluedoTest extends TestCase {


	public void testCluedo() {
		
		AxeInt axeLine = new AxeInt<>("worldLine", 1);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		AxeInt axeBoardLine = new AxeInt<>("boardLine", 1);
		AxeInt axeBoardCol = new AxeInt<>("boardCol", 1);
		int[] indices = new int[] { 0, 0};
		
		
		AxeOrd<AxeValue<AxeValue<Character>>> test = mot("test");
		for(AxeValue<AxeValue<Character>> c : test.getElements()) {
			System.out.println(c.getValue());
		}
		
		AxeOrd<AxeValue<String>> attribute = new AxeOrd<AxeValue<String>>("attribute");
		attribute.add(new AxeValue<String>("Nom"));
		attribute.add(new AxeValue<String>("Type"));
		
		AxeOrd<AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>> type = new AxeOrd<AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>>("type");
		type.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("IA")));
		type.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("Humain")));
		
		AxeOrd<AxeValue<? extends Object>> h = new AxeOrd<AxeValue<? extends Object>>("player");
		h.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot("H")));
		h.add(type.getElements().get(0));
		
		AxeOrd<AxeValue<String>> player = new AxeOrd<AxeValue<String>>("player");
		player.add(new AxeValue<String>("Nom"));
		player.add(new AxeValue<String>("Type"));
		
		AxeOrd<AxeValue<String>> types = new AxeOrd<AxeValue<String>>("types");
		types.add(new AxeValue<String>("Humain"));
		types.add(new AxeValue<String>("IA"));
		
		AxeOrd<AxeValue<String>> players = new AxeOrd<AxeValue<String>>("joueurs");
		players.add(new AxeValue<String>("Hélène"));
		players.add(new AxeValue<String>("Player1"));
		players.add(new AxeValue<String>("Player2"));
		
		
		
		
		
		ArrayXDOrd<ArrayXDOrd<String, String, Axe<AxeValue<String>>>, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<ArrayXDOrd<String, String, Axe<AxeValue<String>>>, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceStringValue(axeBoardLine, axeBoardCol,
						new CoordinatesXDByIndices(axes, indices));
	
		
		
		
		
		
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
		rooms.add(new AxeValue<String>("Salle de réception"));
		rooms.add(new AxeValue<String>("Jardin d'hiver"));
		rooms.add(new AxeValue<String>("Cuisine"));

		AxeOrd<AxeValue<String>> persons = new AxeOrd<AxeValue<String>>("personnes");
		persons.add(new AxeValue<String>("M. Olive"));
		persons.add(new AxeValue<String>("Dr. Orchidée"));
		persons.add(new AxeValue<String>("Mlle Rose"));
		persons.add(new AxeValue<String>("Prof. Violet"));
		persons.add(new AxeValue<String>("Col. Moutarde"));
		persons.add(new AxeValue<String>("Mme Pervenche"));

//		AxeInt axeLine = new AxeInt<>("worldLine", 1);
//		AxeInt axeCol = new AxeInt<>("worldCol", 1);
//		List<Axe> axes = new ArrayList<>();
//		axes.add(axeLine);
//		axes.add(axeCol);
//		AxeInt axeBoardLine = new AxeInt<>("boardLine", 1);
//		AxeInt axeBoardCol = new AxeInt<>("boardCol", 1);
		
		ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>> array2 = (ArrayXDOrd<String, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArrayLogigramValueX2Integer(axeBoardLine, axeBoardCol,
						new CoordinatesXDByIndices(axes, indices));
		
		//array.setValue("", 0, 0);
		
		
		
		int randA = (int) (Math.random() * weapons.size());
		int randP = (int) (Math.random() * persons.size());
		int randR = (int) (Math.random() * rooms.size());
		
		AxeValue<String> weaponToFind = weapons.getElements().get(randA);
		AxeValue<String> personToFind = persons.getElements().get(randP);
		AxeValue<String> roomToFind = rooms.getElements().get(randR);
		
		System.out.println(weaponToFind.getValue());
		System.out.println(personToFind.getValue());
		System.out.println(roomToFind.getValue());
		
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
		
		for(AxeValue<String> p :persons.getElements()) {
			if(!p.getValue().equals(personToFind.getValue())) {
				others.add(p);
			}
		}
		
		int nbCards = (int) ((others.size() / players.size()) + 1);
		AxeValue<String>[][] playersHand = new AxeValue[players.size()][nbCards];
		
		for(int currentPlayer = 0; currentPlayer < players.size(); currentPlayer++) {
			int index = 0;
			while(others.size() > 0 && index < nbCards) {
				int randO = (int) (Math.random() * others.size());
				AxeValue<String> foundCard = others.get(randO);
				playersHand[currentPlayer][index] = foundCard;
				others.remove(randO);
				index++;
			}
		}
		
		for(AxeValue<String>[] hand : playersHand) {
			System.out.println("**********");
			for(AxeValue<String> card : hand) {
				if(card != null) {
					System.out.println(card.getValue());
				}
			}
		}
		
		//Choix du nombre de joueur et de leur "type"
		//Le type d'un joueur peut-être "humain" ou "ia".
		//Détermination des cartes coupables et distribution des cartes aux joueurs, sous forme de fichiers
		//Choix du joueur qui commence
		//Lance deux dés, se déplace selon le résultat obtenu et pose une question à un joueur si il arrive dans une pièce (question assoicée à la pièce)
		//Un joueur est déplacé si il fait partie de l'hypothèse.
		//Le joueur remplit sa grille selon la réponse, les autres joueurs écoutent et déterminent aussi des indices.
		//Lorsque le joueur pense avoir trouvé le coupable alors il peut le dire à n(importe quel moment de son tour.
		
		
		
		
		
		
		
		

//		Bureau       entrée    salon
//		Bibliotheque            salle à manger
//		salle de billard   
//		Jardin d'hiver salle de réception cusisine
//		
//		Cuisine passage Bureau
//		Salon passage jardin d'hiver
//		cuisine salle à manger 11
//		salle à manger salon 
	}


}
