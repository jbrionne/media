package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.impl.logigram.ArrayLogigramValue;
import fr.next.logigram.process.History;
import junit.framework.TestCase;

public class LogigramFullResolutionTest extends TestCase  {

	
	public static final String CLASSE = "Classe";
	private static final String CM2 = "CM2";
	private static final String CM1 = "CM1";
	private static final String CE2 = "CE2";
	private static final String CE1 = "CE1";
	private static final String CP = "CP";
	public static final String DUREE = "Durée";
	private static final String _13_MIN = "13 min";
	private static final String _11_MIN = "11 min";
	private static final String _9_MIN = "9 min";
	private static final String _7_MIN = "7 min";
	private static final String _5_MIN = "5 min";
	public static final String CACHETTE = "Cachette";
	private static final String PIERRE = "Pierre";
	private static final String MUR = "Mur";
	private static final String FEUILLES = "Feuilles";
	private static final String BRANCHES = "Branches";
	private static final String ARBRE = "Arbre";
	public static final String ENFANT = "Enfant";
	private static final String LOICK = "Loïck";
	private static final String FLORENT = "Florent";
	private static final String CELIA = "Célia";
	private static final String BLANDINE = "Blandine";
	private static final String ASMA = "Asma";

	public void testMain() {
		
		List<Axe<AxeValue<String>>> domains = new ArrayList<>();
		
		AxeOrd<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>(ASMA));
		domPersons.add(new AxeValue<String>(BLANDINE));
		domPersons.add(new AxeValue<String>(CELIA));
		domPersons.add(new AxeValue<String>(FLORENT));
		domPersons.add(new AxeValue<String>(LOICK));
		domains.add(domPersons);
		
		
		AxeOrd<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>(ARBRE));
		domCaches.add(new AxeValue<String>(BRANCHES));
		domCaches.add(new AxeValue<String>(FEUILLES));
		domCaches.add(new AxeValue<String>(MUR));
		domCaches.add(new AxeValue<String>(PIERRE));
		domains.add(domCaches);
		
		AxeOrd<AxeValue<String>> domDuree = new AxeOrd<AxeValue<String>>(DUREE);
		domDuree.add(new AxeValue<String>(_5_MIN));
		domDuree.add(new AxeValue<String>(_7_MIN));
		domDuree.add(new AxeValue<String>(_9_MIN));
		domDuree.add(new AxeValue<String>(_11_MIN));
		domDuree.add(new AxeValue<String>(_13_MIN));
		domains.add(domDuree);
		
		AxeOrd<AxeValue<String>> domClasse = new AxeOrd<AxeValue<String>>(CLASSE);
		domClasse.add(new AxeValue<String>(CP));
		domClasse.add(new AxeValue<String>(CE1));
		domClasse.add(new AxeValue<String>(CE2));
		domClasse.add(new AxeValue<String>(CM1));
		domClasse.add(new AxeValue<String>(CM2));
		domains.add(domClasse);
		
		Array2DWorld cubeWorld = new Array2DWorld(domains);
		Logigram logigram = new Logigram(new History(), cubeWorld);
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPC = cubeWorld.getArray2D(domPersons, domCaches);
		cPC.setValue(ArrayLogigramValue.NEG, BLANDINE, ARBRE);
		cPC.setValue(ArrayLogigramValue.NEG, BLANDINE, PIERRE);
		cPC.setValue(ArrayLogigramValue.NEG, FLORENT, ARBRE);
		cPC.setValue(ArrayLogigramValue.NEG, FLORENT, FEUILLES);
		cPC.setValue(ArrayLogigramValue.NEG, FLORENT, PIERRE);
		cPC.setValue(ArrayLogigramValue.NEG, LOICK, PIERRE);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPD = cubeWorld.getArray2D(domPersons, domDuree);
		cPD.setValue(ArrayLogigramValue.NEG, ASMA, _11_MIN);
		cPD.setValue(ArrayLogigramValue.NEG, BLANDINE, _11_MIN);
		cPD.setValue(ArrayLogigramValue.NEG, CELIA, _5_MIN);
		cPD.setValue(ArrayLogigramValue.NEG, CELIA, _7_MIN);
		cPD.setValue(ArrayLogigramValue.NEG, CELIA, _11_MIN);
		cPD.setValue(ArrayLogigramValue.NEG, FLORENT, _13_MIN);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPCl = cubeWorld.getArray2D(domPersons, domClasse);
		cPCl.setValue(ArrayLogigramValue.NEG, BLANDINE, CM1);
		cPCl.setValue(ArrayLogigramValue.NEG, BLANDINE, CM2);
		cPCl.setValue(ArrayLogigramValue.NEG, FLORENT, CE2);
		cPCl.setValue(ArrayLogigramValue.NEG, LOICK, CM1);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cClC = cubeWorld.getArray2D(domClasse, domCaches);
		cClC.setValue(ArrayLogigramValue.NEG, CP, MUR);
		cClC.setValue(ArrayLogigramValue.NEG, CE1, PIERRE);
		cClC.setValue(ArrayLogigramValue.NEG, CM1, BRANCHES);
		cClC.setValue(ArrayLogigramValue.NEG, CM1, MUR);
		cClC.setValue(ArrayLogigramValue.NEG, CM1, PIERRE);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cClD = cubeWorld.getArray2D(domClasse, domDuree);
		cClD.setValue(ArrayLogigramValue.NEG, CE1, _7_MIN);
		cClD.setValue(ArrayLogigramValue.NEG, CE1, _11_MIN);
		cClD.setValue(ArrayLogigramValue.NEG, CE1, _13_MIN);
		cClD.setValue(ArrayLogigramValue.NEG, CE2, _13_MIN);
		cClD.setValue(ArrayLogigramValue.NEG, CM2, _13_MIN);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cDC = cubeWorld.getArray2D(domDuree, domCaches);
		cDC.setValue(ArrayLogigramValue.NEG, _5_MIN, FEUILLES);
		cDC.setValue(ArrayLogigramValue.NEG, _5_MIN, MUR);
		cDC.setValue(ArrayLogigramValue.NEG, _7_MIN, FEUILLES);
		cDC.setValue(ArrayLogigramValue.NEG, _13_MIN, ARBRE);
		cDC.setValue(ArrayLogigramValue.NEG, _13_MIN, FEUILLES);
		cDC.setValue(ArrayLogigramValue.NEG, _13_MIN, PIERRE);
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

	

}
