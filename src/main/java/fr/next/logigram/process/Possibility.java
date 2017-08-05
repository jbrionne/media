package fr.next.logigram.process;

import fr.next.logigram.array.logigram.Array2DOrdValue;

public class Possibility {

	private String line;
	private String col;
	private Array2DOrdValue value;
	
	
	public Possibility(String line, String col, Array2DOrdValue value) {
		super();
		this.line = line;
		this.col = col;
		this.value = value;
	}
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public Array2DOrdValue getValue() {
		return value;
	}
	public void setValue(Array2DOrdValue value) {
		this.value = value;
	}
}
