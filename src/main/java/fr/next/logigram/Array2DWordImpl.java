package fr.next.logigram;

import java.util.ArrayList;
import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.Coordinates;
import fr.next.logigram.array.logigram.Array2DOrdValue;

public class Array2DWordImpl implements Array2DOrd<Array2DOrd<Array2DOrdValue, String>, String> {

	private Array2DOrd<Array2DOrdValue, String>[][] cases;

	@SuppressWarnings("unchecked")
	public Array2DWordImpl(int nbLine, int nbCol) {
		this.cases = (Array2DOrd<Array2DOrdValue, String>[][]) new Array2DOrd[nbLine][nbCol];
	}
	
	@Override
	public void setValue(String valueDomLine, String valueDomCol, Array2DOrd<Array2DOrdValue, String> value) {
		throw new AssertionError();
	}

	@Override
	public void setValue(int indexLine, int indexCol, Array2DOrd<Array2DOrdValue, String> value) {
		cases[indexLine][indexCol] = value;
	}

	@Override
	public Array2DOrd<Array2DOrdValue, String> getValue(int indexLine, int indexCol) {
		return cases[indexLine][indexCol];
	}

	@Override
	public Array2DOrd<Array2DOrdValue, String> getValue(String valueDomLine, String valueDomCol) {
		throw new AssertionError();
	}

	@Override
	public Array2DOrd<Array2DOrdValue, String>[] getLine(int indexLine) {
		throw new AssertionError();
	}

	@Override
	public Array2DOrd<Array2DOrdValue, String>[] getCol(int indexCol) {
		throw new AssertionError();
	}

	@Override
	public AxeOrdNum getAxeLine() {
		throw new AssertionError();
	}

	@Override
	public AxeOrdNum getAxeCol() {
		throw new AssertionError();
	}

	@Override
	public Coordinates getCoordinates() {
		throw new AssertionError();
	}

	@Override
	public List<Array2DOrd<Array2DOrdValue, String>> getAll() {
		List<Array2DOrd<Array2DOrdValue, String>> all = new ArrayList<>();
		for(Array2DOrd<Array2DOrdValue, String>[] a : cases) {
			for(Array2DOrd<Array2DOrdValue, String> k : a) {
				if(k != null) {
					all.add(k);
				}
			}
		}
		return all;
	}
}
