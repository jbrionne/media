package fr.next.media;

import java.util.ArrayList;
import java.util.List;

import fr.next.media.Array2DWorld;
import fr.next.media.Logigram;
import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
import fr.next.media.process.History;
import junit.framework.TestCase;

public class LogigramFullResolution2Test extends TestCase  {

	
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
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPC = cubeWorld.getArray2D(domPersons, domCaches);
		cPC.setValue(ArrayLogigramValue.NEG, AMELIE, J7);
		cPC.setValue(ArrayLogigramValue.NEG, AUDE, J5);
		cPC.setValue(ArrayLogigramValue.NEG, AUDE, J6);
		cPC.setValue(ArrayLogigramValue.NEG, CLAIRE, J5);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPD = cubeWorld.getArray2D(domPersons, domDuree);
		cPD.setValue(ArrayLogigramValue.NEG, AUDE, MUSIQUE);
		cPD.setValue(ArrayLogigramValue.NEG, CLAIRE, MUSIQUE);
		cPD.setValue(ArrayLogigramValue.NEG, HONORINE, LECTURE);
		cPD.setValue(ArrayLogigramValue.NEG, HONORINE, MUSIQUE);
		cPD.setValue(ArrayLogigramValue.NEG, MAEL, JEUX);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPCl = cubeWorld.getArray2D(domPersons, domClasse);
		cPCl.setValue(ArrayLogigramValue.NEG, AMELIE, D19);
		cPCl.setValue(ArrayLogigramValue.NEG, AMELIE, D21);
		cPCl.setValue(ArrayLogigramValue.NEG, CLAIRE, D21);
		cPCl.setValue(ArrayLogigramValue.NEG, HONORINE, D21);
		cPCl.setValue(ArrayLogigramValue.NEG, HONORINE, D23);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cClC = cubeWorld.getArray2D(domClasse, domCaches);
		cClC.setValue(ArrayLogigramValue.NEG, D19, J5);
		cClC.setValue(ArrayLogigramValue.NEG, D19, J6);
		cClC.setValue(ArrayLogigramValue.NEG, D19, J9);
		cClC.setValue(ArrayLogigramValue.NEG, D20, J5);
		cClC.setValue(ArrayLogigramValue.NEG, D20, J6);
		cClC.setValue(ArrayLogigramValue.NEG, D20, J9);
		cClC.setValue(ArrayLogigramValue.NEG, D22, J5);
		cClC.setValue(ArrayLogigramValue.NEG, D22, J6);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cClD = cubeWorld.getArray2D(domClasse, domDuree);
		cClD.setValue(ArrayLogigramValue.NEG, D19, DEVOIRS);
		cClD.setValue(ArrayLogigramValue.NEG, D19, JEUX);
		cClD.setValue(ArrayLogigramValue.NEG, D20, DEVOIRS);
		cClD.setValue(ArrayLogigramValue.NEG, D20, JEUX);
		cClD.setValue(ArrayLogigramValue.NEG, D21, DEVOIRS);
		
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cDC = cubeWorld.getArray2D(domDuree, domCaches);
		cDC.setValue(ArrayLogigramValue.NEG, APPELS, J7);
		cDC.setValue(ArrayLogigramValue.NEG, APPELS, J8);
		cDC.setValue(ArrayLogigramValue.NEG, JEUX, J6);
		
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

}
