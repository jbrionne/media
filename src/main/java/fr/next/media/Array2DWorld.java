package fr.next.media;

import java.util.ArrayList;
import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.ArrayXDOrdProxy;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeInt;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.Array2DMatrix3fImpl;
import fr.next.media.array.impl.ArrayFactory;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;
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
		
		axeLine = new AxeInt<>("worldLine", indexLineSize);
		axeCol = new AxeInt<>("worldCol", indexColSize);
		
		ArrayXDOrd axes = new MapXDWithEmptyValueGenericImpl<>(Integer.class, 0, axeLine, axeCol);
		ArrayXDOrd indices = new Array2DMatrix3fImpl<>(new AxeInt("x", 3), new AxeInt("y", 3));
		indices.setValue(0f, 0, 0);
		indices.setValue(0f, 0, 1);
		arrays2D = (ArrayXDOrd<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValueForWorld(axeLine, axeCol);
		arrays2D.addCoordinate(new CoordinatesXDByIndices(axes, indices));
		float indexColO = 0;
		for (int i = 1; i < domains.size(); i++) {
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(d0, domains.get(i));
			ArrayXDOrd indices2 = new Array2DMatrix3fImpl<>(new AxeInt("x", 3), new AxeInt("y", 3));
			indices2.setValue(0f, 0, 0);
			indices2.setValue(indexColO, 0, 1);
			array.addCoordinate(new CoordinatesXDByIndices(axes, indices2));
			arrays2D.setValueByIndices(array, 0, (int) indexColO);
			indexColO += domains.get(i).getElements().size();
		}

		float indexLine = d0.getElements().size();
		float indexCol = 0;
		for (int i = domains.size() - 1; i > 0; i--) {
			for (int j = 1; j < i; j++) {
				ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2D = (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>) ArrayFactory.newInstanceArrayLogigramValue(domains.get(i), domains.get(j));
				ArrayXDOrd indices2 = new Array2DMatrix3fImpl<>(new AxeInt("x", 3), new AxeInt("y", 3));
				indices2.setValue(indexLine, 0, 0);
				indices2.setValue(indexCol, 0, 1);
				array2D.addCoordinate(new CoordinatesXDByIndices(axes, indices2));
				arrays2D.setValueByIndices(array2D, (int) indexLine, (int) indexCol);
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
