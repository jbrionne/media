package fr.next.logigram.array.logigram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrd;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.Coordinates;

public class Array2DOrdImpl implements Array2DOrd<Array2DOrdValue, String> {

	private Array2DOrdValue[][] cases;

	private Axe<AxeValue<String>> domainLine;
	private Axe<AxeValue<String>> domainCol;

	private Coordinates coordinates;

	public Array2DOrdImpl(Axe<AxeValue<String>> domainLine, Axe<AxeValue<String>> domainCol, Coordinates coordinates) {
		this.domainLine = domainLine;
		this.domainCol = domainCol;
		this.coordinates = coordinates;
		cases = new Array2DOrdValue[domainLine.getElements().size()][domainCol.getElements().size()];
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases[i].length; j++) {
				cases[i][j] = Array2DOrdValue.EMPTY;
			}
		}
	}

	@Override
	public void setValue(String valueDomLine, String valueDomCol, Array2DOrdValue value) {

		int indexLine = AxeOrd.findIndex(valueDomLine, domainLine);
		int indexCol = AxeOrd.findIndex(valueDomCol, domainCol);

		if (!cases[indexLine][indexCol].equals(Array2DOrdValue.EMPTY) && !cases[indexLine][indexCol].equals(value)) {
			throw new AssertionError(" already set " + cases[indexLine][indexCol] + " set " + value);
		}

		cases[indexLine][indexCol] = value;
	}

	@Override
	public void setValue(int indexLine, int indexCol, Array2DOrdValue value) {

		if (!cases[indexLine][indexCol].equals(Array2DOrdValue.EMPTY) && !cases[indexLine][indexCol].equals(value)) {
			throw new AssertionError(" already set " + cases[indexLine][indexCol] + " set " + value);
		}

		cases[indexLine][indexCol] = value;
	}

	@Override
	public Array2DOrdValue getValue(int indexLine, int indexCol) {
		return cases[indexLine][indexCol];
	}

	@Override
	public Array2DOrdValue[] getLine(int indexLine) {
		return cases[indexLine];
	}

	@Override
	public Array2DOrdValue[] getCol(int indexCol) {
		Array2DOrdValue[] caseCol = new Array2DOrdValue[cases.length];
		for (int i = 0; i < cases.length; i++) {
			caseCol[i] = cases[i][indexCol];
		}
		return caseCol;
	}

	@Override
	public Axe<AxeValue<String>> getAxeLine() {
		return domainLine;
	}

	@Override
	public Axe<AxeValue<String>> getAxeCol() {
		return domainCol;
	}

	@Override
	public String toString() {
		return "Array2D [cases=" + Arrays.toString(cases) + "]";
	}

	@Override
	public Array2DOrdValue getValue(String valueDomLine, String valueDomCol) {
		int indexLine = AxeOrdNum.findIndex(valueDomLine, domainLine);
		int indexCol = AxeOrdNum.findIndex(valueDomCol, domainCol);
		return cases[indexLine][indexCol];
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	@Override
	public List<Array2DOrdValue> getAll() {
		List<Array2DOrdValue> all = new ArrayList<>();
		for(Array2DOrdValue[] a : cases) {
			for(Array2DOrdValue k : a) {
				if(k != null) {
					all.add(k);
				}
			}
		}
		return all;
	}

}
