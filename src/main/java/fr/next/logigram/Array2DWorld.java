package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.Array2DOrdProxy;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.Coordinates;
import fr.next.logigram.array.logigram.Array2DOrdImpl;
import fr.next.logigram.array.logigram.Array2DOrdValue;

public class Array2DWorld {

	private Array2DOrd<Array2DOrd<Array2DOrdValue, String>, String> arrays2D;
	private List<Axe<AxeValue<String>>> domains;

	public Array2DWorld(List<Axe<AxeValue<String>>> domains) {
		this.domains = domains;
		
		
		//TODO
		int indexColSize = 0;
		for (int i = 1; i < domains.size(); i++) {
			indexColSize += domains.get(i).getElements().size();
		}
		Axe<AxeValue<String>> d0 = domains.get(0);
		int indexLineSize = d0.getElements().size();
		for (int i = domains.size() - 1; i > 0; i--) {
			indexLineSize += domains.get(i).getElements().size();
		}
		arrays2D = new Array2DWordImpl(indexLineSize, indexColSize);
		
		int indexColO = 0;
		for (int i = 1; i < domains.size(); i++) {
			Array2DOrd<Array2DOrdValue, String> array = new Array2DOrdImpl(d0, domains.get(i), new Coordinates(0, indexColO));
			arrays2D.setValue(0, indexColO, array);
			indexColO += domains.get(i).getElements().size();
		}

		int indexLine = d0.getElements().size();
		int indexCol = 0;
		for (int i = domains.size() - 1; i > 0; i--) {
			for (int j = 1; j < i; j++) {
				Array2DOrd<Array2DOrdValue, String> array2D = new Array2DOrdImpl(domains.get(i), domains.get(j), new Coordinates(indexLine, indexCol));
				arrays2D.setValue(indexLine, indexCol, array2D);
				indexCol += domains.get(j).getElements().size();
			}
			indexLine += domains.get(i).getElements().size();
		}
	}

	public Array2DOrd<Array2DOrdValue, String> getArray2D(Axe<AxeValue<String>> domainLine, Axe<AxeValue<String>> domainCol) {
		for (Array2DOrd<Array2DOrdValue, String> a : arrays2D.getAll()) {
			if ((!domainLine.equals(domainCol))
					&& (a.getAxeLine().equals(domainLine)
							&& a.getAxeCol().equals(domainCol))) {
				return a;
			}
		}
		return null;
	}

	public List<Array2DOrd<Array2DOrdValue, String>> findAllArrays2DWithDomainInCol(Axe<AxeValue<String>> domainName) {
		List<Array2DOrd<Array2DOrdValue, String>> arrays2DWithDomainInCol = new ArrayList<>();
		for (Array2DOrd<Array2DOrdValue, String> a : arrays2D.getAll()) {
			if (a.getAxeCol().equals(domainName)) {
				arrays2DWithDomainInCol.add(a);
			}
			if (a.getAxeLine().equals(domainName)) {
				arrays2DWithDomainInCol.add(new Array2DOrdProxy<Array2DOrdValue, String>(a));
			}
		}
		return arrays2DWithDomainInCol;
	}

	public List<Array2DOrd<Array2DOrdValue, String>> findAllArrays2DWithDomainInLine(Axe<AxeValue<String>> domainName) {
		List<Array2DOrd<Array2DOrdValue, String>> arrays2DWithDomainInLine = new ArrayList<>();
		for (Array2DOrd<Array2DOrdValue, String> a : arrays2D.getAll()) {
			if (a.getAxeLine().equals(domainName)) {
				arrays2DWithDomainInLine.add(a);
			}
			if (a.getAxeCol().equals(domainName)) {
				arrays2DWithDomainInLine.add(new Array2DOrdProxy<Array2DOrdValue, String>(a));
			}
		}
		return arrays2DWithDomainInLine;
	}

	public Array2DOrd<Array2DOrdValue, String> getArray2DSpecific(AxeValue<String> aEl, AxeValue<String> bEl) throws AssertionError {
		Axe<AxeValue<String>> domainWithA = aEl.getAxe();
		Axe<AxeValue<String>> domainWithB = bEl.getAxe();
		Array2DOrd<Array2DOrdValue, String> a = getArray2D(domainWithA, domainWithB);
		if (a == null) {
			Array2DOrd<Array2DOrdValue, String> v = getArray2D(domainWithB, domainWithA);
			if (v == null) {
				throw new AssertionError();
			}
			a = new Array2DOrdProxy<Array2DOrdValue, String>(v);
		}
		return a;
	}

	public List<Array2DOrd<Array2DOrdValue, String>> getArrays2D() {
		return arrays2D.getAll();
	}

	public List<? extends Axe<AxeValue<String>>> getDomains() {
		return domains;
	}
}
