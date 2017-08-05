package fr.next.logigram.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.Coordinates;
import fr.next.logigram.array.logigram.Array2DOrdImpl;
import fr.next.logigram.array.logigram.Array2DOrdValue;
import fr.next.logigram.draw.Draw;
import junit.framework.Assert;
import junit.framework.TestCase;

public class StrategiesTest extends TestCase {

	public static final String CLASSE = "Classe";
	private static final String CM2 = "CM2";
	private static final String CM1 = "CM1";
	private static final String CE2 = "CE2";
	private static final String CE1 = "CE1";
	private static final String CP = "CP";
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
	
	public void testStrategy() {
		Assert.assertTrue(true);
		
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
		
		AxeOrd<AxeValue<String>> domClasse = new AxeOrd<AxeValue<String>>(CLASSE);
		domClasse.add(new AxeValue<String>(CP));
		domClasse.add(new AxeValue<String>(CE1));
		domClasse.add(new AxeValue<String>(CE2));
		domClasse.add(new AxeValue<String>(CM1));
		domClasse.add(new AxeValue<String>(CM2));
		domains.add(domClasse);
		
		
		List<Array2DOrd<Array2DOrdValue, String>> cubes = new ArrayList<>();
		Array2DOrd<Array2DOrdValue, String> c1 = new Array2DOrdImpl(domPersons, domCaches, new Coordinates(0,0));
		Array2DOrd<Array2DOrdValue, String> c2 = new Array2DOrdImpl(domPersons, domClasse, new Coordinates(0,1));
		cubes.add(c1);
		cubes.add(c2);
		
		List<Array2DOrd<Array2DOrdValue, String>> cubesResults = new ArrayList<>();
		Array2DOrd<Array2DOrdValue, String> c3 = new Array2DOrdImpl(domCaches, domClasse, new Coordinates(2,0));
		cubesResults.add(c3);
		
		Strategy s =  new Strategy(new History(), cubesResults);
		
		c1.setValue(ASMA, BRANCHES, Array2DOrdValue.NEG);
		c1.setValue(CELIA, BRANCHES, Array2DOrdValue.NEG);
		c1.setValue(FLORENT, BRANCHES, Array2DOrdValue.NEG);
		
		c2.setValue(BLANDINE, CE1, Array2DOrdValue.NEG);
		c2.setValue(LOICK, CE1, Array2DOrdValue.NEG);
	
		
		Draw.draw(5, 2, Collections.singletonList(c1));
		Draw.draw(5, 2, Collections.singletonList(c2));
		Draw.draw(5, 2, Collections.singletonList(c3));
		
		s.strategyExclusionByCol(new ResultStrategy(), cubes);
		
		Draw.draw(5, 2, Collections.singletonList(c1));
		Draw.draw(5, 2, Collections.singletonList(c2));
		Draw.draw(5, 2, Collections.singletonList(c3));
		
		for(int i = 0; i < c3.getAxeLine().getElements().size(); i++) {
			for(int j = 0; j < c3.getAxeCol().getElements().size(); j++) {
				if(i == 1 && j == 1) {
					assertEquals(Array2DOrdValue.NEG, c3.getValue(i, j));
				} else {
					assertEquals(Array2DOrdValue.EMPTY, c3.getValue(i, j));
				}
			}
		}
		
	}
}
