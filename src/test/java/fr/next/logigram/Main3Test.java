package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.process.History;
import junit.framework.TestCase;

public class Main3Test extends TestCase  {

	//TODO
	
	public static final String AGE = "Age";
	private static final String D23 = "23 ans";
	private static final String D22 = "22 ans";
	private static final String D21 = "21 ans";
	private static final String D20 = "20 ans";
	private static final String D19 = "19 ans";
	public static final String ATTENTE = "Attente";
	private static final String MUSIQUE = "Musique";
	private static final String LECTURE = "Lecture";
	private static final String JEUX = "Jeux";
	private static final String DEVOIRS = "Devoirs";
	private static final String APPELS = "Appels";
	public static final String JOURS = "Jours";
	private static final String J9 = "9 jours";
	private static final String J8 = "8 jours";
	private static final String J7 = "7 jours";
	private static final String J6 = "6 jours";
	private static final String J5 = "5 jours";
	public static final String ETUDIANT = "Etudiant";
	private static final String MAEL = "Mael";
	private static final String HONORINE = "Honorine";
	private static final String CLAIRE = "Claire";
	private static final String AUDE = "Aude";
	private static final String AMELIE = "Amelie";

	public void testMain() {
		
		List<Axe<AxeValue<String>>> domains = new ArrayList<>();
		
		AxeOrd<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ETUDIANT);
		domPersons.add(new AxeValue<String>(AMELIE));
		domPersons.add(new AxeValue<String>(AUDE));
		domPersons.add(new AxeValue<String>(CLAIRE));
		domPersons.add(new AxeValue<String>(HONORINE));
		domPersons.add(new AxeValue<String>(MAEL));
		
		domains.add(domPersons);
		
		AxeOrd<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(JOURS);
		domCaches.add(new AxeValue<String>(J5));
		domCaches.add(new AxeValue<String>(J6));
		domCaches.add(new AxeValue<String>(J7));
		domCaches.add(new AxeValue<String>(J8));
		domCaches.add(new AxeValue<String>(J9));
		domains.add(domCaches);
		
		AxeOrd<AxeValue<String>> domDuree = new AxeOrd<AxeValue<String>>(ATTENTE);
		domDuree.add(new AxeValue<String>(APPELS));
		domDuree.add(new AxeValue<String>(DEVOIRS));
		domDuree.add(new AxeValue<String>(JEUX));
		domDuree.add(new AxeValue<String>(LECTURE));
		domDuree.add(new AxeValue<String>(MUSIQUE));
		domains.add(domDuree);
		
		
		AxeOrd<AxeValue<String>> domClasse = new AxeOrd<AxeValue<String>>(AGE);
		domClasse.add(new AxeValue<String>(D19));
		domClasse.add(new AxeValue<String>(D20));
		domClasse.add(new AxeValue<String>(D21));
		domClasse.add(new AxeValue<String>(D22));
		domClasse.add(new AxeValue<String>(D23));
		domains.add(domClasse);
		
		Array2DWorld cubeWorld = new Array2DWorld(domains);
		Logigram logigram = new Logigram(new History(), cubeWorld);
		//add constraints
		
		
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

}
