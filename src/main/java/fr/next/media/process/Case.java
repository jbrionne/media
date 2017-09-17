package fr.next.media.process;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;

public class Case {

	private ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2D;
	
	private int indexLine;
	private int indexCol;
	private ArrayLogigramValue value;

	public Case(ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2D, ArrayLogigramValue value, int indexLine, int indexCol) {
		super();
		this.value = value;
		this.indexLine = indexLine;
		this.indexCol = indexCol;
		this.array2D = array2D;
	}

	public ArrayLogigramValue getValue() {
		return value;
	}

	public void setValue(ArrayLogigramValue value) {
		this.value = value;
	}


	public int getIndexLine() {
		return indexLine;
	}

	public int getIndexCol() {
		return indexCol;
	}

	public ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> getArray2D() {
		return array2D;
	}

}
