package fr.next.media.process;

import java.util.ArrayList;
import java.util.List;

public class LineMatching {

	
	private List<Integer> emptyCol = new ArrayList<>();
	private List<Case> casesInContext = new ArrayList<>();
	private int count;
	private int index;
	
	public List<Integer> getEmptyCol() {
		return emptyCol;
	}
	public void setEmptyCol(List<Integer> emptyCol) {
		this.emptyCol = emptyCol;
	}
	public List<Case> getCasesInContext() {
		return casesInContext;
	}
	public void setCasesInContext(List<Case> casesInContext) {
		this.casesInContext = casesInContext;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
