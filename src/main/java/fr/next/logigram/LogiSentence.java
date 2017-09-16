package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.impl.logigram.ArrayLogigramValue;
import fr.next.logigram.process.Possibility;

public class LogiSentence {

	private Array2DWorld array2DWorld;
	
	public LogiSentence(Array2DWorld array2DWorld) {
		super();
		this.array2DWorld = array2DWorld;
	}
	
	public void isNot(AxeValue<String> a, AxeValue<String> b) {
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2DSpecific(a, b);
		c.setValue(ArrayLogigramValue.NEG, a.getValue(), b.getValue());
	}

	public void hasMoreThan(AxeValue<String> aEl, Axe<AxeValue<String>> dB, AxeValue<String> bEl) {
		Axe<AxeValue<String>> domainWithA = aEl.getAxe();
		String a = aEl.getValue();

		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		String b = bEl.getValue();

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2D(domainWithA, domainWithB);
		c.setValue(ArrayLogigramValue.NEG, a, b);

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cB = array2DWorld.getArray2D(domainWithA, dB);
		cB.setValue(ArrayLogigramValue.NEG, a, dB.getElements().get(dB.getElements().size() - 1).getValue());

		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cC = array2DWorld.getArray2D(bEl.getAxe(), dB);
		List<ArrayLogigramValue> cas = cB.getValuesForAnAxe(0, aEl.findIndex());
		for(int index0 = cas.size() - 1; index0 >= 0; index0--) {
			ArrayLogigramValue ca = cas.get(index0);
			cC.setValueByIndices(ArrayLogigramValue.NEG, bEl.findIndex(), index0);
			if(!ca.equals(ArrayLogigramValue.NEG)) {
				break;
			}
		}
		
		int indexOk = getIndexLineOkFromCol(b, domainWithB, c);

		if (indexOk != -1) {
			String s = c.getAxe(0).getElements().get(indexOk).getValue();
			List<ArrayLogigramValue> line = cB.getValuesForAnAxe(0, Axe.findIndex(s, domainWithA));
			int index = 0;
			for (ArrayLogigramValue ca : line) {
				if (ca.equals(ArrayLogigramValue.NEG)) {
					cB.setValue(ArrayLogigramValue.NEG, a, dB.getElements().get(index).getValue());
				}
				if (ca.equals(ArrayLogigramValue.OK)) {
					cB.setValue(ArrayLogigramValue.NEG, a, dB.getElements().get(index).getValue());
					break;
				}
				if (ca.equals(ArrayLogigramValue.EMPTY)) {
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

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2D(domainWithA, domainWithB);
		c.setValue(ArrayLogigramValue.NEG, a, b);

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cB = array2DWorld.getArray2D(domainWithA, dB);
		cB.setValue(ArrayLogigramValue.NEG, a, dB.getElements().get(0).getValue());
		
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cC = array2DWorld.getArray2D(bEl.getAxe(), dB);
		int index0 = 0;
		for(ArrayLogigramValue ca : cB.getValuesForAnAxe(0, aEl.findIndex())) {
			cC.setValueByIndices(ArrayLogigramValue.NEG, bEl.findIndex(), index0);
			if(!ca.equals(ArrayLogigramValue.NEG)) {
				break;
			}
			index0++;
		}
		
		
		int indexOk = getIndexLineOkFromCol(b, domainWithB, c);

		if (indexOk != -1) {
			String s = c.getAxe(0).getElements().get(indexOk).getValue();
			List<ArrayLogigramValue> line = cB.getValuesForAnAxe(0, Axe.findIndex(s, domainWithA));
			for (int index = line.size() - 1; index >= 0; index--) {
				ArrayLogigramValue ca = line.get(index);
				if (ca.equals(ArrayLogigramValue.NEG)) {
					cB.setValue(ArrayLogigramValue.NEG, a, dB.getElements().get(index).getValue());
				}
				if (ca.equals(ArrayLogigramValue.OK)) {
					cB.setValue(ArrayLogigramValue.NEG, a, dB.getElements().get(index).getValue());
					break;
				}
				if (ca.equals(ArrayLogigramValue.EMPTY)) {
					break;
				}
			}
		} else {
			System.out.println(aEl.getValue() + "[" + aEl.getAxe().toString() + "] (" + aEl.getAxe().toString() + ")" + " <[" + dB.toString() + "] " + bEl.getValue() +  "[" + bEl.getAxe().toString()  + "] (" + aEl.getAxe().toString() + ")");
		}
	}


	private int getIndexLineOkFromCol(String b, Axe domainWithB, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c) {
		List<ArrayLogigramValue> col = c.getValuesForAnAxe(1, Axe.findIndex(b, domainWithB));
		int indexOk = -1;
		int index = 0;
		for (ArrayLogigramValue ca : col) {
			if (ca.equals(ArrayLogigramValue.OK)) {
				indexOk = index;
				break;
			}
			index++;
		}
		return indexOk;
	}

	private int getIndexColOkFromLine(String b, Axe domainWithB, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c) {
		List<ArrayLogigramValue> line = c.getValuesForAnAxe(0, Axe.findIndex(b, domainWithB));
		int indexOk = -1;
		int index = 0;
		for (ArrayLogigramValue ca : line) {
			if (ca.equals(ArrayLogigramValue.OK)) {
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
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2D(domainWithA, dB);

		int max = Integer.valueOf(dB.getElements().get(dB.getElements().size() - 1).getValue());
		int current = max;
		int limit = max - val;
		boolean stop = false;
		while (!stop) {
			c.setValue(ArrayLogigramValue.NEG, a1, String.valueOf(current));
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
			c.setValue(ArrayLogigramValue.NEG, a5, String.valueOf(currentNext));
			currentNext = currentNext + dB.getUnit();
			if (currentNext == limitMin) {
				stopNext = true;
			}
		}

		int indexOk = getIndexColOkFromLine(a5, domainWithA, c);

		if (indexOk != -1) {
			String s = c.getAxe(1).getElements().get(indexOk).getValue();
			int g = Integer.valueOf(s);
			int newVal = g - val;
			c.setValue(ArrayLogigramValue.OK, a1, String.valueOf(newVal));
		} else {
			List<ArrayLogigramValue> line = c.getValuesForAnAxe(0, Axe.findIndex(a1, domainWithA));
			exclusionOverMax(val, a5, c, max, line);

			List<ArrayLogigramValue> lineNext = c.getValuesForAnAxe(0, Axe.findIndex(a5, domainWithA));
			exclusionOverMinus(val, a1, c, min, lineNext);
		}

	}

	private void exclusionOverMinus(int val, String a1, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c, int min, List<ArrayLogigramValue> lineNext) {
		int indexColNext = 0;
		for (ArrayLogigramValue ca : lineNext) {
			if (ca.equals(ArrayLogigramValue.NEG)) {
				String s = c.getAxe(1).getElements().get(indexColNext).getValue();
				int g = Integer.valueOf(s);
				int newVal = g - val;
				if (newVal >= min) {
					c.setValue(ArrayLogigramValue.NEG, a1, String.valueOf(newVal));
				}
			}
			indexColNext++;
		}
	}

	private void exclusionOverMax(int val, String a5, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c, int max, List<ArrayLogigramValue> line) {
		int indexCol = 0;
		for (ArrayLogigramValue ca : line) {
			if (ca.equals(ArrayLogigramValue.NEG)) {
				String s = c.getAxe(1).getElements().get(indexCol).getValue();
				int g = Integer.valueOf(s);
				int newVal = g + val;
				if (newVal <= max) {
					c.setValue(ArrayLogigramValue.NEG, a5, String.valueOf(newVal));
				}
			}
			indexCol++;
		}
	}
	
	public void xHasMore(AxeOrd<AxeValue<String>> dB, AxeValue<String> a1El,  AxeValue<String> a5El) {

		Axe<AxeValue<String>> domainWithA = a1El.getAxe();

		AxeOrdNum<AxeValue<String>> domainWithABis = (AxeOrdNum<AxeValue<String>>) a5El.getAxe();

		hasMore(a1El, a5El);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2D(dB, domainWithA);
		int indexOk = getIndexLineOkFromCol(a1El.getValue(), a1El.getAxe(), c);

		if (indexOk != -1) {
			String s = c.getAxe(0).getElements().get(indexOk).getValue();
			
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cA = array2DWorld.getArray2D(dB, domainWithABis);
			int val = Integer.valueOf(a5El.getValue());
			int min = Integer.valueOf(domainWithABis.getElements().get(0).getValue());
			int currentNext = min;
			int limitMin = val;
			boolean stopNext = false;
			while (!stopNext) {
				cA.setValue(ArrayLogigramValue.NEG, s, String.valueOf(currentNext));
				currentNext = currentNext + domainWithABis.getUnit();
				if (currentNext == limitMin) {
					stopNext = true;
				}
			}
		}
	}


	public void hasMore(AxeValue<String> a2, AxeValue<String> b2) {
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2DSpecific(a2, b2);
		int value = Integer.valueOf(b2.getValue());
		for (AxeValue<String> col : c.getAxe(1).getElements()) {
			int val = Integer.valueOf(col.getValue());
			if (val <= value) {
				c.setValue(ArrayLogigramValue.NEG, a2.getValue(), String.valueOf(val));
			}
		}
	}
	
	public void hasLess(AxeValue<String> a2, AxeValue<String> b2) {
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2DSpecific(a2, b2);
		int value = Integer.valueOf(b2.getValue());
		for (AxeValue<String> col : c.getAxe(1).getElements()) {
			int val = Integer.valueOf(col.getValue());
			if (val >= value) {
				c.setValue(ArrayLogigramValue.NEG, a2.getValue(), String.valueOf(val));
			}
		}
	}

	public List<Possibility> hasDiffThan(AxeValue<String> aEl, int val, AxeOrdNum<AxeValue<String>> dB, AxeValue<String> bEl) {
		List<Possibility> possibilities = new ArrayList<>();

		Axe<AxeValue<String>> domainWithA = aEl.getAxe();
		String a = aEl.getValue();

		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		String b = bEl.getValue();

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2D(domainWithA, domainWithB);
		c.setValue(ArrayLogigramValue.NEG, a, b);

		int max = Integer.valueOf(dB.getElements().get(dB.getElements().size() - 1).getValue());
		int min = Integer.valueOf(dB.getElements().get(0).getValue());
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cX = array2DWorld.getArray2D(dB, bEl.getAxe());
		List<ArrayLogigramValue> cases = cX.getValuesForAnAxe(1, bEl.findIndex());
		
		exclusionCol(val, max, min, cX, cases);
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cY = array2DWorld.getArray2D(domainWithA, dB);
		List<ArrayLogigramValue> casesY = cY.getValuesForAnAxe(0, aEl.findIndex());
		
		exclusionLine(val, max, min, cY, casesY);

		int indexOk = getIndexLineOkFromCol(b, domainWithB, c);

		if (indexOk != -1) {
			String s = c.getAxe(0).getElements().get(indexOk).getValue();
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cAB = array2DWorld.getArray2D(domainWithA, dB);
			int indexDBOk = getIndexColOkFromLine(s, domainWithA, cAB);
			if (indexDBOk != -1) {
				String ref = cAB.getAxe(1).getElements().get(indexDBOk).getValue();
				int g = Integer.valueOf(ref);
				int newValPlus = g + val;
				int newValMinus = g - val;

				if (newValPlus <= max && newValPlus >= min) {
					if (!cAB.getValue(a, String.valueOf(newValPlus)).equals(ArrayLogigramValue.NEG)
							&& !cAB.getValue(a, String.valueOf(newValPlus)).equals(ArrayLogigramValue.OK)) {
						possibilities.add(new Possibility(a, String.valueOf(newValPlus), ArrayLogigramValue.OK));
					}
				}
				if (newValMinus <= max && newValMinus >= min) {
					if (!cAB.getValue(a, String.valueOf(newValMinus)).equals(ArrayLogigramValue.NEG)
							&& !cAB.getValue(a, String.valueOf(newValMinus)).equals(ArrayLogigramValue.OK)) {
						possibilities.add(new Possibility(a, String.valueOf(newValMinus), ArrayLogigramValue.OK));
					}
				}
				if (possibilities.size() == 1) {
					Possibility solution = possibilities.get(0);
					cAB.setValue(solution.getValue(), solution.getLine(), solution.getCol());
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
	
	private void exclusionCol(int val, int max, int min, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cX, List<ArrayLogigramValue> cases) {
		for(int i = 0; i < cases.size(); i++) {
			int valX = Integer.valueOf(cX.getAxe(0).getElements().get(i).getValue());
			if(valX + val > max && valX - val < min) {
				cX.setValueByIndices(ArrayLogigramValue.NEG, i, i);
				//cases[i] = ArrayLogigramValue.NEG;
				//TODO check
			}
		}
	}
	
	private void exclusionLine(int val, int max, int min, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cX, List<ArrayLogigramValue> cases) {
		for(int i = 0; i < cases.size(); i++) {
			int valX = Integer.valueOf(cX.getAxe(1).getElements().get(i).getValue());
			if(valX + val > max && valX - val < min) {
				cX.setValueByIndices(ArrayLogigramValue.NEG, i, i);
				//cases[i] = ArrayLogigramValue.NEG;
				//TODO check
			}
		}
	}

	private void exclusion(String a, int val, Axe domainWithA, int max, int min, String s, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cAB) {
		List<ArrayLogigramValue> line = cAB.getValuesForAnAxe(0, Axe.findIndex(s, domainWithA));
		int index = 0;
		for (ArrayLogigramValue ca : line) {
			if (ca.equals(ArrayLogigramValue.NEG)) {
				String ref = cAB.getAxe(1).getElements().get(index).getValue();
				int g = Integer.valueOf(ref);
				int newValPlus = g + val;
				int newValMinus = g - val;
				if (newValMinus <= max && newValMinus >= min) {
					cAB.setValue(ArrayLogigramValue.NEG, a, String.valueOf(newValMinus));
				}
				if (newValPlus <= max && newValPlus >= min) {
					cAB.setValue(ArrayLogigramValue.NEG, a, String.valueOf(newValPlus));
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

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c3 = array2DWorld.getArray2DSpecific(xEl, bEl);
		c3.setValueByIndices(ArrayLogigramValue.NEG, xEl.findIndex(), bEl.findIndex());
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c7 = array2DWorld.getArray2D(dD, bEl.getAxe());
		int max = Integer.valueOf(dD.getElements().get(dD.getElements().size() - 1).getValue());
		List<AxeValue<String>> els = c7.getAxe(0).getElements();
		for(int myIndex = els.size() - 1; myIndex >= 0; myIndex--) {
			AxeValue<String> e = els.get(myIndex);
			int val = Integer.valueOf(e.getValue());
			if(val > max - i) {
				c7.setValueByIndices(ArrayLogigramValue.NEG, e.findIndex(), bEl.findIndex());
			}
		}
		
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c5 = array2DWorld.getArray2D(dD, domainWithX);
		int min = Integer.valueOf(dD.getElements().get(0).getValue());
		List<AxeValue<String>> elsMin = c7.getAxe(0).getElements();
		for(int myIndex = 0; myIndex <= elsMin.size() - 1; myIndex++) {
			AxeValue<String> e = elsMin.get(myIndex);
			int val = Integer.valueOf(e.getValue());
			if(val < min + i) {
				c5.setValueByIndices(ArrayLogigramValue.NEG, e.findIndex(), xEl.findIndex());
			}
		}

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c2 = array2DWorld.getArray2D(domainWithA, domainWithB);
		int indexOk2 = getIndexLineOkFromCol(b, domainWithB, c2);
		String s2 = null;
		if (indexOk2 != -1) {
			s2 = c2.getAxe(0).getElements().get(indexOk2).getValue();
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cAD2 = array2DWorld.getArray2D(domainWithA, xEl.getAxe());
			cAD2.setValue(ArrayLogigramValue.NEG, s2, x);

			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cA2 = array2DWorld.getArray2D(domainWithA, dD);
			int valMin = Integer.valueOf(cA2.getAxe(1).getElements().get(cA2.getAxe(1).getElements().size() - 1).getValue()) - i;
			for(AxeValue<String> e : cA2.getAxe(1).getElements()) {
				int currentCol = Integer.valueOf(e.getValue());
				if(currentCol > valMin) {
					cA2.setValue(ArrayLogigramValue.NEG, s2,
							String.valueOf(currentCol));
				}
			}
		}

		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = array2DWorld.getArray2D(domainWithA, domainWithX);
		int indexOk = getIndexLineOkFromCol(x, domainWithX, c);
		String s = null;
		if (indexOk != -1) {
			s = c.getAxe(0).getElements().get(indexOk).getValue();
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cAD = array2DWorld.getArray2D(domainWithA, bEl.getAxe());
			cAD.setValue(ArrayLogigramValue.NEG, s, b);

			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cA = array2DWorld.getArray2D(domainWithA, dD);
			int valMax = Integer.valueOf(cA.getAxe(1).getElements().get(0).getValue()) + i;
			for(AxeValue<String> e : cA.getAxe(1).getElements()) {
				int currentCol = Integer.valueOf(e.getValue());
				if(currentCol < valMax) {
					cA.setValue(ArrayLogigramValue.NEG, s,
							String.valueOf(currentCol));
				}
			}
		}
		if (indexOk != -1 && indexOk2 != -1) {
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c4 = array2DWorld.getArray2D(domainWithA, dD);
			int indexOk23 = getIndexColOkFromLine(s2, domainWithA, c4);
			if (indexOk23 != -1) {
				int newVal = Integer.valueOf(dD.getElements().get(indexOk23).getValue()) + i;
				c4.setValue(ArrayLogigramValue.OK, s, String.valueOf(newVal));
			}
			
			int indexOk24 = getIndexColOkFromLine(s, domainWithA, c4);
			if (indexOk24 != -1) {
				int newVal = Integer.valueOf(dD.getElements().get(indexOk24).getValue()) - i;
				c4.setValue(ArrayLogigramValue.OK, s2, String.valueOf(newVal));
			}
		}

	}
}
