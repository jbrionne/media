package fr.next.media.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.ArrayFactory;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;
import fr.next.media.draw.Draw;
import fr.next.media.process.History;
import fr.next.media.process.ResultStrategy;
import fr.next.media.process.Strategy;
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
	private static final String BRUNO = "Bruno";
	
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
		
		List<Axe<AxeValue>> axes = new ArrayList<>();
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
	
		
		Draw.drawLogigram(5, 2, Collections.singletonList(c1));
		Draw.drawLogigram(5, 2, Collections.singletonList(c2));
		Draw.drawLogigram(5, 2, Collections.singletonList(c3));
		
		s.strategyExclusionByCol(new ResultStrategy(), cubes);
		
		Draw.drawLogigram(5, 2, Collections.singletonList(c1));
		Draw.drawLogigram(5, 2, Collections.singletonList(c2));
		Draw.drawLogigram(5, 2, Collections.singletonList(c3));
		
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
	
	
	public void testStrategyMax() {
		
		Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>(ASMA));
		domPersons.add(new AxeValue<String>(BLANDINE));
		domPersons.add(new AxeValue<String>(CELIA));
		domPersons.add(new AxeValue<String>(FLORENT));
		domPersons.add(new AxeValue<String>(LOICK));
		
		Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>(ARBRE));
		domCaches.add(new AxeValue<String>(BRANCHES));
		domCaches.add(new AxeValue<String>(FEUILLES));
		domCaches.add(new AxeValue<String>(MUR));
		domCaches.add(new AxeValue<String>(PIERRE));
		

		Axe axeLine = new AxeOrd<>("worldLine");
		Axe axeCol = new AxeOrd<>("worldCol");
		List<Axe<AxeValue>> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c1 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domPersons, domCaches, new CoordinatesXDByIndices(axes, new int[] {0, 0}));
		
		c1.setValue(ArrayLogigramValue.OK, ASMA, BRANCHES);
		
		Strategy s =  new Strategy(new History(), null);
		s.strategyFillIfMaxOkByLine(Collections.singletonList(c1), new int[] {1, 1, 1, 1, 1});
		Draw.draw(c1);
		
		assertEquals(ArrayLogigramValue.OK, c1.getValue(ASMA, BRANCHES));
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(BLANDINE, BRANCHES));
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(CELIA, BRANCHES));
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(FLORENT, BRANCHES));
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(LOICK, BRANCHES));
	}
	
public void testStrategyFillLine() {
		
		Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
		domPersons.add(new AxeValue<String>(ASMA));
		domPersons.add(new AxeValue<String>(BLANDINE));
		domPersons.add(new AxeValue<String>(CELIA));
		domPersons.add(new AxeValue<String>(FLORENT));
		domPersons.add(new AxeValue<String>(LOICK));
		
		Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
		domCaches.add(new AxeValue<String>(ARBRE));
		domCaches.add(new AxeValue<String>(BRANCHES));
		domCaches.add(new AxeValue<String>(FEUILLES));
		domCaches.add(new AxeValue<String>(MUR));
		domCaches.add(new AxeValue<String>(PIERRE));
		

		Axe axeLine = new AxeOrd<>("worldLine");
		Axe axeCol = new AxeOrd<>("worldCol");
		List<Axe<AxeValue>> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c1 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domPersons, domCaches, new CoordinatesXDByIndices(axes, new int[] {0, 0}));
		
		c1.setValue(ArrayLogigramValue.OK, ASMA, BRANCHES);
		
		
		Strategy s =  new Strategy(new History(), null);
		s.strategyFillLine(Collections.singletonList(c1));
		Draw.draw(c1);
		
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(ASMA, ARBRE));
		assertEquals(ArrayLogigramValue.OK, c1.getValue(ASMA, BRANCHES));
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(ASMA, FEUILLES));
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(ASMA, MUR));
		assertEquals(ArrayLogigramValue.NEG, c1.getValue(ASMA, PIERRE));
	}

public void testStrategyFillIfAllNByCol() {
	
	Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
	domPersons.add(new AxeValue<String>(ASMA));
	domPersons.add(new AxeValue<String>(BLANDINE));
	domPersons.add(new AxeValue<String>(CELIA));
	domPersons.add(new AxeValue<String>(FLORENT));
	domPersons.add(new AxeValue<String>(LOICK));
	
	Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
	domCaches.add(new AxeValue<String>(ARBRE));
	domCaches.add(new AxeValue<String>(BRANCHES));
	domCaches.add(new AxeValue<String>(FEUILLES));
	domCaches.add(new AxeValue<String>(MUR));
	domCaches.add(new AxeValue<String>(PIERRE));
	

	Axe axeLine = new AxeOrd<>("worldLine");
	Axe axeCol = new AxeOrd<>("worldCol");
	List<Axe<AxeValue>> axes = new ArrayList<>();
	axes.add(axeLine);
	axes.add(axeCol);
	ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c1 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domPersons, domCaches, new CoordinatesXDByIndices(axes, new int[] {0, 0}));
	
	c1.setValue(ArrayLogigramValue.NEG, ASMA, FEUILLES);
	c1.setValue(ArrayLogigramValue.NEG, ASMA, MUR);
	c1.setValue(ArrayLogigramValue.NEG, ASMA, PIERRE);
	c1.setValue(ArrayLogigramValue.NEG, ASMA, ARBRE);
	
	Strategy s =  new Strategy(new History(), null);
	s.strategyFillIfAllNByCol(Collections.singletonList(c1));
	Draw.draw(c1);
	
	assertEquals(ArrayLogigramValue.OK, c1.getValue(ASMA, BRANCHES));
}


public void testStrategyFillIfMaxOkByLineByCountN() {
	
	Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
	domPersons.add(new AxeValue<String>(ASMA));
	domPersons.add(new AxeValue<String>(BLANDINE));
	domPersons.add(new AxeValue<String>(CELIA));
	domPersons.add(new AxeValue<String>(FLORENT));
	domPersons.add(new AxeValue<String>(LOICK));
	domPersons.add(new AxeValue<String>(BRUNO));
	
	Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
	domCaches.add(new AxeValue<String>(ARBRE));
	domCaches.add(new AxeValue<String>(BRANCHES));
	domCaches.add(new AxeValue<String>(FEUILLES));
	domCaches.add(new AxeValue<String>(MUR));
	

	Axe axeLine = new AxeOrd<>("worldLine");
	Axe axeCol = new AxeOrd<>("worldCol");
	List<Axe<AxeValue>> axes = new ArrayList<>();
	axes.add(axeLine);
	axes.add(axeCol);
	ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c1 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domPersons, domCaches, new CoordinatesXDByIndices(axes, new int[] {0, 0}));

    //  playerH playerJ player1 guiltyCards 
    //Poignard EMPTY NEG NEG EMPTY 
	c1.setValue(ArrayLogigramValue.EMPTY, ASMA, ARBRE);
	c1.setValue(ArrayLogigramValue.NEG, ASMA, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, ASMA, FEUILLES);
	c1.setValue(ArrayLogigramValue.EMPTY, ASMA, MUR);

	//Corde NEG OK NEG NEG 
	c1.setValue(ArrayLogigramValue.NEG, BLANDINE, ARBRE);
	c1.setValue(ArrayLogigramValue.OK, BLANDINE, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, BLANDINE, FEUILLES);
	c1.setValue(ArrayLogigramValue.NEG, BLANDINE, MUR);
	
	//Barre de fer OK NEG NEG NEG 
	c1.setValue(ArrayLogigramValue.OK, CELIA, ARBRE);
	c1.setValue(ArrayLogigramValue.NEG, CELIA, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, CELIA, FEUILLES);
	c1.setValue(ArrayLogigramValue.NEG, CELIA, MUR);
	
	//Clé Anglaise OK NEG NEG NEG 
	c1.setValue(ArrayLogigramValue.OK, FLORENT, ARBRE);
	c1.setValue(ArrayLogigramValue.NEG, FLORENT, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, FLORENT, FEUILLES);
	c1.setValue(ArrayLogigramValue.NEG, FLORENT, MUR);

	//Révolver NEG NEG OK NEG 
	c1.setValue(ArrayLogigramValue.NEG, LOICK, ARBRE);
	c1.setValue(ArrayLogigramValue.NEG, LOICK, BRANCHES);
	c1.setValue(ArrayLogigramValue.OK, LOICK, FEUILLES);
	c1.setValue(ArrayLogigramValue.NEG, LOICK, MUR);

	//Chandelier NEG NEG OK NEG 
	c1.setValue(ArrayLogigramValue.NEG, BRUNO, ARBRE);
	c1.setValue(ArrayLogigramValue.NEG, BRUNO, BRANCHES);
	c1.setValue(ArrayLogigramValue.OK, BRUNO, FEUILLES);
	c1.setValue(ArrayLogigramValue.NEG, BRUNO, MUR);


	
	Strategy s =  new Strategy(new History(), null);
	s.strategyFillIfMaxOkByLineByCountN(Collections.singletonList(c1), new int[] {-1, -1, -1, 1});
	Draw.draw(c1);
	
	assertEquals(ArrayLogigramValue.OK, c1.getValue(ASMA, MUR));
}

public void testStrategyFillIfMaxOkByLineByCountN2() {
	
	Axe<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ENFANT);
	domPersons.add(new AxeValue<String>(ASMA));
	domPersons.add(new AxeValue<String>(BLANDINE));
	domPersons.add(new AxeValue<String>(CELIA));
	domPersons.add(new AxeValue<String>(FLORENT));
	domPersons.add(new AxeValue<String>(LOICK));
	domPersons.add(new AxeValue<String>(BRUNO));
	
	Axe<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(CACHETTE);
	domCaches.add(new AxeValue<String>(ARBRE));
	domCaches.add(new AxeValue<String>(BRANCHES));
	domCaches.add(new AxeValue<String>(FEUILLES));
	domCaches.add(new AxeValue<String>(MUR));
	

	Axe axeLine = new AxeOrd<>("worldLine");
	Axe axeCol = new AxeOrd<>("worldCol");
	List<Axe<AxeValue>> axes = new ArrayList<>();
	axes.add(axeLine);
	axes.add(axeCol);
	ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c1 = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domPersons, domCaches, new CoordinatesXDByIndices(axes, new int[] {0, 0}));

//	playerH playerJ player1 guiltyCards 
//	Mlle Rose EMPTY EMPTY NEG EMPTY 
	c1.setValue(ArrayLogigramValue.EMPTY, ASMA, ARBRE);
	c1.setValue(ArrayLogigramValue.EMPTY, ASMA, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, ASMA, FEUILLES);
	c1.setValue(ArrayLogigramValue.EMPTY, ASMA, MUR);

	//Prof. Violet EMPTY EMPTY NEG EMPTY 
	c1.setValue(ArrayLogigramValue.EMPTY, BLANDINE, ARBRE);
	c1.setValue(ArrayLogigramValue.EMPTY, BLANDINE, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, BLANDINE, FEUILLES);
	c1.setValue(ArrayLogigramValue.EMPTY, BLANDINE, MUR);
	
	//Mme Pervenche EMPTY EMPTY NEG EMPTY 
	c1.setValue(ArrayLogigramValue.EMPTY, CELIA, ARBRE);
	c1.setValue(ArrayLogigramValue.EMPTY, CELIA, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, CELIA, FEUILLES);
	c1.setValue(ArrayLogigramValue.EMPTY, CELIA, MUR);
	
	//M. Olive EMPTY EMPTY NEG EMPTY 
	c1.setValue(ArrayLogigramValue.EMPTY, FLORENT, ARBRE);
	c1.setValue(ArrayLogigramValue.EMPTY, FLORENT, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, FLORENT, FEUILLES);
	c1.setValue(ArrayLogigramValue.EMPTY, FLORENT, MUR);

	//Dr. Orchidée EMPTY EMPTY NEG EMPTY 
	c1.setValue(ArrayLogigramValue.EMPTY, LOICK, ARBRE);
	c1.setValue(ArrayLogigramValue.EMPTY, LOICK, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, LOICK, FEUILLES);
	c1.setValue(ArrayLogigramValue.EMPTY, LOICK, MUR);

	//Col. Moutarde EMPTY EMPTY NEG EMPTY 
	c1.setValue(ArrayLogigramValue.EMPTY, BRUNO, ARBRE);
	c1.setValue(ArrayLogigramValue.EMPTY, BRUNO, BRANCHES);
	c1.setValue(ArrayLogigramValue.NEG, BRUNO, FEUILLES);
	c1.setValue(ArrayLogigramValue.EMPTY, BRUNO, MUR);


	
	Strategy s =  new Strategy(new History(), null);
	s.strategyFillIfMaxOkByLineByCountN(Collections.singletonList(c1), new int[] {-1, -1, -1, 1});
	Draw.draw(c1);
	
	assertEquals(ArrayLogigramValue.EMPTY, c1.getValue(ASMA, ARBRE));
}





}
