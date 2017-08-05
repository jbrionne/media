package fr.next.logigram.process;

import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.logigram.Array2DOrdValue;

public class Action {

	private String action;
	private int indexLine;
	private int indexCol;
	private Array2DOrd<Array2DOrdValue, String> array2D;
	private Array2DOrdValue value;
	private List<Case> casesInContext;
	
	public Action(String action, int indexLine, int indexCol, Array2DOrd<Array2DOrdValue, String> array2D, Array2DOrdValue value, List<Case> casesInContext) {
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

	public Array2DOrd<Array2DOrdValue, String> getArray() {
		return array2D;
	}

	public Array2DOrdValue getValue() {
		return value;
	}

	public List<Case> getCasesInContext() {
		return casesInContext;
	}
	
	
}
