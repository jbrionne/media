package fr.next.logigram.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.CoordinatesXDByIndices;
import fr.next.logigram.array.impl.ArrayFactory;
import fr.next.logigram.array.impl.logigram.ArrayLogigramValue;
import fr.next.logigram.draw.Draw;
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
		
		List<Axe<AxeValue<String>>> domains = new ArrayList<>();
		
		Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>(ASMA));
		domPersons.add(new AxeValue<String>(BLANDINE));
		domPersons.add(new AxeValue<String>(CELIA));
		domPersons.add(new AxeValue<String>(FLORENT));
		domPersons.add(new AxeValue<String>(LOICK));
		domains.add(domPersons);
		
		Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>(ARBRE));
		domCaches.add(new AxeValue<String>(BRANCHES));
		domCaches.add(new AxeValue<String>(FEUILLES));
		domCaches.add(new AxeValue<String>(MUR));
		domCaches.add(new AxeValue<String>(PIERRE));
		domains.add(domCaches);
		
		Axe<AxeValue<String>> domClasse = new AxeOrd<AxeValue<String>>(CLASSE);
		domClasse.add(new AxeValue<String>(CP));
		domClasse.add(new AxeValue<String>(CE1));
		domClasse.add(new AxeValue<String>(CE2));
		domClasse.add(new AxeValue<String>(CM1));
		domClasse.add(new AxeValue<String>(CM2));
		domains.add(domClasse);
		
		Axe axeLine = new AxeOrd<>("worldLine");
		Axe axeCol = new AxeOrd<>("worldCol");
		
		List<Axe> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		int[] indices00 = new int[] {0, 0};
		int[] indices01 = new int[] {0, 1};
		int[] indices20 = new int[] {2, 0};
		List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> cubes = new ArrayList<>();
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c1 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domPersons, domCaches, new CoordinatesXDByIndices(axes, indices00));
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c2 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domPersons, domClasse, new CoordinatesXDByIndices(axes,indices01));
		cubes.add(c1);
		cubes.add(c2);
		
		List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> cubesResults = new ArrayList<>();
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c3 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domCaches, domClasse, new CoordinatesXDByIndices(axes, indices20));
		cubesResults.add(c3);
		
		Strategy s =  new Strategy(new History(), cubesResults);
		
		c1.setValue(ArrayLogigramValue.NEG, ASMA, BRANCHES);
		c1.setValue(ArrayLogigramValue.NEG, CELIA, BRANCHES);
		c1.setValue(ArrayLogigramValue.NEG, FLORENT, BRANCHES);
		
		c2.setValue(ArrayLogigramValue.NEG, BLANDINE, CE1);
		c2.setValue(ArrayLogigramValue.NEG, LOICK, CE1);
	
		
		Draw.draw(5, 2, Collections.singletonList(c1));
		Draw.draw(5, 2, Collections.singletonList(c2));
		Draw.draw(5, 2, Collections.singletonList(c3));
		
		s.strategyExclusionByCol(new ResultStrategy(), cubes);
		
		Draw.draw(5, 2, Collections.singletonList(c1));
		Draw.draw(5, 2, Collections.singletonList(c2));
		Draw.draw(5, 2, Collections.singletonList(c3));
		
		for(int i = 0; i < c3.getAxe(0).getElements().size(); i++) {
			for(int j = 0; j < c3.getAxe(1).getElements().size(); j++) {
				if(i == 1 && j == 1) {
					assertEquals(ArrayLogigramValue.NEG, c3.getValueByIndices(i, j));
				} else {
					assertEquals(ArrayLogigramValue.EMPTY, c3.getValueByIndices(i, j));
				}
			}
		}
		
	}
}
