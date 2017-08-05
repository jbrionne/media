package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.process.History;
import junit.framework.TestCase;

public class Main5Test extends TestCase  {

	
	public static final String D = "D";
	private static final AxeValue<String> D5 = new AxeValue<String>("46");
	private static final AxeValue<String> D4 = new AxeValue<String>("42");
	private static final AxeValue<String> D3 = new AxeValue<String>("38");
	private static final AxeValue<String> D2 = new AxeValue<String>("34");
	private static final AxeValue<String> D1 = new AxeValue<String>("30");
	public static final String C = "C";
	private static final AxeValue<String> C5 = new AxeValue<String>("c5");
	private static final AxeValue<String> C4 = new AxeValue<String>("c4");
	private static final AxeValue<String> C3 = new AxeValue<String>("c3");
	private static final AxeValue<String> C2 = new AxeValue<String>("c2");
	private static final AxeValue<String> C1 = new AxeValue<String>("c1");
	public static final String B = "B";
	private static final AxeValue<String> B5 = new AxeValue<String>("36");
	private static final AxeValue<String> B4 = new AxeValue<String>("34");
	private static final AxeValue<String> B3 = new AxeValue<String>("32");
	private static final AxeValue<String> B2 = new AxeValue<String>("30");
	private static final AxeValue<String> B1 = new AxeValue<String>("28");
	public static final String A = "A";
	private static final AxeValue<String> A5 = new AxeValue<String>("a5");
	private static final AxeValue<String> A4 = new AxeValue<String>("a4");
	private static final AxeValue<String> A3 = new AxeValue<String>("a3");
	private static final AxeValue<String> A2 = new AxeValue<String>("a2");
	private static final AxeValue<String> A1 = new AxeValue<String>("a1");

	public void testMain() {
		
		List<Axe<AxeValue<String>>> domains = new ArrayList<>();
		
		AxeOrd<AxeValue<String>> dA = new AxeOrd<AxeValue<String>>(A);
		dA.add(A1);
		dA.add(A2);
		dA.add(A3);
		dA.add(A4);
		dA.add(A5);
		domains.add(dA);
		
		AxeOrdNum<AxeValue<String>> dB = new AxeOrdNum<AxeValue<String>>(B, 2);
		dB.add(B1);
		dB.add(B2);
		dB.add(B3);
		dB.add(B4);
		dB.add(B5);
		domains.add(dB);
		
		AxeOrd<AxeValue<String>> dC = new AxeOrd<AxeValue<String>>(C);
		dC.add(C1);
		dC.add(C2);
		dC.add(C3);
		dC.add(C4);
		dC.add(C5);
		domains.add(dC);
		
		
		AxeOrdNum<AxeValue<String>> dD = new AxeOrdNum<AxeValue<String>>(D, 4);
		dD.add(D1);
		dD.add(D2);
		dD.add(D3);
		dD.add(D4);
		dD.add(D5);
		domains.add(dD);
		
		
		
		Array2DWorld cubeWorld = new Array2DWorld(domains);
		Logigram logigram = new Logigram(new History(), cubeWorld);
		LogiSentence logiSentence = new LogiSentence(cubeWorld);
		
		//hasMore
//		Cube cPC = cubeWorld.getCube(dA, dB);
//		cPC.setValue(A2, B1, Cube.NEG);
//		cPC.setValue(A2, B2, Cube.NEG);
//		cPC.setValue(A2, B3, Cube.OK);
//		
//		Cube cPCl = cubeWorld.getCube(dA, dD);
//		cPCl.setValue(A2, D5, Cube.OK);
		
		//hasLess
//		Cube cPC = cubeWorld.getCube(dA, dB);
//		cPC.setValue(A5, B5, Cube.NEG);
//		cPC.setValue(A5, B4, Cube.NEG);
//		cPC.setValue(A5, B3, Cube.OK);
//		
//		Cube cPD = cubeWorld.getCube(dA, dC);
//		cPD.setValue(A5, C5, Cube.OK);		
		
		
		//hasMinus
//		Cube cPC = cubeWorld.getCube(dA, dB);
//		cPC.setValue(A5, B3, Cube.NEG);
		
		
		//hasMore
		
		//hasDiffthan
//		Cube cPC = cubeWorld.getCube(dA, dB);
//		cPC.setValue(A1, B5, Cube.OK);
		
		//first ok
//		Cube cPCl = cubeWorld.getCube(dA, dD);
//		cPCl.setValue(A1, D2, Cube.OK);
		
		//without ok
//		Cube cPCl = cubeWorld.getCube(dA, dD);
//		cPCl.setValue(A2, D2, Cube.NEG);
		
//		hasMinusThan
//		Cube cPCl = cubeWorld.getCube(dA, dB);
//		cPCl.setValue(A2, B2, Cube.OK);
//		
//		Cube cPCl3 = cubeWorld.getCube(dA, dC);
//		cPCl3.setValue(A4, C2, Cube.OK);
//		
//		Cube cPCl2 = cubeWorld.getCube(dA, dD);
//		cPCl2.setValue(A4, D3, Cube.OK);
		
		//xHasMore
//		Cube cPCl3 = cubeWorld.getCube(dA, dC);
//		cPCl3.setValue(A4, C4, Cube.OK);
		
		
		for(int i = 0 ; i < 2; i++) {
		//1
			logiSentence.isNot(A3, C3);
			logiSentence.hasMoreThan(A3, dB, D5);
			logiSentence.hasLessThan(A3, dB, C5);
			logiSentence.isNot(D5, C5);
		
		//2
			logiSentence.isNot(A5, D2);
			logiSentence.hasMinus(A1, 4, dB, A5);
		
		//3
			logiSentence.hasMore(A2, B2);
			logiSentence.hasDiffThan(A2, 12, dD, B5);
		
		//4
			logiSentence.hasMinusThan(dA, B2, 8, dD, C2);
		
		
		//5
			logiSentence.hasLess(A4, D4);
			logiSentence.isNot(A4, C3);
		
		//6
			logiSentence.xHasMore(dA, C4, D3);
		}
		
		
		//TODO...cas par elmination 
		
//		Cube cPC = cubeWorld.getCube(dA, dB);
//		cPC.setValue(A1, B4, Cube.NEG);
//		cPC.setValue(A1, B5, Cube.NEG);
//		cPC.setValue(A2, B1, Cube.NEG);
//		cPC.setValue(A2, B2, Cube.NEG);
//		cPC.setValue(A2, B5, Cube.NEG);
//		cPC.setValue(A3, B1, Cube.NEG);
//		cPC.setValue(A3, B5, Cube.NEG);
//		cPC.setValue(A5, B1, Cube.NEG);
//		cPC.setValue(A5, B2, Cube.NEG);
//		
//		Cube cPD = cubeWorld.getCube(dA, dC);
//		cPD.setValue(A3, C3, Cube.NEG);
//		cPD.setValue(A3, C5, Cube.NEG);
//		cPD.setValue(A4, C3, Cube.NEG);
//		cPD.setValue(A4, C4, Cube.NEG);
//		
//		Cube cPCl = cubeWorld.getCube(dA, dD);
//		cPCl.setValue(A2, D2, Cube.NEG);
//		cPCl.setValue(A2, D3, Cube.NEG);
//		cPCl.setValue(A3, D5, Cube.NEG);
//		cPCl.setValue(A4, D4, Cube.NEG);
//		cPCl.setValue(A4, D5, Cube.NEG);
//		cPCl.setValue(A5, D2, Cube.NEG);
//		
//		Cube cClC = cubeWorld.getCube(dD, dB);
//		cClC.setValue(D3, B5, Cube.NEG);
//		cClC.setValue(D4, B2, Cube.NEG);
//		cClC.setValue(D5, B2, Cube.NEG);
//		cClC.setValue(D5, B4, Cube.NEG);
//		cClC.setValue(D5, B5, Cube.NEG);
//		
//		Cube cClD = cubeWorld.getCube(dD, dC);
//		cClD.setValue(D1, C2, Cube.NEG);
//		cClD.setValue(D1, C4, Cube.NEG);
//		cClD.setValue(D2, C2, Cube.NEG);
//		cClD.setValue(D2, C4, Cube.NEG);
//		cClD.setValue(D3, C4, Cube.NEG);
//		cClD.setValue(D5, C5, Cube.NEG);
//		
//		Cube cDC = cubeWorld.getCube(dC, dB);
//		cDC.setValue(C2, B2, Cube.NEG);
//		cDC.setValue(C4, B2, Cube.NEG);
//		cDC.setValue(C5, B1, Cube.NEG);
//		cDC.setValue(C5, B2, Cube.NEG);
		
		logigram.draw(9);
		
		logigram.doResolution();
		
		logigram.draw(9);
		
		logigram.getHistory().draw();
		
		System.out.println("END");
		

	}

	

}
