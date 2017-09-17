package fr.next.media.array.impl.logigram;

public enum ArrayLogigramValue {
	
	EMPTY("-"),
	NEG("N"),
	OK("O");
	
	private String code;

	private ArrayLogigramValue(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
