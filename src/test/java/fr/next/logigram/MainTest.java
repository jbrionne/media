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

public class MainTest extends TestCase  {

	
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
		Array2DOrd<Array2DOrdValue, String> cPC = cubeWorld.getArray2D(domPersons, domCaches);
		cPC.setValue(BLANDINE, ARBRE, Array2DOrdValue.NEG);
		cPC.setValue(BLANDINE, PIERRE, Array2DOrdValue.NEG);
		cPC.setValue(FLORENT, ARBRE, Array2DOrdValue.NEG);
		cPC.setValue(FLORENT, FEUILLES, Array2DOrdValue.NEG);
		cPC.setValue(FLORENT, PIERRE, Array2DOrdValue.NEG);
		cPC.setValue(LOICK, PIERRE, Array2DOrdValue.NEG);
		
		Array2DOrd<Array2DOrdValue, String> cPD = cubeWorld.getArray2D(domPersons, domDuree);
		cPD.setValue(ASMA, _11_MIN, Array2DOrdValue.NEG);
		cPD.setValue(BLANDINE, _11_MIN, Array2DOrdValue.NEG);
		cPD.setValue(CELIA, _5_MIN, Array2DOrdValue.NEG);
		cPD.setValue(CELIA, _7_MIN, Array2DOrdValue.NEG);
		cPD.setValue(CELIA, _11_MIN, Array2DOrdValue.NEG);
		cPD.setValue(FLORENT, _13_MIN, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cPCl = cubeWorld.getArray2D(domPersons, domClasse);
		cPCl.setValue(BLANDINE, CM1, Array2DOrdValue.NEG);
		cPCl.setValue(BLANDINE, CM2, Array2DOrdValue.NEG);
		cPCl.setValue(FLORENT, CE2, Array2DOrdValue.NEG);
		cPCl.setValue(LOICK, CM1, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cClC = cubeWorld.getArray2D(domClasse, domCaches);
		cClC.setValue(CP, MUR, Array2DOrdValue.NEG);
		cClC.setValue(CE1, PIERRE, Array2DOrdValue.NEG);
		cClC.setValue(CM1, BRANCHES, Array2DOrdValue.NEG);
		cClC.setValue(CM1, MUR, Array2DOrdValue.NEG);
		cClC.setValue(CM1, PIERRE, Array2DOrdValue.NEG);
		
		Array2DOrd<Array2DOrdValue, String> cClD = cubeWorld.getArray2D(domClasse, domDuree);
		cClD.setValue(CE1, _7_MIN, Array2DOrdValue.NEG);
		cClD.setValue(CE1, _11_MIN, Array2DOrdValue.NEG);
		cClD.setValue(CE1, _13_MIN, Array2DOrdValue.NEG);
		cClD.setValue(CE2, _13_MIN, Array2DOrdValue.NEG);
		cClD.setValue(CM2, _13_MIN, Array2DOrdValue.NEG);
		
		Array2DOrd<Array2DOrdValue, String> cDC = cubeWorld.getArray2D(domDuree, domCaches);
		cDC.setValue(_5_MIN, FEUILLES, Array2DOrdValue.NEG);
		cDC.setValue(_5_MIN, MUR, Array2DOrdValue.NEG);
		cDC.setValue(_7_MIN, FEUILLES, Array2DOrdValue.NEG);
		cDC.setValue(_13_MIN, ARBRE, Array2DOrdValue.NEG);
		cDC.setValue(_13_MIN, FEUILLES, Array2DOrdValue.NEG);
		cDC.setValue(_13_MIN, PIERRE, Array2DOrdValue.NEG);
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

	

}
