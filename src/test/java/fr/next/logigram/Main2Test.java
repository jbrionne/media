package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.logigram.Array2DOrdValue;
import fr.next.logigram.process.History;
import junit.framework.TestCase;

public class Main2Test extends TestCase  {

	
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
		
		AxeOrd<AxeValue<String>> domPersons = new AxeOrd<>(ETUDIANT);
		domPersons.add(new AxeValue<String>(AMELIE));
		domPersons.add(new AxeValue<String>(AUDE));
		domPersons.add(new AxeValue<String>(CLAIRE));
		domPersons.add(new AxeValue<String>(HONORINE));
		domPersons.add(new AxeValue<String>(MAEL));
		domains.add(domPersons);
		
		AxeOrd<AxeValue<String>> domCaches = new AxeOrd<>(JOURS);
		domCaches.add(new AxeValue<String>(J5));
		domCaches.add(new AxeValue<String>(J6));
		domCaches.add(new AxeValue<String>(J7));
		domCaches.add(new AxeValue<String>(J8));
		domCaches.add(new AxeValue<String>(J9));
		domains.add(domCaches);
		
		AxeOrd<AxeValue<String>> domDuree = new AxeOrd<>(ATTENTE);
		domDuree.add(new AxeValue<String>(APPELS));
		domDuree.add(new AxeValue<String>(DEVOIRS));
		domDuree.add(new AxeValue<String>(JEUX));
		domDuree.add(new AxeValue<String>(LECTURE));
		domDuree.add(new AxeValue<String>(MUSIQUE));
		domains.add(domDuree);
		
		
		AxeOrd<AxeValue<String>> domClasse = new AxeOrd<>(AGE);
		domClasse.add(new AxeValue<String>(D19));
		domClasse.add(new AxeValue<String>(D20));
		domClasse.add(new AxeValue<String>(D21));
		domClasse.add(new AxeValue<String>(D22));
		domClasse.add(new AxeValue<String>(D23));
		domains.add(domClasse);
		
		
		Array2DWorld cubeWorld = new Array2DWorld(domains);
		Logigram logigram = new Logigram(new History(), cubeWorld);
		Array2DOrd<Array2DOrdValue, String> cPC = cubeWorld.getArray2D(domPersons, domCaches);
		cPC.setValue(AMELIE, J7, Array2DOrdValue.NEG);
		cPC.setValue(AUDE, J5, Array2DOrdValue.NEG);
		cPC.setValue(AUDE, J6, Array2DOrdValue.NEG);
		cPC.setValue(CLAIRE, J5, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cPD = cubeWorld.getArray2D(domPersons, domDuree);
		cPD.setValue(AUDE, MUSIQUE, Array2DOrdValue.NEG);
		cPD.setValue(CLAIRE, MUSIQUE, Array2DOrdValue.NEG);
		cPD.setValue(HONORINE, LECTURE, Array2DOrdValue.NEG);
		cPD.setValue(HONORINE, MUSIQUE, Array2DOrdValue.NEG);
		cPD.setValue(MAEL, JEUX, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cPCl = cubeWorld.getArray2D(domPersons, domClasse);
		cPCl.setValue(AMELIE, D19, Array2DOrdValue.NEG);
		cPCl.setValue(AMELIE, D21, Array2DOrdValue.NEG);
		cPCl.setValue(CLAIRE, D21, Array2DOrdValue.NEG);
		cPCl.setValue(HONORINE, D21, Array2DOrdValue.NEG);
		cPCl.setValue(HONORINE, D23, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cClC = cubeWorld.getArray2D(domClasse, domCaches);
		cClC.setValue(D19, J5, Array2DOrdValue.NEG);
		cClC.setValue(D19, J6, Array2DOrdValue.NEG);
		cClC.setValue(D19, J9, Array2DOrdValue.NEG);
		cClC.setValue(D20, J5, Array2DOrdValue.NEG);
		cClC.setValue(D20, J6, Array2DOrdValue.NEG);
		cClC.setValue(D20, J9, Array2DOrdValue.NEG);
		cClC.setValue(D22, J5, Array2DOrdValue.NEG);
		cClC.setValue(D22, J6, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cClD = cubeWorld.getArray2D(domClasse, domDuree);
		cClD.setValue(D19, DEVOIRS, Array2DOrdValue.NEG);
		cClD.setValue(D19, JEUX, Array2DOrdValue.NEG);
		cClD.setValue(D20, DEVOIRS, Array2DOrdValue.NEG);
		cClD.setValue(D20, JEUX, Array2DOrdValue.NEG);
		cClD.setValue(D21, DEVOIRS, Array2DOrdValue.NEG);
		
		
		
		Array2DOrd<Array2DOrdValue, String> cDC = cubeWorld.getArray2D(domDuree, domCaches);
		cDC.setValue(APPELS, J7, Array2DOrdValue.NEG);
		cDC.setValue(APPELS, J8, Array2DOrdValue.NEG);
		cDC.setValue(JEUX, J6, Array2DOrdValue.NEG);
		
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

}
