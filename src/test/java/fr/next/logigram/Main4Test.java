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
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPC = cubeWorld.getArray2D(domPersons, domCaches);
		cPC.setValue(ArrayLogigramValue.NEG, CED, _5);
		cPC.setValue(ArrayLogigramValue.NEG, CLEM, _1);
		cPC.setValue(ArrayLogigramValue.NEG, CLEM, _2);
		cPC.setValue(ArrayLogigramValue.NEG, DYLAN, _4);
		cPC.setValue(ArrayLogigramValue.NEG, DYLAN, _5);
		cPC.setValue(ArrayLogigramValue.NEG, JOAO, _1);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPD = cubeWorld.getArray2D(domPersons, domDuree);
		cPD.setValue(ArrayLogigramValue.NEG, DYLAN, BLEUE);
		cPD.setValue(ArrayLogigramValue.NEG, DYLAN, VERTE);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cPCl = cubeWorld.getArray2D(domPersons, domClasse);
		cPCl.setValue(ArrayLogigramValue.NEG, FLORIAN, PARIS);
		cPCl.setValue(ArrayLogigramValue.NEG, JOAO, PARIS);
		cPCl.setValue(ArrayLogigramValue.NEG, DYLAN, BARCELONE);
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cClC = cubeWorld.getArray2D(domClasse, domCaches);
		cClC.setValue(ArrayLogigramValue.NEG, BARCELONE, _1);
		cClC.setValue(ArrayLogigramValue.NEG, BARCELONE, _2);
		cClC.setValue(ArrayLogigramValue.NEG, MARSEILLE, _5);
		cClC.setValue(ArrayLogigramValue.NEG, PARIS, _1);
		cClC.setValue(ArrayLogigramValue.NEG, PARIS, _2);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cClD = cubeWorld.getArray2D(domClasse, domDuree);
		cClD.setValue(ArrayLogigramValue.NEG, BARCELONE, VERTE);
		cClD.setValue(ArrayLogigramValue.NEG, MANCHESTER, BLEUE);
		cClD.setValue(ArrayLogigramValue.NEG, MARSEILLE, ROUGE);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cDC = cubeWorld.getArray2D(domDuree, domCaches);
		cDC.setValue(ArrayLogigramValue.NEG, BLANCHE, _1);
		cDC.setValue(ArrayLogigramValue.NEG, BLANCHE, _4);
		cDC.setValue(ArrayLogigramValue.NEG, BLANCHE, _5);
		cDC.setValue(ArrayLogigramValue.NEG, BLEUE, _4);
		cDC.setValue(ArrayLogigramValue.NEG, BLEUE, _5);
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

	

}
