package fr.next.logigram.array.logigram;

public enum Array2DOrdValue {
	
	EMPTY("-"),
	NEG("N"),
	OK("O");
	
	private String code;

	private Array2DOrdValue(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
