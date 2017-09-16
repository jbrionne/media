package fr.next.logigram.process;

import java.util.List;

import fr.next.logigram.array.ArrayXDOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.impl.logigram.ArrayLogigramValue;

public class Action {

	private String action;
	private int indexLine;
	private int indexCol;
	private ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2D;
	private ArrayLogigramValue value;
	private List<Case> casesInContext;
	
	public Action(String action, int indexLine, int indexCol, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2D, ArrayLogigramValue value, List<Case> casesInContext) {
		super();
		this.action = action;
		this.indexLine = indexLine;
		this.indexCol = indexCol;
		this.array2D = array2D;
		this.value = value;
		this.casesInContext = casesInContext;
	}

	public String getAction() {
		return action;
	}

	public int getIndexLine() {
		return indexLine;
	}

	public int getIndexCol() {
		return indexCol;
	}

	public ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> getArray() {
		return array2D;
	}

	public ArrayLogigramValue getValue() {
		return value;
	}

	public List<Case> getCasesInContext() {
		return casesInContext;
	}
	
	
}
