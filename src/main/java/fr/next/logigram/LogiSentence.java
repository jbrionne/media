package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.logigram.Array2DOrdValue;
import fr.next.logigram.process.Possibility;

public class LogiSentence {

	private Array2DWorld array2DWorld;
	
	public LogiSentence(Array2DWorld array2DWorld) {
		super();
		this.array2DWorld = array2DWorld;
	}
	
	public void isNot(AxeValue<String> a, AxeValue<String> b) {
		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2DSpecific(a, b);
		c.setValue(a.getValue(), b.getValue(), Array2DOrdValue.NEG);
	}

	public void hasMoreThan(AxeValue<String> aEl, Axe<AxeValue<String>> dB, AxeValue<String> bEl) {
		Axe<AxeValue<String>> domainWithA = aEl.getAxe();
		String a = aEl.getValue();

		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		String b = bEl.getValue();

		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2D(domainWithA, domainWithB);
		c.setValue(a, b, Array2DOrdValue.NEG);

		Array2DOrd<Array2DOrdValue, String> cB = array2DWorld.getArray2D(domainWithA, dB);
		cB.setValue(a, dB.getElements().get(dB.getElements().size() - 1).getValue(), Array2DOrdValue.NEG);

		
		Array2DOrd<Array2DOrdValue, String> cC = array2DWorld.getArray2D(bEl.getAxe(), dB);
		Array2DOrdValue[] cas = cB.getLine(aEl.findIndex());
		for(int index0 = cas.length - 1; index0 >= 0; index0--) {
			Array2DOrdValue ca = cas[index0];
			cC.setValue(bEl.findIndex(), index0, Array2DOrdValue.NEG);
			if(!ca.equals(Array2DOrdValue.NEG)) {
				break;
			}
		}
		
		int indexOk = getIndexLineOkFromCol(b, domainWithB, c);

		if (indexOk != -1) {
			String s = c.getAxeLine().getElements().get(indexOk).getValue();
			Array2DOrdValue[] line = cB.getLine(AxeOrdNum.findIndex(s, domainWithA));
			int index = 0;
			for (Array2DOrdValue ca : line) {
				if (ca.equals(Array2DOrdValue.NEG)) {
					cB.setValue(a, dB.getElements().get(index).getValue(), Array2DOrdValue.NEG);
				}
				if (ca.equals(Array2DOrdValue.OK)) {
					cB.setValue(a, dB.getElements().get(index).getValue(), Array2DOrdValue.NEG);
					break;
				}
				if (ca.equals(Array2DOrdValue.EMPTY)) {
					break;
				}
				index++;
			}
		} else {
			//equation
			System.out.println(aEl.getValue() + "[" + aEl.getAxe().toString()  + "] (" + aEl.getAxe().toString() + ")" + " >[" + dB.toString() + "] " + bEl.getValue() +  "[" + bEl.getAxe().toString() + "] (" + aEl.getAxe().toString() + ")");
		}
	}

	public void hasLessThan(AxeValue<String> aEl, Axe<AxeValue<String>> dB, AxeValue<String> bEl) {
		Axe<AxeValue<String>> domainWithA = aEl.getAxe();
		String a = aEl.getValue();

		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		String b = bEl.getValue();

		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2D(domainWithA, domainWithB);
		c.setValue(a, b, Array2DOrdValue.NEG);

		Array2DOrd<Array2DOrdValue, String> cB = array2DWorld.getArray2D(domainWithA, dB);
		cB.setValue(a, dB.getElements().get(0).getValue(), Array2DOrdValue.NEG);
		
		
		Array2DOrd<Array2DOrdValue, String> cC = array2DWorld.getArray2D(bEl.getAxe(), dB);
		int index0 = 0;
		for(Array2DOrdValue ca : cB.getLine(aEl.findIndex())) {
			cC.setValue(bEl.findIndex(), index0, Array2DOrdValue.NEG);
			if(!ca.equals(Array2DOrdValue.NEG)) {
				break;
			}
			index0++;
		}
		
		
		int indexOk = getIndexLineOkFromCol(b, domainWithB, c);

		if (indexOk != -1) {
			String s = c.getAxeLine().getElements().get(indexOk).getValue();
			Array2DOrdValue[] line = cB.getLine(AxeOrdNum.findIndex(s, domainWithA));
			for (int index = line.length - 1; index >= 0; index--) {
				Array2DOrdValue ca = line[index];
				if (ca.equals(Array2DOrdValue.NEG)) {
					cB.setValue(a, dB.getElements().get(index).getValue(), Array2DOrdValue.NEG);
				}
				if (ca.equals(Array2DOrdValue.OK)) {
					cB.setValue(a, dB.getElements().get(index).getValue(), Array2DOrdValue.NEG);
					break;
				}
				if (ca.equals(Array2DOrdValue.EMPTY)) {
					break;
				}
			}
		} else {
			System.out.println(aEl.getValue() + "[" + aEl.getAxe().toString() + "] (" + aEl.getAxe().toString() + ")" + " <[" + dB.toString() + "] " + bEl.getValue() +  "[" + bEl.getAxe().toString()  + "] (" + aEl.getAxe().toString() + ")");
		}
	}


	private int getIndexLineOkFromCol(String b, Axe domainWithB, Array2DOrd<Array2DOrdValue, String> c) {
		Array2DOrdValue[] col = c.getCol(AxeOrdNum.findIndex(b, domainWithB));
		int indexOk = -1;
		int index = 0;
		for (Array2DOrdValue ca : col) {
			if (ca.equals(Array2DOrdValue.OK)) {
				indexOk = index;
				break;
			}
			index++;
		}
		return indexOk;
	}

	private int getIndexColOkFromLine(String b, Axe domainWithB, Array2DOrd<Array2DOrdValue, String> c) {
		Array2DOrdValue[] line = c.getLine(AxeOrdNum.findIndex(b, domainWithB));
		int indexOk = -1;
		int index = 0;
		for (Array2DOrdValue ca : line) {
			if (ca.equals(Array2DOrdValue.OK)) {
				indexOk = index;
				break;
			}
			index++;
		}
		return indexOk;
	}

	public void hasMinus(AxeValue<String> a1El, int val, AxeOrdNum<AxeValue<String>> dB, AxeValue<String> a5El) {

		Axe<AxeValue<String>> domainWithA = a1El.getAxe();
		String a1 = a1El.getValue();

		Axe<AxeValue<String>> domainWithABis = a5El.getAxe();
		String a5 = a5El.getValue();

		if (!domainWithA.equals(domainWithABis)) {
			throw new AssertionError();
		}
		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2D(domainWithA, dB);

		int max = Integer.valueOf(dB.getElements().get(dB.getElements().size() - 1).getValue());
		int current = max;
		int limit = max - val;
		boolean stop = false;
		while (!stop) {
			c.setValue(a1, String.valueOf(current), Array2DOrdValue.NEG);
			current = current - dB.getUnit();
			if (current == limit) {
				stop = true;
			}
		}

		int min = Integer.valueOf(dB.getElements().get(0).getValue());
		int currentNext = min;
		int limitMin = min + val;
		boolean stopNext = false;
		while (!stopNext) {
			c.setValue(a5, String.valueOf(currentNext), Array2DOrdValue.NEG);
			currentNext = currentNext + dB.getUnit();
			if (currentNext == limitMin) {
				stopNext = true;
			}
		}

		int indexOk = getIndexColOkFromLine(a5, domainWithA, c);

		if (indexOk != -1) {
			String s = c.getAxeCol().getElements().get(indexOk).getValue();
			int g = Integer.valueOf(s);
			int newVal = g - val;
			c.setValue(a1, String.valueOf(newVal), Array2DOrdValue.OK);
		} else {
			Array2DOrdValue[] line = c.getLine(AxeOrdNum.findIndex(a1, domainWithA));
			exclusionOverMax(val, a5, c, max, line);

			Array2DOrdValue[] lineNext = c.getLine(AxeOrdNum.findIndex(a5, domainWithA));
			exclusionOverMinus(val, a1, c, min, lineNext);
		}

	}

	private void exclusionOverMinus(int val, String a1, Array2DOrd<Array2DOrdValue, String> c, int min, Array2DOrdValue[] lineNext) {
		int indexColNext = 0;
		for (Array2DOrdValue ca : lineNext) {
			if (ca.equals(Array2DOrdValue.NEG)) {
				String s = c.getAxeCol().getElements().get(indexColNext).getValue();
				int g = Integer.valueOf(s);
				int newVal = g - val;
				if (newVal >= min) {
					c.setValue(a1, String.valueOf(newVal), Array2DOrdValue.NEG);
				}
			}
			indexColNext++;
		}
	}

	private void exclusionOverMax(int val, String a5, Array2DOrd<Array2DOrdValue, String> c, int max, Array2DOrdValue[] line) {
		int indexCol = 0;
		for (Array2DOrdValue ca : line) {
			if (ca.equals(Array2DOrdValue.NEG)) {
				String s = c.getAxeCol().getElements().get(indexCol).getValue();
				int g = Integer.valueOf(s);
				int newVal = g + val;
				if (newVal <= max) {
					c.setValue(a5, String.valueOf(newVal), Array2DOrdValue.NEG);
				}
			}
			indexCol++;
		}
	}
	
	public void xHasMore(AxeOrd<AxeValue<String>> dB, AxeValue<String> a1El,  AxeValue<String> a5El) {

		Axe<AxeValue<String>> domainWithA = a1El.getAxe();

		AxeOrdNum<AxeValue<String>> domainWithABis = (AxeOrdNum<AxeValue<String>>) a5El.getAxe();

		hasMore(a1El, a5El);
		
		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2D(dB, domainWithA);
		int indexOk = getIndexLineOkFromCol(a1El.getValue(), a1El.getAxe(), c);

		if (indexOk != -1) {
			String s = c.getAxeLine().getElements().get(indexOk).getValue();
			
			Array2DOrd<Array2DOrdValue, String> cA = array2DWorld.getArray2D(dB, domainWithABis);
			int val = Integer.valueOf(a5El.getValue());
			int min = Integer.valueOf(domainWithABis.getElements().get(0).getValue());
			int currentNext = min;
			int limitMin = val;
			boolean stopNext = false;
			while (!stopNext) {
				cA.setValue(s, String.valueOf(currentNext), Array2DOrdValue.NEG);
				currentNext = currentNext + domainWithABis.getUnit();
				if (currentNext == limitMin) {
					stopNext = true;
				}
			}
		}
	}


	public void hasMore(AxeValue<String> a2, AxeValue<String> b2) {
		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2DSpecific(a2, b2);
		int value = Integer.valueOf(b2.getValue());
		for (AxeValue<String> col : c.getAxeCol().getElements()) {
			int val = Integer.valueOf(col.getValue());
			if (val <= value) {
				c.setValue(a2.getValue(), String.valueOf(val), Array2DOrdValue.NEG);
			}
		}
	}
	
	public void hasLess(AxeValue<String> a2, AxeValue<String> b2) {
		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2DSpecific(a2, b2);
		int value = Integer.valueOf(b2.getValue());
		for (AxeValue<String> col : c.getAxeCol().getElements()) {
			int val = Integer.valueOf(col.getValue());
			if (val >= value) {
				c.setValue(a2.getValue(), String.valueOf(val), Array2DOrdValue.NEG);
			}
		}
	}

	public List<Possibility> hasDiffThan(AxeValue<String> aEl, int val, AxeOrdNum<AxeValue<String>> dB, AxeValue<String> bEl) {
		List<Possibility> possibilities = new ArrayList<>();

		Axe<AxeValue<String>> domainWithA = aEl.getAxe();
		String a = aEl.getValue();

		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		String b = bEl.getValue();

		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2D(domainWithA, domainWithB);
		c.setValue(a, b, Array2DOrdValue.NEG);

		int max = Integer.valueOf(dB.getElements().get(dB.getElements().size() - 1).getValue());
		int min = Integer.valueOf(dB.getElements().get(0).getValue());
		
		Array2DOrd<Array2DOrdValue, String> cX = array2DWorld.getArray2D(dB, bEl.getAxe());
		Array2DOrdValue[] cases = cX.getCol(bEl.findIndex());
		
		exclusionCol(val, max, min, cX, cases);
		
		Array2DOrd<Array2DOrdValue, String> cY = array2DWorld.getArray2D(domainWithA, dB);
		Array2DOrdValue[] casesY = cY.getLine(aEl.findIndex());
		
		exclusionLine(val, max, min, cY, casesY);

		int indexOk = getIndexLineOkFromCol(b, domainWithB, c);

		if (indexOk != -1) {
			String s = c.getAxeLine().getElements().get(indexOk).getValue();
			Array2DOrd<Array2DOrdValue, String> cAB = array2DWorld.getArray2D(domainWithA, dB);
			int indexDBOk = getIndexColOkFromLine(s, domainWithA, cAB);
			if (indexDBOk != -1) {
				String ref = cAB.getAxeCol().getElements().get(indexDBOk).getValue();
				int g = Integer.valueOf(ref);
				int newValPlus = g + val;
				int newValMinus = g - val;

				if (newValPlus <= max && newValPlus >= min) {
					if (!cAB.getValue(a, String.valueOf(newValPlus)).equals(Array2DOrdValue.NEG)
							&& !cAB.getValue(a, String.valueOf(newValPlus)).equals(Array2DOrdValue.OK)) {
						possibilities.add(new Possibility(a, String.valueOf(newValPlus), Array2DOrdValue.OK));
					}
				}
				if (newValMinus <= max && newValMinus >= min) {
					if (!cAB.getValue(a, String.valueOf(newValMinus)).equals(Array2DOrdValue.NEG)
							&& !cAB.getValue(a, String.valueOf(newValMinus)).equals(Array2DOrdValue.OK)) {
						possibilities.add(new Possibility(a, String.valueOf(newValMinus), Array2DOrdValue.OK));
					}
				}
				if (possibilities.size() == 1) {
					Possibility solution = possibilities.get(0);
					cAB.setValue(solution.getLine(), solution.getCol(), solution.getValue());
				}
			} else {
				exclusion(a, val, domainWithA, max, min, s, cAB);

				exclusion(s, val, domainWithA, max, min, a, cAB);
			}
		} else {
			// Possibilities...

			// TODO
		}
		return possibilities;
	}
	
	private void exclusionCol(int val, int max, int min, Array2DOrd<Array2DOrdValue, String> cX, Array2DOrdValue[] cases) {
		for(int i = 0; i < cases.length; i++) {
			int valX = Integer.valueOf(cX.getAxeLine().getElements().get(i).getValue());
			if(valX + val > max && valX - val < min) {
				cases[i] = Array2DOrdValue.NEG;
			}
		}
	}
	
	private void exclusionLine(int val, int max, int min, Array2DOrd<Array2DOrdValue, String> cX, Array2DOrdValue[] cases) {
		for(int i = 0; i < cases.length; i++) {
			int valX = Integer.valueOf(cX.getAxeCol().getElements().get(i).getValue());
			if(valX + val > max && valX - val < min) {
				cases[i] = Array2DOrdValue.NEG;
			}
		}
	}

	private void exclusion(String a, int val, Axe domainWithA, int max, int min, String s, Array2DOrd<Array2DOrdValue, String> cAB) {
		Array2DOrdValue[] line = cAB.getLine(AxeOrdNum.findIndex(s, domainWithA));
		int index = 0;
		for (Array2DOrdValue ca : line) {
			if (ca.equals(Array2DOrdValue.NEG)) {
				String ref = cAB.getAxeCol().getElements().get(index).getValue();
				int g = Integer.valueOf(ref);
				int newValPlus = g + val;
				int newValMinus = g - val;
				if (newValMinus <= max && newValMinus >= min) {
					cAB.setValue(a, String.valueOf(newValMinus), Array2DOrdValue.NEG);
				}
				if (newValPlus <= max && newValPlus >= min) {
					cAB.setValue(a, String.valueOf(newValPlus), Array2DOrdValue.NEG);
				}

			}
			index++;
		}
	}

	public void hasMinusThan(AxeOrd<AxeValue<String>> domainWithA, AxeValue<String> bEl, int i, AxeOrdNum<AxeValue<String>> dD, AxeValue<String> xEl) {

		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		String b = bEl.getValue();
		Axe<AxeValue<String>> domainWithX = xEl.getAxe();
		String x = xEl.getValue();

		Array2DOrd<Array2DOrdValue, String> c3 = array2DWorld.getArray2DSpecific(xEl, bEl);
		c3.setValue(xEl.findIndex(), bEl.findIndex(), Array2DOrdValue.NEG);
		
		Array2DOrd<Array2DOrdValue, String> c7 = array2DWorld.getArray2D(dD, bEl.getAxe());
		int max = Integer.valueOf(dD.getElements().get(dD.getElements().size() - 1).getValue());
		List<AxeValue<String>> els = c7.getAxeLine().getElements();
		for(int myIndex = els.size() - 1; myIndex >= 0; myIndex--) {
			AxeValue<String> e = els.get(myIndex);
			int val = Integer.valueOf(e.getValue());
			if(val > max - i) {
				c7.setValue(e.findIndex(), bEl.findIndex(), Array2DOrdValue.NEG);
			}
		}
		
		Array2DOrd<Array2DOrdValue, String> c5 = array2DWorld.getArray2D(dD, domainWithX);
		int min = Integer.valueOf(dD.getElements().get(0).getValue());
		List<AxeValue<String>> elsMin = c7.getAxeLine().getElements();
		for(int myIndex = 0; myIndex <= elsMin.size() - 1; myIndex++) {
			AxeValue<String> e = elsMin.get(myIndex);
			int val = Integer.valueOf(e.getValue());
			if(val < min + i) {
				c5.setValue(e.findIndex(), xEl.findIndex(), Array2DOrdValue.NEG);
			}
		}

		Array2DOrd<Array2DOrdValue, String> c2 = array2DWorld.getArray2D(domainWithA, domainWithB);
		int indexOk2 = getIndexLineOkFromCol(b, domainWithB, c2);
		String s2 = null;
		if (indexOk2 != -1) {
			s2 = c2.getAxeLine().getElements().get(indexOk2).getValue();
			Array2DOrd<Array2DOrdValue, String> cAD2 = array2DWorld.getArray2D(domainWithA, xEl.getAxe());
			cAD2.setValue(s2, x, Array2DOrdValue.NEG);

			Array2DOrd<Array2DOrdValue, String> cA2 = array2DWorld.getArray2D(domainWithA, dD);
			int valMin = Integer.valueOf(cA2.getAxeCol().getElements().get(cA2.getAxeCol().getElements().size() - 1).getValue()) - i;
			for(AxeValue<String> e : cA2.getAxeCol().getElements()) {
				int currentCol = Integer.valueOf(e.getValue());
				if(currentCol > valMin) {
					cA2.setValue(s2, String.valueOf(currentCol),
							Array2DOrdValue.NEG);
				}
			}
		}

		Array2DOrd<Array2DOrdValue, String> c = array2DWorld.getArray2D(domainWithA, domainWithX);
		int indexOk = getIndexLineOkFromCol(x, domainWithX, c);
		String s = null;
		if (indexOk != -1) {
			s = c.getAxeLine().getElements().get(indexOk).getValue();
			Array2DOrd<Array2DOrdValue, String> cAD = array2DWorld.getArray2D(domainWithA, bEl.getAxe());
			cAD.setValue(s, b, Array2DOrdValue.NEG);

			Array2DOrd<Array2DOrdValue, String> cA = array2DWorld.getArray2D(domainWithA, dD);
			int valMax = Integer.valueOf(cA.getAxeCol().getElements().get(0).getValue()) + i;
			for(AxeValue<String> e : cA.getAxeCol().getElements()) {
				int currentCol = Integer.valueOf(e.getValue());
				if(currentCol < valMax) {
					cA.setValue(s, String.valueOf(currentCol),
							Array2DOrdValue.NEG);
				}
			}
		}
		if (indexOk != -1 && indexOk2 != -1) {
			Array2DOrd<Array2DOrdValue, String> c4 = array2DWorld.getArray2D(domainWithA, dD);
			int indexOk23 = getIndexColOkFromLine(s2, domainWithA, c4);
			if (indexOk23 != -1) {
				int newVal = Integer.valueOf(dD.getElements().get(indexOk23).getValue()) + i;
				c4.setValue(s, String.valueOf(newVal), Array2DOrdValue.OK);
			}
			
			int indexOk24 = getIndexColOkFromLine(s, domainWithA, c4);
			if (indexOk24 != -1) {
				int newVal = Integer.valueOf(dD.getElements().get(indexOk24).getValue()) - i;
				c4.setValue(s2, String.valueOf(newVal), Array2DOrdValue.OK);
			}
		}

	}
}
