package fr.next.media;

import java.util.ArrayList;
import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.ArrayXDOrdProxy;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeInt;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.ArrayFactory;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;

public class Array2DWorld {
	
	private Axe axeLine;
	private Axe axeCol;

	private ArrayXDOrd<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>, String, Axe<AxeValue<String>>> arrays2D;
	private List<Axe<AxeValue<String>>> domains;

	public Array2DWorld(List<Axe<AxeValue<String>>> domains) {
		this.domains = domains;
		
		

		int indexColSize = 0;
		for (int i = 1; i < domains.size(); i++) {
			for(int j=0; j < domains.get(i).getElements().size(); j++) {
				indexColSize++;
			}
		}
		Axe<AxeValue<String>> d0 = domains.get(0);
		int indexLineSize = d0.getElements().size();
		for (int i = domains.size() - 1; i > 0; i--) {
			for(int j=0; j < domains.get(i).getElements().size(); j++) {
				indexLineSize++;
			}
		}
		
		AxeInt axeInteger = new AxeInt<>("integer", Integer.MAX_VALUE);
		axeLine = new AxeInt<>("worldLine", indexLineSize);
		axeCol = new AxeInt<>("worldCol", indexColSize);
		
		//arrays2D = new Array2DWordImpl(indexLineSize, indexColSize);
		List<Axe<AxeValue>> axes = new ArrayList();
		axes.add(axeLine);
		axes.add(axeCol);
		int[] indices = new int[] { 0, 0 };
		arrays2D = (ArrayXDOrd<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValueForWorld(axeLine, axeCol, new CoordinatesXDByIndices(axes, indices));
		int indexColO = 0;
		for (int i = 1; i < domains.size(); i++) {
			int[] indices2 = new int[] { 0, indexColO};
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(d0, domains.get(i), new CoordinatesXDByIndices(axes, indices2));
			arrays2D.setValueByIndices(array, 0, indexColO);
			indexColO += domains.get(i).getElements().size();
		}

		int indexLine = d0.getElements().size();
		int indexCol = 0;
		for (int i = domains.size() - 1; i > 0; i--) {
			for (int j = 1; j < i; j++) {
				int[] indices2 = new int[] { indexLine, indexCol};
				ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2D = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domains.get(i), domains.get(j), new CoordinatesXDByIndices(axes, indices2));
				arrays2D.setValueByIndices(array2D, indexLine, indexCol);
				indexCol += domains.get(j).getElements().size();
			}
			indexLine += domains.get(i).getElements().size();
		}
	}

	public ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> getArray2D(Axe<AxeValue<String>> domainLine, Axe<AxeValue<String>> domainCol) {
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> a : arrays2D.getAll()) {
			if ((!domainLine.equals(domainCol))
					&& (a.getAxe(0).equals(domainLine)
							&& a.getAxe(1).equals(domainCol))) {
				return a;
			}
		}
		return null;
	}

	public List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> findAllArrays2DWithDomainInCol(Axe<AxeValue<String>> domainName) {
		List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> arrays2DWithDomainInCol = new ArrayList<>();
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> a : arrays2D.getAll()) {
			if (a.getAxe(1).equals(domainName)) {
				arrays2DWithDomainInCol.add(a);
			}
			if (a.getAxe(0).equals(domainName)) {
				arrays2DWithDomainInCol.add(new ArrayXDOrdProxy<ArrayLogigramValue, String, Axe<AxeValue<String>>>(a, new int [] {1, 0}, String.class));
			}
		}
		return arrays2DWithDomainInCol;
	}

	public List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> findAllArrays2DWithDomainInLine(Axe<AxeValue<String>> domainName) {
		List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> arrays2DWithDomainInLine = new ArrayList<>();
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> a : arrays2D.getAll()) {
			if (a.getAxe(0).equals(domainName)) {
				arrays2DWithDomainInLine.add(a);
			}
			if (a.getAxe(1).equals(domainName)) {
				arrays2DWithDomainInLine.add(new ArrayXDOrdProxy<ArrayLogigramValue, String, Axe<AxeValue<String>>>(a, new int [] {1, 0}, String.class));
			}
		}
		return arrays2DWithDomainInLine;
	}

	public ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> getArray2DSpecific(AxeValue<String> aEl, AxeValue<String> bEl) throws AssertionError {
		Axe<AxeValue<String>> domainWithA = aEl.getAxe();
		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> a = getArray2D(domainWithA, domainWithB);
		if (a == null) {
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> v = getArray2D(domainWithB, domainWithA);
			if (v == null) {
				throw new AssertionError();
			}
			a = new ArrayXDOrdProxy<ArrayLogigramValue, String, Axe<AxeValue<String>>>(v, new int [] {1, 0}, String.class);
		}
		return a;
	}

	public List getArrays2D() {
		return arrays2D.getAll();
	}

	public List<? extends Axe<AxeValue<String>>> getDomains() {
		return domains;
	}
}
