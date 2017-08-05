package fr.next.logigram.process;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.logigram.Array2DOrdValue;

public class Case {

	private Array2DOrd<Array2DOrdValue, String> array2D;
	
	private int indexLine;
	private int indexCol;
	private Array2DOrdValue value;

	public Case(Array2DOrd<Array2DOrdValue, String> array2D, Array2DOrdValue value, int indexLine, int indexCol) {
		super();
		this.value = value;
		this.indexLine = indexLine;
		this.indexCol = indexCol;
		this.array2D = array2D;
	}

	public Array2DOrdValue getValue() {
		return value;
	}

	public void setValue(Array2DOrdValue value) {
		this.value = value;
	}


	public int getIndexLine() {
		return indexLine;
	}

	public int getIndexCol() {
		return indexCol;
	}

	public Array2DOrd<Array2DOrdValue, String> getArray2D() {
		return array2D;
	}

}
