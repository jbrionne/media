package fr.next.logigram.db;

public class Decoup {

	public Decoup(String before, String sub) {
		super();
		this.before = before;
		this.sub = sub;
	}
	
	private String before;
	private String sub;
	
	public String getBefore() {
		return before;
	}
	public String getSub() {
		return sub;
	}
	public void setBefore(String before) {
		this.before = before;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
	
}
