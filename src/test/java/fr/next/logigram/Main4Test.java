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

public class Main4Test extends TestCase  {

	
	public static final String EQUIPE = "equipe";
	private static final String PARIS = "paris";
	private static final String MARSEILLE = "marseille";
	private static final String MANCHESTER = "manschester";
	private static final String MADRID = "madrid";
	private static final String BARCELONE = "barcelone";
	public static final String COULEUR = "Couleur";
	private static final String VERTE = "verte";
	private static final String ROUGE = "rouge";
	private static final String JAUNE = "jaune";
	private static final String BLEUE = "bleue";
	private static final String BLANCHE = "blanche";
	public static final String BUTS = "Buts";
	private static final String _5 = "5";
	private static final String _4 = "4";
	private static final String _3 = "3";
	private static final String _2 = "2";
	private static final String _1 = "1";
	public static final String ADO = "ado";
	private static final String JOAO = "joao";
	private static final String FLORIAN = "florian";
	private static final String DYLAN = "dylan";
	private static final String CLEM = "Clem";
	private static final String CED = "ced";

	public void testMain() {
		
		List<Axe<AxeValue<String>>> domains = new ArrayList<>();
		
		AxeOrd<AxeValue<String>> domPersons = new AxeOrd<AxeValue<String>>(ADO);
		domPersons.add(new AxeValue<String>(CED));
		domPersons.add(new AxeValue<String>(CLEM));
		domPersons.add(new AxeValue<String>(DYLAN));
		domPersons.add(new AxeValue<String>(FLORIAN));
		domPersons.add(new AxeValue<String>(JOAO));
		domains.add(domPersons);
		
		AxeOrd<AxeValue<String>> domCaches = new AxeOrd<AxeValue<String>>(BUTS);
		domCaches.add(new AxeValue<String>(_1));
		domCaches.add(new AxeValue<String>(_2));
		domCaches.add(new AxeValue<String>(_3));
		domCaches.add(new AxeValue<String>(_4));
		domCaches.add(new AxeValue<String>(_5));
		domains.add(domCaches);
		
		AxeOrd<AxeValue<String>> domDuree = new AxeOrd<AxeValue<String>>(COULEUR);
		domDuree.add(new AxeValue<String>(BLANCHE));
		domDuree.add(new AxeValue<String>(BLEUE));
		domDuree.add(new AxeValue<String>(JAUNE));
		domDuree.add(new AxeValue<String>(ROUGE));
		domDuree.add(new AxeValue<String>(VERTE));
		domains.add(domDuree);
		
		
		AxeOrd<AxeValue<String>> domClasse = new AxeOrd<AxeValue<String>>(EQUIPE);
		domClasse.add(new AxeValue<String>(BARCELONE));
		domClasse.add(new AxeValue<String>(MADRID));
		domClasse.add(new AxeValue<String>(MANCHESTER));
		domClasse.add(new AxeValue<String>(MARSEILLE));
		domClasse.add(new AxeValue<String>(PARIS));
		domains.add(domClasse);
		
		
		Array2DWorld cubeWorld = new Array2DWorld(domains);
		Logigram logigram = new Logigram(new History(), cubeWorld);
		Array2DOrd<Array2DOrdValue, String> cPC = cubeWorld.getArray2D(domPersons, domCaches);
		cPC.setValue(CED, _5, Array2DOrdValue.NEG);
		cPC.setValue(CLEM, _1, Array2DOrdValue.NEG);
		cPC.setValue(CLEM, _2, Array2DOrdValue.NEG);
		cPC.setValue(DYLAN, _4, Array2DOrdValue.NEG);
		cPC.setValue(DYLAN, _5, Array2DOrdValue.NEG);
		cPC.setValue(JOAO, _1, Array2DOrdValue.NEG);
		
		Array2DOrd<Array2DOrdValue, String> cPD = cubeWorld.getArray2D(domPersons, domDuree);
		cPD.setValue(DYLAN, BLEUE, Array2DOrdValue.NEG);
		cPD.setValue(DYLAN, VERTE, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cPCl = cubeWorld.getArray2D(domPersons, domClasse);
		cPCl.setValue(FLORIAN, PARIS, Array2DOrdValue.NEG);
		cPCl.setValue(JOAO, PARIS, Array2DOrdValue.NEG);
		cPCl.setValue(DYLAN, BARCELONE, Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cClC = cubeWorld.getArray2D(domClasse, domCaches);
		cClC.setValue(BARCELONE, _1, Array2DOrdValue.NEG);
		cClC.setValue(BARCELONE, _2, Array2DOrdValue.NEG);
		cClC.setValue(MARSEILLE, _5, Array2DOrdValue.NEG);
		cClC.setValue(PARIS, _1, Array2DOrdValue.NEG);
		cClC.setValue(PARIS, _2, Array2DOrdValue.NEG);
		
		Array2DOrd<Array2DOrdValue, String> cClD = cubeWorld.getArray2D(domClasse, domDuree);
		cClD.setValue(BARCELONE, VERTE, Array2DOrdValue.NEG);
		cClD.setValue(MANCHESTER, BLEUE, Array2DOrdValue.NEG);
		cClD.setValue(MARSEILLE, ROUGE, Array2DOrdValue.NEG);
		
		Array2DOrd<Array2DOrdValue, String> cDC = cubeWorld.getArray2D(domDuree, domCaches);
		cDC.setValue(BLANCHE, _1, Array2DOrdValue.NEG);
		cDC.setValue(BLANCHE, _4, Array2DOrdValue.NEG);
		cDC.setValue(BLANCHE, _5, Array2DOrdValue.NEG);
		cDC.setValue(BLEUE, _4, Array2DOrdValue.NEG);
		cDC.setValue(BLEUE, _5, Array2DOrdValue.NEG);
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

	

}
