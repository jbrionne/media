package fr.next.media.process;

import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;

public class Action {

	private String action;
	private int indexLine;
	private int indexCol;
	private ArrayXDOrd array2D;
	private ArrayLogigramValue value;
	private List<Case> casesInContext;
	
	public Action(String action, int indexLine, int indexCol, ArrayXDOrd array2D, ArrayLogigramValue value, List<Case> casesInContext) {
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

	public ArrayXDOrd getArray() {
		return array2D;
	}

	public ArrayLogigramValue getValue() {
		return value;
	}

	public List<Case> getCasesInContext() {
		return casesInContext;
	}
	
	
}
