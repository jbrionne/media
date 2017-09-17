package fr.next.media.process;

import fr.next.media.array.impl.logigram.ArrayLogigramValue;

public class Possibility {

	private String line;
	private String col;
	private ArrayLogigramValue value;
	
	
	public Possibility(String line, String col, ArrayLogigramValue value) {
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
	public ArrayLogigramValue getValue() {
		return value;
	}
	public void setValue(ArrayLogigramValue value) {
		this.value = value;
	}
}
